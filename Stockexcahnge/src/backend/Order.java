//package backend;
//
//public class Order {
//    protected String Label;
//    protected String orderType;
//    protected int quantity;
//    protected double price;
//    protected double offeredPrice;
//    protected String orderStatus;
//    protected Company com;
//    protected int UserID;
//    private int sellingUserId;
//
//    public void buy(String Label, int quantity, int userID) {
//        this.UserID = userID;
//        User user = Users.get(userID);
//        this.Label = Label;
//        this.quantity = quantity;
//        for (Company company : CompanyController.companyList) {
//            if (company.getLabel().equals(Label)) {
//                com = company;
//                price = company.getStockPrice() * quantity;
//                orderExecution(company, price, quantity, user, Label);
//        }
//    }}
//
//public void sell(String Label, int quantity, double price, int userID){
//    this.offeredPrice = price;
//    this.sellingUserId = userID;
//    this.quantity = quantity;
//    this.orderStatus= "OFFER_FOR_SALE";
//}
//
//
//public void cancel_order() {
//    com.setNumOfAvailableStocks(com.getNumOfAvailableStocks() + quantity);
//    User user = Users.get(userID);
//    user.setCashBalance(user.getCashBalance() + price);
//}
//
////public void update_order(String state) {
////    String str = state.toUpperCase();
////    switch (str) {
////        case "APPROVED":
////            this.orderStatus = "APPROVED";
////            Securities newstock = SecurityFactory.createSecurity("stock", Label, quantity, orderType);
////            System.out.println("Your order has been approved");
////            break;
////        case "REJECTED":
////            this.orderStatus = "REJECTED";
////            System.out.println("No available stocks");
////            break;
////        case "OFFER_FOR_SALE":
////            this.orderStatus = "OFFER_FOR_SALE";
////            break;
////    }
////}
//
//public void buyFromUser(int buyingUserID, User buyingUser, User sellingUser) {
//    com.setStockPrice((com.getStockPrice() + offeredPrice) / 2);
//    sellingUser.removeStock();
//    buyingUser.addStock();
//    double totalPrice = com.getStockPrice() * quantity;
//    buyingUser.setCashBalance(buyingUser.getCashBalance() - totalPrice);
//    sellingUser.setCashBalance(sellingUser.getCashBalance() + totalPrice);
//    Securities newstock = SecurityFactory.createSecurity("stock", Label, quantity, "sell");
//    this.orderStatus = "COMPLETED";
//}
//protected void orderExecution(Company company, double price, int quantity, User user, String label){
//    if (company.getNumOfAvailableStocks() >= quantity && user.getCashBalance() >= price) {
//        Securities newstock = SecurityFactory.createSecurity("stock", label, quantity, "buy");
//        user.setCashBalance(user.getCashBalance() - price);
//        orderStatus = "EXECUTED";
//    } else {
//        throw new SecurityException("Not enough stock or funds to buy this item");
//    }
//}
//}
