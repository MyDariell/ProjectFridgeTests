import server.ServerSideDatabase;
import java.io.IOException;

//RUN THIS TO START THE TEST SERVER FOR TESTING

public class ClientSideApplicationTest {
    public static void main(String[] args) {
        try {
            ServerSideDatabase testingServer = new ServerSideDatabase("ItemExpiryDates/TestFood_ExpiryDates.txt", 1234);
            testingServer.startServer();
            System.out.println("Server started successfully!");
        } catch (IOException e) {
            System.err.println("Failed to initialize the server: " + e.getMessage());
        }

    }
}
