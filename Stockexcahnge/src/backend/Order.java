package backend;

import frontendmalak.ViewControl.AdminManageUsersController;

import java.io.IOException;

public class Order {
    public Boolean executed = false;
    protected String Label;
    protected int quantity;
    protected double price;
    protected String orderStatus;
    protected Company com;
    protected int UserID;
    User user;
    public void buy(String Label, int quantity, int userID) throws IOException {
        FindUser(Label, quantity, userID);
        for (Company company : DataManager.companyList) {
            if (company.getLabel().equals(Label)) {
                com = company;
                price = company.getStockPrice() * quantity;
                orderExecution(company, price, quantity, user, Label, "BUY");
        }
    }}

public void sell(String Label, int quantity, int userID) throws IOException {
    FindUser(Label, quantity, userID);
    for (Company company : DataManager.companyList) {
        if (company.getLabel().equals(Label)) {
            com = company;
            price = company.getStockPrice() * quantity;
            orderExecution(company, price, quantity, user, Label, "SELL");
        }
    }
}

    private void FindUser(String Label, int quantity, int userID) {
        this.UserID = userID;
        for(User u : AdminManageUsersController.userList){
            if(UserID == u.getID()){
                user = u;
            }
        }
        this.Label = Label;
        this.quantity = quantity;
    }

    protected void orderExecution(Company company, double price, int quantity, User user, String label, String state) throws IOException {
        switch (state) {
            case "BUY":
                if (company.getNumOfAvailableStocks() >= quantity && user.getCashBalance() >= price) {
                user.setCashBalance(user.getCashBalance() - price);
                Securities newstock = SecurityFactory.createSecurity("stock", label, quantity, state);
                }
                else{
                    throw new IllegalArgumentException("NOT ENOUGH MONEY OR STOCKS");
                }
                break;
            case "SELL":
                user.setCashBalance(user.getCashBalance() + price);
                Securities newstock = SecurityFactory.createSecurity("stock", label, quantity, state);
                break;
        }
        orderStatus = "EXECUTED";
}

}
