import java.time.LocalDate;

public class Calendar {
    private LocalDate currentDate;

    public Calendar() {
        this.currentDate = LocalDate.now();
    }

    public void advanceDay() {
        this.currentDate = this.currentDate.plusDays(1);
    }

    // Method to go back to the previous day
    // public void previousDay() {
    //   this.currentDate = this.currentDate.minusDays(1);
    //}

    // Method to get the current date
    public LocalDate getCurrentDate() {
        return this.currentDate;
    }
}
