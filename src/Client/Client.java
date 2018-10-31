package Client;
/*Operations.Client.Operations.Client Program
Following is the client program of this application. Here, we are fetching the remote object and invoking its method named animation().*/

import ServerWork.Operations.RestaurantOrderRemoteInterface;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client extends Application {

    public static void main(String[] args) {
        try {
            // Getting the registry
            Registry registry = LocateRegistry.getRegistry(null);

            // Looking up the registry for the remote object  or unbundling or unmarshaling
            RestaurantOrderRemoteInterface stub = (RestaurantOrderRemoteInterface) registry.lookup("advJavaRmi");

            // Calling the remote method using the obtained object
            stub.animation();

            System.out.println("Remote method invoked");
            launch(args);
        } catch (Exception e) {
            System.err.println("Operations.Client.Operations.Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     *
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set. The primary stage will be embedded in
     *                     the browser if the application was launched as an applet.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages and will not be embedded in the browser.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Views/clCustomer.fxml"));
        primaryStage.setTitle("SwinRestaurant Order");
        primaryStage.setScene(new Scene(root, 1310, 950));
        primaryStage.show();
    }

    public void run(String[] args) {
        launch(args);
    }
}