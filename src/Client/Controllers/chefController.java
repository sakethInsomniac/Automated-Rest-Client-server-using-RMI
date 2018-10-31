package Client.Controllers;

import ServerWork.Operations.RestaurantOrderRemoteInterface;
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
import pojo.order;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class chefController implements Initializable {


    private ObservableList<order> strList = FXCollections.observableArrayList();
    private ArrayList<order> tempArrayList = new ArrayList<>();
    private RestaurantOrderRemoteInterface restaurantOrderRemoteInterface;
    @FXML
    private Button BTPrepare;
    @FXML
    private ListView<order> waitingListView;

    private String selectedOrderId = "";
    private Timeline timeline;
    private Integer timeSeconds = 5;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getRegistryInterface();
        getTimerData(10);


    }//close initialize
    //ObservableList<orderList> WaitingList = FXCollections.observableArrayList();

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

        timeSeconds=time;
        // KeyFrame event handler
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1),
                        event -> {
                            timeSeconds--;
                            if (timeSeconds <= 0) {
                                timeline.stop();
                                try {

                                    //todo comment
                                    System.out.println("dattatttttttttttttttttttttttttttt fetchd");
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

        }
    }

    private void setArrayListData() throws RemoteException {
        try {
            waitingListView.getItems().clear();
            tempArrayList = restaurantOrderRemoteInterface.getWaitingList("0");
            loadListView(tempArrayList);
        } catch (RemoteException e) {

        }
    }

    private void loadListView(ArrayList<order> list) {
        try {
            ObservableList<order> pendingOrders = FXCollections.observableArrayList(list);
            waitingListView.getItems().addAll(pendingOrders);
            if (!waitingListView.getSelectionModel().isEmpty()) {
                BTPrepare.setDisable(false);
            }
            waitingListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                selectedOrderId = newValue.getOrderId();
            });
        } catch (Exception e) {
            System.out.println("error");
        }


    }

    @FXML
    private void prepareMenu(ActionEvent event) throws RemoteException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Order");
        alert.setContentText("You want to Prepare this order ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
                 try {
                System.out.println("selectedOrderId"+selectedOrderId);
                restaurantOrderRemoteInterface.updateOrderStatus(selectedOrderId, "1");
                System.out.println("Done with query");
                setArrayListData();
            } catch (Exception e) {

            }

        } else {

            return;
        }



    }
}
