package ServerWork.Operations;

import Utility.DBConnect;
import Utility.ReadCSV;
import pojo.menuDetails;
import pojo.order;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static Utility.DBConnect.*;
import static Utility.commonConstants.*;

/*
Developing the Implementation Class
In the Implementation class (Remote Object) of this application, we are trying to create a window which displays GUI content, using JavaFX.
*/

// Implementing the remote interface RestaurantOrderRemoteInterface that contains void animation() throws RemoteException;
public class RestaurantOrderImpl implements RestaurantOrderRemoteInterface {

//    @Override
//    public void animation() throws RemoteException {
//
//    }


    @Override
    public void animation() throws RemoteException {
        System.out.println("default animation invoked");
    }

    @Override
    public Map<String, ArrayList<menuDetails>> loadDropDown(String mealType) {
        ReadCSV read = new ReadCSV();
        return getConsumables(read.csvQueryDropDownList(), mealType);
    }

    @Override
    public Map<String, ArrayList<menuDetails>> getDisplayChoiceData(String selectedFoodItem, String selectedBeverageItem) throws RemoteException {
        ArrayList<menuDetails> tempFoodList = new ArrayList<>();
        ArrayList<menuDetails> tempBeverageList = new ArrayList<>();

        Map<String, ArrayList<menuDetails>> tempMap = new HashMap<>();

        try {
            DBConnect db = new DBConnect();

            String foodStatement = "SELECT * FROM menu WHERE ItemName='" + selectedFoodItem + "'";
            String beverageStatement = "SELECT * FROM menu WHERE ItemName='" + selectedBeverageItem + "'";


            ResultSet rs = db.getData(foodStatement);
            ResultSet rsBeverage = db.getData(beverageStatement);

            if (rs.next()) {
                menuDetails menuDetails = new menuDetails(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)
                        , rs.getString(8), rs.getString(9), rs.getString(10));
                tempFoodList.add(menuDetails);
            }


            if (rsBeverage.next()) {
                menuDetails menuBeverageDetails = new menuDetails(rsBeverage.getString(1), rsBeverage.getString(2)
                        , rsBeverage.getString(3), rsBeverage.getString(4), rsBeverage.getString(5),
                        rsBeverage.getString(6), rsBeverage.getString(7),
                        rsBeverage.getString(8), rsBeverage.getString(9), rsBeverage.getString(10));
                tempBeverageList.add(menuBeverageDetails);
            }

            tempMap.put(FOOD_LIST, tempFoodList);
            tempMap.put(BEVERAGE_LIST, tempBeverageList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tempMap;
    }

    @Override
    public ArrayList<menuDetails> getDisplayOrderData(String customerName, String tableNumber) throws RemoteException {
        ArrayList<menuDetails> tempDisplayOrderList = new ArrayList<>();

        try {
            DBConnect db = new DBConnect();

            String statement = "SELECT c.cusName, c.cusTable, o.FoodName,o.BeverageName, o.OrderID FROM Orders o, customerDetails c " +
                    "where cusID=OrderID and cusTable=" + tableNumber + " and cusName='" + customerName + "'; ";

            ResultSet rs = db.getData(statement);

            while (rs.next()) {
                menuDetails order = new menuDetails(rs.getString(1).trim(), rs.getString(2).trim(),
                        rs.getString(3).trim(),
                        rs.getString(4).trim(), rs.getString(5).trim());
                tempDisplayOrderList.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tempDisplayOrderList;
    }

    /**
     * returns a map containing separated food and beverage arrayList from total foodList
     *
     * @param foodList total foodList received from server
     * @param mealType mealType selected by user
     * @return a map containing separated food and beverage arrayList
     */
    private Map<String, ArrayList<menuDetails>> getConsumables(ArrayList<menuDetails> foodList, String mealType) {
        // using streams and lambda functionality to filter data on mealType and MenuDescription
        ArrayList<menuDetails> foodItemsList = foodList.stream()
                .filter(n -> n.getMenuDesc().equalsIgnoreCase(FOOD) && n.getMealType().equalsIgnoreCase(mealType))
                .collect(Collectors.toCollection(ArrayList::new));

        ArrayList<menuDetails> beverageItemsList = foodList.stream()
                .filter(n -> n.getMenuDesc().equalsIgnoreCase(BEVERAGE) && n.getMealType().equalsIgnoreCase(mealType))
                .collect(Collectors.toCollection(ArrayList::new));

        Map<String, ArrayList<menuDetails>> tempMap = new HashMap<>();
        tempMap.put(FOOD_LIST, foodItemsList);
        tempMap.put(BEVERAGE_LIST, beverageItemsList);
        return tempMap;
    }


    public ArrayList<String> dbOrderTableData(int orderID) {
        ArrayList<String> odrList = new ArrayList<>();
        try {
            Class.forName(JDBC_DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Orders where OrderID=" + orderID);
            while (rs.next()) {
                odrList.add(rs.getString(2));
                odrList.add(rs.getString(3));
            }
            con.close();
        } catch (Exception e) {
            System.out.println("db exception:" + e.toString());
        }
        return odrList;
    }


    /**
     * function to enter data to database
     *
     * @param toogleGroupValue     selected meal type
     * @param customerName         name of customer
     * @param custTableNumber      table number of customer
     * @param selectedFoodItem     selected food item
     * @param selectedBeverageItem selected beverage item
     */
    public void insertOrderDataToDB(String toogleGroupValue, String customerName, String custTableNumber, String selectedFoodItem, String selectedBeverageItem) {
        String insertStmt = "INSERT INTO customerDetails(cusName, cusTable,MealType)" +
                "VALUES('" + customerName + "'," + Integer.parseInt(custTableNumber) + ",'" + toogleGroupValue + "');";
        DBConnect db = new DBConnect();
        int OrderID = db.dbconnectExecute(insertStmt);
        insertStmt = "INSERT INTO Orders(OrderID,FoodName,BeverageName,orderStatus)" +
                "VALUES('" + OrderID + "','" + selectedFoodItem + "','" + selectedBeverageItem + "',0);";
        db.dbconnectExecute(insertStmt);
    }

    @Override
    public ArrayList<order> getWaitingList(String statusValue) throws RemoteException {
        ArrayList<order> temp = new ArrayList<>();
        try {
            DBConnect db = new DBConnect();
            String query = "SELECT c.cusName, c.cusTable, o.FoodName,o.BeverageName, o.OrderID, o.orderStatus" +
                    "  FROM Orders o, customerDetails c where orderStatus='" + statusValue + "' AND cusID=OrderID";
            ResultSet rs = db.getData(query);


            while (rs.next()) {
                order order1 = new order(rs.getString(1), rs.getString(2),
                        rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6));
                temp.add(order1);
            }
        } catch (Exception e) {
            System.out.println("Exception:" + e.toString());
        }
        return temp;
    }

    @Override
    public void updateOrderStatus(String selectedOrderId, String updateValue) {
        try {
            System.out.println("OOO" + selectedOrderId);
            System.out.println("OOO1" + updateValue);
            DBConnect db = new DBConnect();
            String query = "update Orders set orderStatus='" + updateValue + "' where OrderID='" + selectedOrderId + "';";
            db.setData(query);
        } catch (Exception e) {
            System.out.println("exception:" + e.toString());
        }
    }

    @Override
    public ArrayList<String> dbCustomerTableData(String orderId) throws RemoteException {

        ArrayList<String> odrList = new ArrayList<>();
        try {
            Class.forName(JDBC_DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from customerDetails where cusID=" + orderId);
            while (rs.next()) {
                odrList.add(rs.getString(2));
                odrList.add(rs.getString(3));

            }
            con.close();
        } catch (Exception e) {
            System.out.println("db exception:" + e.toString());
        }
        return odrList;
    }

    @Override
    public ArrayList<String> dbOrderData(String orderId) throws RemoteException {
        ArrayList<String> odrList = new ArrayList<>();
        try {
            Class.forName(JDBC_DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Orders where OrderID=" + orderId);
            while (rs.next()) {
                odrList.add(rs.getString(2));
                odrList.add(rs.getString(3));
            }
            con.close();
        } catch (Exception e) {
            System.out.println("db exception:" + e.toString());
        }
        return odrList;
    }

    public void initialDBUpload() throws SQLException {
        String line;
        String cvsSplitBy = ",";
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;
        try (BufferedReader br = new BufferedReader(new FileReader(SRC_OPERATIONS_DATA_CSV_FILE_PATH))) {

            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] food = line.split(cvsSplitBy);


                DBConnect dbConnect = new DBConnect();

                String insertTableSQL = "INSERT INTO menu VALUES(?,?,?,?,?,?,?,?,?,?)";

                dbConnection = dbConnect.getConnection();
                preparedStatement = dbConnection.prepareStatement(insertTableSQL);
                preparedStatement.setString(1, food[0]);
                preparedStatement.setString(2, food[1]);
                preparedStatement.setString(3, food[2]);
                preparedStatement.setString(4, food[3]);
                preparedStatement.setString(5, food[4]);
                preparedStatement.setString(6, food[5]);
                preparedStatement.setString(7, food[6]);
                preparedStatement.setString(8, food[7]);
                preparedStatement.setString(9, food[8]);
                preparedStatement.setString(10, food[9]);

                // execute insert SQL stetement
                preparedStatement.executeUpdate();
                System.out.println("Record is inserted into DBUSER table!");

            }
        } catch (IOException e) {
            System.out.println("exception:" + e);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }
        }

    }

    public void clearMenuDb() {
        String statement = "truncate table menu";
        DBConnect db = new DBConnect();
        db.dbconnectExecute(statement);
        System.out.println("");
    }
}
