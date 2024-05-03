package Stockexcahnge.src;

import java.util.List;

public class PremiumUser extends User implements Observer {
    private boolean notificationsEnabled;
    private List<Observer> observerList;

    public PremiumUser(int ID, String username, String password, double cashBalance,boolean isPremium) {
        super();
        this.notificationsEnabled = false;
    }


    public void showLineCharts() {

    }


    public void enableNotifications() {
        this.notificationsEnabled = true;
    }

    @Override
    public void update(Stock stock, double newPrice) {

    }

    @Override
    public void Subscribe(Observer observer){
        observerList.add(observer);
    }

    @Override
    public void unSubscribe(Observer observer){
        observerList.remove(observer);
    }
    @Override
    public <Stock> void update(Stock stock, double newPrice) {
        if (notificationsEnabled) {

            System.out.println("Notification: Stock price of " + stock.getClass() + " has changed to " + newPrice);
        }

 }


}
