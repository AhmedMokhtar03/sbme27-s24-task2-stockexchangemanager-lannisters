package backend;
import java.time.LocalDate;
import java.util.*;

public  class User implements Observer {
    public Order order = null;
    private int ID;
    private static Set<Integer> usedIDs = new HashSet<>();
    private String userName;
    private String password;
    private double cashBalance;
    private boolean isPremium;
    LocalDate firstDateOfPremium;
    //each user will have an arraylist for his/her owned stocks in different companies
    private Map<String, Integer> ownedStocks;

    public User() {}


    public User(String username, String password) {
        this.ID = hashCode();
        this.userName = username;
        this.password = password;
        this.cashBalance = 0;
        this.isPremium = false;
        this.ownedStocks = new HashMap<>();
        generateUniqueID();

    }
    //        public User(String username) {
//        this.userName = username;
//    }
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

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
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
    public String toCSV() {
        StringBuilder sb = new StringBuilder();
        sb.append(ID).append(",")
                .append(userName).append(",")
                .append(password).append(",")
                .append(cashBalance).append(",")
                .append(isPremium).append(",")
                .append(firstDateOfPremium);

        // Append the number of stocks owned for each company
        if(!DataManager.companyList.isEmpty()){
        for (Company company : DataManager.companyList) {
            int ownedStocks = this.ownedStocks.getOrDefault(company.getLabel(), 0);
            sb.append(",").append(ownedStocks);
        }}

        return sb.toString();
    }

    private void generateUniqueID() {
        do {
            this.ID = hashCode();
        } while (usedIDs.contains(this.ID));
        usedIDs.add(this.ID);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Company company) {
            double newPrice = (double) arg;
            System.out.println("Notification: Stock price of " + company.getLabel() + " has changed to " + newPrice);
        }
    }
}