public class Order {
    int orderID;
    int traderID;
    String Label;
    String orderType;
    int quantity;
    double price;
    String orderStatus;

    public void place_order(String Label, int quantity, String ordertype, int userID) {
        this.Label = Label;
        this.quantity = quantity;
        this.orderType = ordertype;
        for(Company company : CompanyController.companyList){
            if(company.getLabel().equals(Label)){
                price = company.getStockPrice() * quantity;
//                for(User user: UserController.userList){
//                    if(company.getNumOfAvailableStocks() > quantity && user.getcashBalance()> price){
//                        update_order("APPROVED");
//                    }else
//                        update_order("REJECTED");
//                }
            }
            }
    }

    public void cancel_order() {

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
        }
    }
}
