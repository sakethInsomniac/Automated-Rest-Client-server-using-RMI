package Client;
/*Operations.Client.Operations.Client Program
Following is the client program of this application. Here, we are fetching the remote object and invoking its method named animation().*/

import Client.Client;
import ServerWork.Operations.RestaurantOrderRemoteInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class main {


    public static void main(String[] args) {
        try {
            // Getting the registry
            Registry registry = LocateRegistry.getRegistry(null);

            // Looking up the registry for the remote object  or unbundling or unmarshaling
            RestaurantOrderRemoteInterface stub = (RestaurantOrderRemoteInterface) registry.lookup("advJavaRmi");

            // Calling the remote method using the obtained object
            stub.animation();

            System.out.println("Remote method invoked");
            run(args);

        } catch (Exception e) {
            System.err.println("Operations.Client.Operations.Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

    public static int menu() {
        System.out.println("1. Customer");
        System.out.println("2. Chef");
        System.out.println("3. Receptionist");

        Scanner in = new Scanner(System.in);
        System.out.println("Enter Your Choice :");
        int a = in.nextInt();

        return a;
    }

    public static void run(String[] args) {
        try {
            while (true) {
                switch (menu()) {
                    case 1:
                        Client client = new Client();
                        client.run(args);
                        break;


                    case 2:
                        Chef chef = new Chef();
                        chef.run(args);
                        break;


                    case 3:
                        Receptionist receptionist = new Receptionist();
                        receptionist.run(args);
                        break;

                    default:
                        System.out.println("Invalid option");
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("Something wrong !! Please try again.");

        }
    }
}