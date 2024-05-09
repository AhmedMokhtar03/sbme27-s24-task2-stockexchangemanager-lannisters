package backend;

public class Order {
    protected String Label;
    protected String orderType;
    protected int quantity;
    protected double price;
  //  protected double offeredPrice;
    protected String orderStatus;
    protected Company com;
    protected int UserID;
    private int sellingUserId;


    public void buy(String Label, int quantity, int userID) {
        this.UserID = userID;
        User user = UserManager.users.get(userID);
        this.Label = Label;
        this.quantity = quantity;
        for (Company company : CompanyController.companyList) {
            if (company.getLabel().equals(Label)) {
                com = company;
                price = company.getStockPrice() * quantity;
                orderExecution(company, price, quantity, user, Label, "BUY");
        }
    }}

public void sell(String Label, int quantity, int userID){
    this.UserID = userID;
    User user = UserManager.users.get(userID);
    this.Label = Label;
    this.quantity = quantity;
    for (Company company : CompanyController.companyList) {
        if (company.getLabel().equals(Label)) {
            com = company;
            price = company.getStockPrice() * quantity;
            orderExecution(company, price, quantity, user, Label, "SELL");
        }
    }
}

protected void orderExecution(Company company, double price, int quantity, User user, String label, String state){
    if (company.getNumOfAvailableStocks() >= quantity && user.getCashBalance() >= price) {
        Securities newstock = SecurityFactory.createSecurity("stock", label, quantity, state);
        switch (state) {
            case "BUY":
                user.setCashBalance(user.getCashBalance() - price);
                break;
            case "SELL":
                user.setCashBalance(user.getCashBalance() + price);
                break;
        }
        orderStatus = "EXECUTED";
    } else {
        throw new SecurityException("Not enough stock or funds to buy this item");
    }
}

}
