package backend;

import frontendmalak.ViewControl.AdminManageUsersController;

import java.io.IOException;

public class LimitOrder extends Order {
    private final double limitPrice;

    public LimitOrder(String label, int quantity , int userID, double limitPrice) {
        this.limitPrice = limitPrice;
        this.Label =label;
        this.quantity = quantity;
        this.UserID=userID;
    }
    @Override
    public void buy(String label, int quantity, int userID) throws IOException {
        User user = null;
        for(User u : AdminManageUsersController.userList){
            if(UserID == u.getID()){
                user = u;
            }
        }
        this.Label = label;
        this.quantity = quantity;
        for (Company company : DataManager.companyList) {
            if (company.getLabel().equals(label)) {
                com = company;
                double currentPrice = company.getStockPrice();
                if (currentPrice <= limitPrice) {
                    price = currentPrice * quantity;
                    orderExecution(company, price, quantity, user, label, "BUY");
                    executed = true;
                    user.addStock(label, quantity);
                } else {
                    orderStatus = "PENDING";
                }
            }
        }
    }
    @Override
    public void sell(String label, int quantity, int userID) throws IOException {
        User user = null;
        for(User u : AdminManageUsersController.userList){
            if(UserID == u.getID()){
                user = u;
            }
        }
        this.Label = label;
        this.quantity = quantity;
        for (Company company : DataManager.companyList) {
            if (company.getLabel().equals(label)) {
                com = company;
                double currentPrice = company.getStockPrice();
                if (currentPrice <= limitPrice) {
                    price = currentPrice * quantity;
                    orderExecution(company, price, quantity, user, label, "SELL");
                    executed = true;
                    user.removeStock(label, quantity);
                } else {
                    orderStatus = "PENDING";
                }
            }
        }
    }
    }

