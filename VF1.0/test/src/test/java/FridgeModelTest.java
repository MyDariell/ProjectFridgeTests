import client.Food;
import client.FridgeModel;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class FridgeModelTest {

    LocalDate currentDate = LocalDate.of(2024,11,21);
    FridgeModel clientFridge;
    Food item1 ;
    Food item2 ;

    private void createFridge () {
        clearFile("src/test/testFridges/emptyFridge.txt");
        this.currentDate = LocalDate.of(2024,11,21);
        this.item1 = new Food("Apple", Duration.ofDays(3), currentDate);
        this.item2 = new Food ("Banana", Duration.ofDays(5), currentDate);
        FridgeModel clientFridge = new FridgeModel(currentDate,"src/test/testFridges/emptyFridge.txt");
        clientFridge.addToFridge(item1);
        clientFridge.addToFridge(item2);
        this.clientFridge = clientFridge;
    }
    @Test
    public void testAddItem () {
        createFridge();
        HashMap<String, LocalDate> expectedFridge = new HashMap<>();
        expectedFridge.put("Apple", LocalDate.of(2024,11,24));
        expectedFridge.put("Banana", LocalDate.of(2024,11,26));

        assertEquals(expectedFridge, clientFridge.getClientFridge());
    }

    @Test
    public void removeItem () {
        createFridge();
        HashMap<String, LocalDate> expectedFridge = new HashMap<>();
        expectedFridge.put("Apple", LocalDate.of(2024,11,24));
        clientFridge.removeFromFridge("Banana");
        assertEquals(expectedFridge, clientFridge.getClientFridge());
    }

    @Test
    public void checkExpiry () {
        createFridge();
        clientFridge.setCurrentDate(LocalDate.of(2024,11,26));
        clientFridge.checkExpiry();

        HashMap<String, Boolean> expectedFoodExpired = new HashMap<>();
        expectedFoodExpired.put ("Apple", true);
        expectedFoodExpired.put ("Banana", false);
        assertEquals(expectedFoodExpired, clientFridge.getExpiredFood());
    }

    @Test
    public void checkDaysLeft () {
        createFridge();
        clientFridge.setCurrentDate(LocalDate.of(2024,11,26));
        clientFridge.checkExpiry();
        clientFridge.updateDaysLeft();

        HashMap<String, Boolean> expectedFoodExpired = new HashMap<>();
        expectedFoodExpired.put ("Apple", true);
        expectedFoodExpired.put ("Banana", false);
        assertEquals(2, clientFridge.getFoodDaysLeft("Apple"));
        assertEquals(0, clientFridge.getFoodDaysLeft("Banana"));

    }




    //HELPER METHODS

    public static boolean clearFile(String filePath) {
        try (FileWriter fileWriter = new FileWriter(filePath, false)) {
            return true;
        } catch (IOException e) {
            System.err.println("Error clearing file: " + e.getMessage());
            return false;
        }
    }


}
