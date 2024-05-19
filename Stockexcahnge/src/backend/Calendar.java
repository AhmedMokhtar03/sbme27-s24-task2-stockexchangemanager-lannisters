package backend;

import java.time.LocalDate;
import java.time.LocalDateTime;

public abstract class Calendar {
    public static LocalDate currentDate = LocalDate.now();
   // public static LocalDateTime time = LocalDateTime.now();
    public static void advanceDay() {
        currentDate = currentDate.plusDays(1);
    }
    // Method to get the current date
    public LocalDate getCurrentDate() {
        return currentDate;
    }
}
