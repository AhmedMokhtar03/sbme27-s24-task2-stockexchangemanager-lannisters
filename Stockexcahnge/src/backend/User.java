package backend;
import frontendmalak.ViewControl.ManageOrder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public  class User {
    public int ID;
    private String userName;
    private String password;
    private double cashBalance;
    private boolean isPremium;
    LocalDate firstDateOfPremium;
    private boolean  orderAccepted = false;
    //each user will have an arraylist for his/her owned stocks in different companies
    private Map<String, Integer> ownedStocks;

    public User() {}

//What the hell are you doing?
    public User( String username, String password) {
        this.userName = username;
        this.password = password;
        this.cashBalance = 0;
        this.isPremium = false;
        this.ownedStocks = new HashMap<>();
    }
    public User(String username) {
        this.userName = username;
    }
    //=============================================
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUserName() {
        return userName;
    }

    private void setUserName(String userName) {
        this.userName = userName;
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

    public boolean isPremium() {
        return isPremium;
    }

    public void setPremium(boolean premium) {
        isPremium = premium;
    }

    public LocalDate getFirstDateOfPremium() {
        return firstDateOfPremium;
    }

    public void setFirstDateOfPremium(LocalDate firstDateOfPremium) {
        this.firstDateOfPremium = firstDateOfPremium;
    }

    public void setOwnedStocks(Map<String, Integer> ownedStocks) {
        this.ownedStocks = ownedStocks;
    }

 //========================================
    public void addStock(String stockLabel, int quantity) {
        //update ownedstock data each time user buy
        ownedStocks.put(stockLabel, ownedStocks.getOrDefault(stockLabel, 0) + quantity);
    }
    //update ownedstock data each time user sell
    public void removeStock(String stockLabel, int quantity) {
        if (ownedStocks.containsKey(stockLabel)) {
            int currentQuantity = ownedStocks.get(stockLabel);
            currentQuantity -= quantity;
            orderAccepted = true;
            if (currentQuantity == 0) {
                ownedStocks.remove(stockLabel);
            } else {
                ownedStocks.put(stockLabel, currentQuantity);
            }
        }else{
         throw new IllegalArgumentException("Stock label " + stockLabel + " does not exist in your stocks");
        }
    }


    public void addOrder(String label, int quantity, String orderType, double price) {
        try {
            Order order = null;
            switch (orderType) {
                case "BUY":
                    order = new Order();
                    order.buy(label, quantity, this.ID);
                    addStock(label, quantity);
                    break;
                case "SELL":
                    order = new Order();
                    removeStock(label, quantity);
                    order.sell(label, quantity, this.ID);
                    break;
                case "LimitBuy":
                    order = new LimitOrder(label, quantity, this.ID, price);
                    order.buy(label, quantity, this.ID);
                    break;
                case "LimitSell":
                    order = new LimitOrder(label, quantity, this.ID, price);
                    order.sell(label, quantity, this.ID);
                    break;
            }
    }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
   // public abstract void Subscribe(Observer observer);

    //public abstract void unSubscribe(Observer observer);

    //public abstract void update(Stock stock, double newPrice);

}