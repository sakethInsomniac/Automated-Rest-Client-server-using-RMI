package ServerWork.Operations;

import pojo.menuDetails;
import pojo.order;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

// Creating Remote interface for our application
public interface RestaurantOrderRemoteInterface extends Remote {

    void animation() throws RemoteException;

    Map<String, ArrayList<menuDetails>> loadDropDown(String mealType) throws RemoteException;

    Map<String, ArrayList<menuDetails>> getDisplayChoiceData(String selectedItem, String item) throws RemoteException;

    ArrayList<menuDetails> getDisplayOrderData(String customerName, String tableNumber) throws RemoteException;

    void insertOrderDataToDB(String toogleGroupValue, String customerName, String custTableNumber, String selectedItem, String FoodItem) throws RemoteException;

    ArrayList<order> getWaitingList(String statusValue) throws RemoteException;

    void updateOrderStatus(String selectedOrderId, String updateValue) throws RemoteException;

    ArrayList<String> dbCustomerTableData(String orderId) throws RemoteException;

    ArrayList<String> dbOrderData(String orderId) throws RemoteException;

}