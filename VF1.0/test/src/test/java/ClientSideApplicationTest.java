import client.ClientSideApplication;
import client.Food;
import client.FridgeModel;
import server.ServerSideDatabase;
import java.io.IOException;
import java.time.Duration;

//we can delete

public class ClientSideApplicationTest {
    public static void main(String[] args) {
        try {
            ServerSideDatabase testingServer = new ServerSideDatabase("ItemExpiryDates/TestFood_ExpiryDates.txt", 1234);
            testingServer.startServer();
            System.out.println("Server started successfully!");
        } catch (IOException e) {
            System.err.println("Failed to initialize the server: " + e.getMessage());
        }
//        Food item1 = new Food("PORK COOKED", Duration.ofDays(ClientSideApplication.getExpiryDate("PORK COOKED")));
//        Food item2 = new Food("APPLE", Duration.ofDays(ClientSideApplication.getExpiryDate("APPLE")));
//
//        FridgeModel fridge = new FridgeModel();
//        fridge.addToFridge(item1);
//        fridge.addToFridge(item2);
//
//        fridge.shutDownFridge();
    }
}
