package client;

import java.time.Duration;
import java.time.LocalDate;

public class Food {

    private String itemName;
    private LocalDate expiryDate;
    private LocalDate currentDate = LocalDate.now();

    public Food(String itemName, Duration timeToExpire, LocalDate currentDate) {
        this.itemName = itemName;
        this.currentDate = currentDate;
        this.expiryDate = currentDate.plusDays(timeToExpire.toDays());
    }

    public Food(String itemName, Duration timeToExpire) {
        this.itemName = itemName;
        this.expiryDate = currentDate.plusDays(timeToExpire.toDays());
    }

    public String getItemName() {
        return itemName;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    @Override
    public boolean equals(Object thatObject) {
        if (!(thatObject instanceof Food)) {
            return false;
        }
        Food thatFood = (Food) thatObject;
        return (this.getExpiryDate() == thatFood.getExpiryDate())
            && (this.itemName.equals(thatFood.getItemName()));
    }

    @Override
    public int hashCode() {
        int asciiSum = 0;
        for (char c : itemName.toCharArray()) {
            asciiSum += (int) c;
        }
        return currentDate.getDayOfMonth() + expiryDate.getDayOfMonth() + asciiSum;
    }
}
