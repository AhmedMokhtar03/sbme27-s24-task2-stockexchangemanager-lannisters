//import Stockexcahnge.src.Observer;
//import Stockexcahnge.src.Order;
//import Stockexcahnge.src.Stock;

import java.util.ArrayList;
import java.util.List;

public abstract class User {
    private int ID;
    private String username;
    private String password;
    private double cashBalance;
    private List<Order> orders;
    private List<Double> oldPrices;
    private boolean isPremium;
    public User(){}
    public User(int ID, String username, String password, double cashBalance,boolean isPremium) {
        this.ID = ID;
        this.username = username;
        this.password = password;
        this.cashBalance = cashBalance;
        this.orders = new ArrayList<>();
        this.oldPrices = new ArrayList<>();
        this.isPremium= isPremium;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    private String getUsername() {
        return username;
    }

    private void setUsername(String username) {
        this.username = username;
    }

    private String getPassword() {
        return password;
    }



    private void setPassword(String password) {
        this.password = password;
    }
    public double getCashBalance() {
        return cashBalance;
    }

    public void setCashBalance(double cashBalance) {
        this.cashBalance = cashBalance;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void setOldPrices(List<Double> oldPrices) {
        this.oldPrices = oldPrices;
    }

    public void createNewUser(String username, String password, double cashBalance, boolean isPremium) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        if (cashBalance < 0) {
            throw new IllegalArgumentException("Cash balance cannot be negative");
        }

        this.username = username;
        this.password = password;
        this.cashBalance = cashBalance;
        this.isPremium = isPremium;


        //Approve_Users(); supposed to be uncommented after finishing this method in the admin class
    }


    public void addOrder(Order order) {
        try {
            orders.add(order);
        } catch (Exception e) {
            System.err.println("Failed to add order: " + e.getMessage());
        }
    }

    public void deleteOrder(Order order) {
        try {
            orders.remove(order);
        } catch (Exception e) {
            System.err.println("Failed to delete order: " + e.getMessage());
        }
    }

    public List<Double> getOldPrices() {
        try {
            return oldPrices;
        } catch (Exception e) {
            System.err.println("Failed to retrieve old prices: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public abstract void Subscribe(Observer observer);

    public abstract void unSubscribe(Observer observer);
    public abstract void update(Stock stock, double newPrice);

}
