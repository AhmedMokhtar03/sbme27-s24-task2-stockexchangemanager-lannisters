package backend;
import java.util.Observable;
import java.util.Observer;
public class PremiumUser extends User implements Observer {
    public PremiumUser(int ID, String username, String password, double cashBalance,boolean isPremium) {
        super();
    }
    public void showLineCharts() {

    }
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Company company) {
            double newPrice = (double) arg;
            System.out.println("Notification: Stock price of " + company.getLabel() + " has changed to " + newPrice);
        }
    }
}
