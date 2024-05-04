public class Order {
    String Label;
    String orderType;
    int quantity;
    double price;
    String orderStatus;
    Company com;
    int UserID;

    public void place_order(String Label, int quantity, String orderType, int userID) {
        this.UserID = userID;
        User user = Users.get(userID);
        this.Label = Label;
        this.quantity = quantity;
        this.orderType = orderType;
        for (Company company : CompanyController.companyList) {
            if (company.getLabel().equals(Label)) {
                com= company;
                price = company.getStockPrice() * quantity;
                    if(company.getNumOfAvailableStocks() > quantity && user.getCashBalance()> price){
                        update_order("APPROVED");
                        company.setNumOfAvailableStocks(company.getNumOfAvailableStocks() - quantity);
                        user.setCashBalance(user.getCashBalance() - price);
                    }else
                        update_order("REJECTED");
                }
            }
        }


    public void cancel_order() {
        this.orderStatus = "CANCELED";
        com.setNumOfAvailableStocks(com.getNumOfAvailableStocks() + quantity);
        User user = Users.get(userID);
        user.setCashBalance(user.getCashBalance() + price);
        update_order(orderStatus);
    }

    public void update_order(String state) {
        String str = state.toUpperCase();
        switch (str) {
            case "APPROVED":
                this.orderStatus = "APPROVED";
                Securities newstock = SecurityFactory.createSecurity("stock", Label, quantity, orderType);
                System.out.println("Your order has been approved");
                break;
            case "REJECTED":
                this.orderStatus = "REJECTED";
                System.out.println("No available stocks");
                break;
            case "CANCELLED":
                Securities returnstock = SecurityFactory.createSecurity("stock", Label, quantity, "new");
                break;
        }
    }
}
