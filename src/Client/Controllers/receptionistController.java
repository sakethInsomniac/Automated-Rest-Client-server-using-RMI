package Client.Controllers;

import ServerWork.Operations.RestaurantOrderRemoteInterface;
import Utility.ReadCSV;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.util.Duration;
import pojo.menuDetails;
import pojo.order;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class receptionistController implements Initializable {


    private RestaurantOrderRemoteInterface restaurantOrderRemoteInterface;

    @FXML
    private Button BTbill, btnRefresh;

    @FXML
    private ListView<order> servingListView;
    private ArrayList<order> tempArrayList;
    private String selectedOrderId;

    private Timeline timeline;
    private Integer timeSeconds = 10;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getRegistryInterface();
        getTimerData(10);

    }//close initialize

    private void getRegistryInterface() {
        try {
            Registry registry = LocateRegistry.getRegistry(null);

            // Looking up the registry for the remote object  or unbundling or unmarshaling
            restaurantOrderRemoteInterface = (RestaurantOrderRemoteInterface) registry.lookup("advJavaRmi");
        } catch (RemoteException | NotBoundException e) {
            System.out.println("exception while getting interface" + e.toString());
        }
    }


    private void getTimerData(int time) {
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeSeconds = time;
        // KeyFrame event handler
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1),
                        event -> {
                            timeSeconds--;
                            if (timeSeconds <= 0) {
                                timeline.stop();
                                try {

                                    //todo comment
                                    System.out.println("dattatttttttttttttttttttttttt fetchd");
                                    getTimerData(10);
                                    setArrayListData();


                                } catch (RemoteException e) {
                                    System.out.println("w");
                                }
                            }
                        }));
        timeline.playFromStart();
    }

    @FXML
    private void validateEntry(ActionEvent event) throws RemoteException {

        try {
            setArrayListData();
        } catch (RemoteException e) {
            System.out.println("a");
        }
    }

    private void setArrayListData() throws RemoteException {
        if (!servingListView.getItems().isEmpty()) {
            servingListView.getItems().clear();
        }
        tempArrayList = restaurantOrderRemoteInterface.getWaitingList("1");
        loadListView(tempArrayList);
    }

    private void loadListView(ArrayList<order> list) {
        try {
            ObservableList<order> pendingOrders = FXCollections.observableArrayList(list);
            servingListView.getItems().addAll(pendingOrders);
            if (!servingListView.getSelectionModel().isEmpty()) {
                BTbill.setDisable(false);
            }
            servingListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                selectedOrderId = newValue.getOrderId();
            });
        } catch (Exception e) {
            System.out.println("errr");
        }

    }

    @FXML
    private void billMenu(ActionEvent event) throws RemoteException {
        try {
            restaurantOrderRemoteInterface.updateOrderStatus(selectedOrderId, "2");
            setArrayListData();
            GenerateBill(selectedOrderId);
        } catch (Exception e) {
            System.out.println("e");
        }
    }

    private void GenerateBill(String order) {
        Document document = new Document();
        try {
            ObservableList<menuDetails> items = FXCollections.observableArrayList();
            ArrayList<String> temp = restaurantOrderRemoteInterface.dbCustomerTableData(order);
            ArrayList<String> temp1 = restaurantOrderRemoteInterface.dbOrderData(order);
            ReadCSV rd = new ReadCSV();
            String s1 = temp1.get(0);
            String s2 = temp1.get(1);
            items = rd.csvqueryLoadTableData(s1, s2);

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("src/Client/Bills/Bill.pdf"));
            document.open();
            document.add(new Paragraph("Invoice for the User: " + temp.get(0)));
            document.add(new Paragraph("Table Number: " + temp.get(1)));
            PdfPTable table = new PdfPTable(7);
            PdfPCell cell1 = new PdfPCell(new Paragraph("Item Name"));
            PdfPCell cell2 = new PdfPCell(new Paragraph("Energy"));
            PdfPCell cell3 = new PdfPCell(new Paragraph("Protien"));
            PdfPCell cell4 = new PdfPCell(new Paragraph("CarboHydrates"));
            PdfPCell cell5 = new PdfPCell(new Paragraph("Total Fat"));
            PdfPCell cell6 = new PdfPCell(new Paragraph("Fibre"));
            PdfPCell cell7 = new PdfPCell(new Paragraph("Price"));


            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);
            table.addCell(cell5);
            table.addCell(cell6);
            table.addCell(cell7);

            for (int i = 0; i < items.size(); i++) {
                table.addCell(items.get(i).getItemName());
                table.addCell(items.get(i).getEnergy());
                table.addCell(items.get(i).getProtein());
                table.addCell(items.get(i).getCarbohydrates());
                table.addCell(items.get(i).getTotalFat());
                table.addCell(items.get(i).getDietaryFibre());
                table.addCell(items.get(i).getPrice());

            }

            document.add(table);
            document.add(new Paragraph("Thank You :)"));
            document.close();
            writer.close();
            File f = new File("src/Client/Bills/Bill.pdf");
            if (f.exists()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Bill Generated under Bills Folder ", ButtonType.CLOSE);
                alert.showAndWait();
                //System.out.printf(cusName.getText());//testing line
                //loadListView(strList);

                return;
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }


}
