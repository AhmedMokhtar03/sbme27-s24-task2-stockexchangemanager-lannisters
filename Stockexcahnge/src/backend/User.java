package backend;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public  class User {
    private int ID;
    private String userName;
    private String password;
    private double cashBalance;
    private boolean isPremium;
    LocalDate firstDateOfPremium;
    //each user will have an arraylist for his/her owned stocks in diffrent companies
    private Map<String, Integer> ownedStocks;

    private List<Order> orders;
    private Map<Integer, Order> OrderIds = new HashMap<>();

    //goes to stock class
    //private List<Double> oldPrices;
    //private Map<String, Integer> offeredStocks = new HashMap<>();

  //  public User() {
   // }

    public User(int ID, String username, String password) {
        this.ID = ID;
        this.userName = username;
        this.password = password;
        this.cashBalance = 0;
        this.isPremium = false;
        this.ownedStocks = new HashMap<>();
         this.orders = new ArrayList<>();
        //this.oldPrices = new ArrayList<>();
    }
//=============================================
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    private String getUserName() {
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
    public List<Order> getOrders() {
        return orders;
    }
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

//    public void setOldPrices(List<Double> oldPrices) {
//        this.oldPrices = oldPrices;
//    }


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
        }
        //should be exciption handling here
    }


    public void addOrder(String label, int quantity, String orderType, double price) {
        try {
            Order order;

            if (orderType.equalsIgnoreCase("LIMIT")) {
                order = new LimitOrder(label, quantity, this.getID(), price);
                if (orderType.equalsIgnoreCase("SELL")) {
                    order.sell(label, quantity, price, this.getID());
                }
                else if (orderType.equalsIgnoreCase("BUY")) {
                    order.buy(label, quantity, this.ID);
                }
                else
                    throw new IllegalArgumentException("Invalid order type");
            }

            else if (orderType.equalsIgnoreCase(" IMMEDIATE")) {
                order = new Order();
                if (orderType.equalsIgnoreCase("SELL")) {
                    order.sell(label, quantity, price, this.getID());
                }
                else if (orderType.equalsIgnoreCase("BUY")) {
                    order.buy(label, quantity, this.ID);
                }
                else
                    throw new IllegalArgumentException("Invalid order type");
            }

            else  throw new IllegalArgumentException("Invalid order type");

            orders.add(order);
            for (Order o : orders) {
                OrderIds.put(o.hashCode(), o);
            }
            System.out.println("Order added and your orderID is: " + order.hashCode());
        } catch (Exception e) {
            System.err.println("Failed to add order: " + e.getMessage());
        }

    }


//            if (orderType.equalsIgnoreCase("BUY_FROM_USER")) {
//                int sellingUserID = findSellingUser(label, quantity);
//                if (sellingUserID != -1) {
//                    User selling = Users.get(sellingUserID);
//                    order = selling.getOfferedOredr(label, quantity);
//                    order.buyFromUser(this.getID(), this, selling);
//                }
//                else {
//                    throw new IllegalArgumentException("No offer");
//                }
//            }




//
//    private int findSellingUser(String label, int quantity) {
//        for (User user : Users.values) {
//            if (user.offeredStocks.containsKey(label) && user.offeredStocks.get(label) >= quantity) {
//                return user.getID();
//            }
//        }
//        return -1;
//    }
//
//    public Order getOfferedOredr(String label, int quantity) {
//        for (Order order : orders) {
//            if (order.Label.equalsIgnoreCase(label) && order.quantity == quantity && order.orderStatus.equalsIgnoreCase("offer_for_sale")) {
//                return order;
//            }
//        }
//        throw new IllegalArgumentException("No offer");
//    }



//
//    public void deleteOrder(int OrderID) {
//        try {
//            if (OrderIds.containsKey(OrderID)) {
//                Order order = orders.get(OrderID);
//                order.cancel_order();
//                orders.remove(order);
//                OrderIds.remove(OrderID);
//                System.out.println("Order with ID " + OrderID + " has been canceled and removed.");
//            } else {
//                throw new IllegalArgumentException("OrderID is not in order list");
//            }
//        } catch (Exception e) {
//            System.err.println("Failed to delete order: " + e.getMessage());
//        }
//    }

    public List<Map<String, Double>> getOldPrices(String label) {
        try {
            for (Company c : CompanyController.companyList) {
                if (c.getLabel().equals(label)) {
                    return c.getPriceHistory();
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to retrieve old prices: " + e.getMessage());
            return new ArrayList<>();
        }
        return null;
    }

   // public abstract void Subscribe(Observer observer);

    //public abstract void unSubscribe(Observer observer);

    //public abstract void update(Stock stock, double newPrice);

}
