package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class ServerSideDatabase {
    private static final HashMap<String, Duration> expiryDate = new HashMap<>();
    private static final ArrayList<String> allFoods = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        Socket socket = null;
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;
        // Buffered Section improves efficiency by wrapping bytes / chars as a package and sending them
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;


        ServerSocket serverSocket = null;


        serverSocket = new ServerSocket(1234);

        //READ CSV
        String filePath = "ItemExpiryDates/Food_ExpiryDates.txt";
        try (BufferedReader parseBufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = parseBufferedReader.readLine()) != null) {
                // Split the line using comma as the delimiter
                String[] parts = line.split(",");

                // Ensure the line has exactly two parts
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    int days = Integer.parseInt(parts[1].trim());


                    // Add the entry to the HashMap
                    expiryDate.put(key, Duration.ofDays(days));
                    allFoods.add(key);
                } else {
                    System.out.println("Invalid line format: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                socket = serverSocket.accept(); // Accept new client connection
                System.out.println("New client connected.");


                // Create a new thread to handle the client
                ClientSideThread clientHandler = new ClientSideThread(socket);
                Thread thread = new Thread(clientHandler);
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected static Duration getExpiryDate(String foodSearched) {
        return expiryDate.get(foodSearched);
    }

    protected static void addFoodEntry(String food, Integer expiryDays) {
        expiryDate.put(food, Duration.ofDays(expiryDays));
    }

    protected static ArrayList<String> getSearchResult(String searchInput) {
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < allFoods.size(); i++){
            if (allFoods.get(i).contains(searchInput)) {
                result.add(allFoods.get(i));
            }
        }
        return result;
    }
}

class ClientSideThread implements Runnable {
    private final Socket socket;

    ClientSideThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
             BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter)) {


            String msgFromClient;


            while ((msgFromClient = bufferedReader.readLine()) != null) {
                System.out.println("Client: " + msgFromClient);

                // Handle client commands (example: adding or retrieving expiry dates)
                if (msgFromClient.startsWith("GET")) {
                    String food = msgFromClient.substring(4);
                    Duration expiry = ServerSideDatabase.getExpiryDate(food);
                    if (expiry != null) {
                        System.out.println("Expiry for " + food + ": " + expiry.toDays() + " days.");
                        bufferedWriter.write(String.valueOf(expiry.toDays()));
                    } else {
                        bufferedWriter.write("no_expiry_date");
                    }
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                } else if (msgFromClient.startsWith("SEARCH")) {
                    String searchValue = msgFromClient.substring(7);
                    ArrayList<String> indexedSearch = ServerSideDatabase.getSearchResult(searchValue);
                    String delimiter = ";";
                    try {
                        if (indexedSearch.isEmpty()) {
                            bufferedWriter.write("EMPTY");
                        } else {
                            String listToSend = String.join(delimiter, indexedSearch);
                            bufferedWriter.write(listToSend);
                        }
                        bufferedWriter.newLine();
                        bufferedWriter.flush();

                    } catch (Exception e) {
                        e.printStackTrace();
                        bufferedWriter.write("Error processing SEARCH command.");
                        bufferedWriter.newLine();
                        bufferedWriter.flush();
                    }
                } else if (msgFromClient.equalsIgnoreCase("STOP")) {
                    bufferedWriter.write("Goodbye!");
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                    break;
                } else {
                    bufferedWriter.write("Unknown command.");
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}



