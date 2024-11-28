package client;

import java.io.*;
import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Representation Invariant
 * <p>
 * Abstract Function
 */

public class FridgeModel {
    private HashMap<String, LocalDate> clientFridge;
    private HashMap<String, Boolean> foodExpired;
    private LocalDate today = LocalDate.now();
    private String clientFridgePath = "src/ClientFridge.txt";

    public FridgeModel() {
        this.clientFridge = new HashMap<>();
        this.foodExpired = new HashMap<>();
        loadCSV();
        ClockThread thread = new ClockThread();
        thread.start();

    }

    public FridgeModel(LocalDate startingDate, String path) {
        this.today = startingDate;
        this.clientFridge = new HashMap<>();
        this.clientFridgePath = path;
        this.foodExpired = new HashMap<>();
        loadCSV();
    }

    public void addToFridge(Food food) {
        clientFridge.put(food.getItemName(), food.getExpiryDate());
        foodExpired.put(food.getItemName(), false);
    }

    public boolean hasItem(Food food) {
        return clientFridge.entrySet().stream()
            .anyMatch(entry -> entry.getKey().equals(food.getItemName()) && entry.getValue() == food.getExpiryDate());
    }

    /**
     * Returns true if food is successfully removed from the fridge. False otherwise.
     *
     * @param food
     * @return
     */
    public boolean removeFromFridge(Food food) {
        if (hasItem(food)) {
            clientFridge.remove(food.getItemName());
            foodExpired.remove(food.getItemName());
            return true;
        }
        return false;
    }

    /**
     * Checks all Item in the fridge, whether they have expired.
     * returns true if at least more than one item has expired and been set to expired.
     *
     * @return
     */
    public void checkExpiry() {
        for (String food : clientFridge.keySet()) {
            if (today.isAfter(clientFridge.get(food))) {
                foodExpired.put(food, true);
            }
        }
    }

    //GETTERS------------------------------------------------------------------------------------------------

    /**
     * Gets a Map of all the food in the Fridge mapped to whether they are expired or not.
     * @return
     */
    public HashMap<String, Boolean> getExpiredFood() {
        return new HashMap<>(foodExpired);
    }

    /**
     * Returns a Map of the client's fridge and the item's expiry date
     * @return
     */
    public HashMap<String, LocalDate> getClientFridge() {
        return new HashMap<>(clientFridge);
    }

    /**
     * Updates the current Date to the System Date
     */
    public void updateCurrentDate() {
        this.today = LocalDate.now();
    }


    /**
     * Manual override of current date
     * Used for testing.
     *
     * @param date
     */
    public void setCurrentDate(LocalDate date) {
        this.today = date;
    }
    public void loadPath(String path) {
        clientFridgePath = path;
    }


    //WRITE AND LOADING CSV------------------------------------------------------------------------------------------
    public void writeCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/ClientFridge.txt"))) {
            for (Map.Entry<String, LocalDate> entry : clientFridge.entrySet()) {
                String line = entry.getKey() + "," + entry.getValue().toString();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadCSV() {
        try (BufferedReader reader = new BufferedReader(new FileReader(clientFridgePath
        ))) {
            String line;

            while ((line = reader.readLine()) != null) {
                // Split the line by the comma delimiter
                String[] parts = line.split(",");
                String itemName = parts[0].trim();
                LocalDate expiryDate = LocalDate.parse(parts[1].trim());
                clientFridge.put(itemName, expiryDate);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Thread that continously updates the currentDate whenever client is up and checks the expiry date of the fridge.
     */
    class ClockThread extends Thread {
        public void run() {
            while (true) {
                updateCurrentDate();
                checkExpiry();
                try {
                    sleep(Duration.ofSeconds(300));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}


