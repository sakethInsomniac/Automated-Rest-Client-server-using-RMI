package ServerWork;/*Operation.Server.Operation.Server Program
An RMI server program should implement the remote interface or extend the implementation class. Here, we should create a remote object and bind it to the RMIregistry.

Following is the server program of this application. Here, we will extend the above created class, create a remote object, and registered it to the RMI registry with the bind name hello. */

import ServerWork.Operations.RestaurantOrderImpl;
import ServerWork.Operations.RestaurantOrderRemoteInterface;

import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server extends RestaurantOrderImpl {
    public Server() {
    }

    public static void main(String args[]) {
        try {
            // Instantiating the implementation class
            RestaurantOrderImpl obj = new RestaurantOrderImpl();


            if (System.getSecurityManager() == null) {
                System.setProperty("java.security.policy", "F:\\SwinBurne_Sem3\\AdvancedJava\\Assign2_submission\\server\\src\\Security.policy");
                System.setSecurityManager(new RMISecurityManager());
            }

            String[] command = new String[]{"rmiregistry", "2020"};
            Runtime.getRuntime().exec(command);


            // Exporting the object of implementation class
            // (here we are exporting the remote object to the stub)
            RestaurantOrderRemoteInterface stub = (RestaurantOrderRemoteInterface) UnicastRemoteObject.exportObject(obj, 0);

            // Binding the remote object (stub) in the registry
            Registry registry = LocateRegistry.getRegistry();

            // binding or bundling or marshaling
            registry.rebind("advJavaRmi", stub);
            System.out.println("Server ready");


            RestaurantOrderImpl impl = new RestaurantOrderImpl();
            impl.clearMenuDb();
            impl.initialDBUpload();

        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}