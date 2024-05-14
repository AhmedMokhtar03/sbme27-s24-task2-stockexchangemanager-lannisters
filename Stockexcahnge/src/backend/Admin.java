package backend;

import frontendmalak.ViewControl.AdminMangeUsersController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Admin {
    private int adminID;
    private int sessionID;
    private double openingPrice;
    private String userName;
    private String password;
    private boolean onSession;
    public static List<Stock> stockList = new ArrayList<>();

    //===========================================================
    public int getAdminID() {
        return adminID;
    }

    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }

    public int getSessionID() {
        return sessionID;
    }

    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }


    public double getOpeningPrice() {
        return openingPrice;
    }

    public void setOpeningPrice(double openingPrice) {
        this.openingPrice = openingPrice;
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

    //====================================================

    public void startSession() {
        onSession = true;
        //I want to use last data of the last session
        // opening price = closing price


    }

    public void endSession() {
        onSession = false;
        //closing price = current price
        //store closing price
        Calendar.advanceDay();
    }

//    public void createNewUser(String username, String password) {
//        if (username == null || username.isEmpty()) {
//            throw new IllegalArgumentException("Username cannot be empty");
//        }
//        if (password == null || password.isEmpty()) {
//            throw new IllegalArgumentException("Password cannot be empty");
//        }
//        this.username = username;
//        this.password = password;
//        //Approve_Users(); supposed to be uncommented after finishing this method in the admin class
//    }


    private void addUser() {
        //String userName = userNameField.getText();
        //String password = userPasswordField.getText();
        //forTesting we will get them from the fxml file
        int userID = 0;
        String userName = "ahmed";
        String password = "ahmed";

        User newUser = new User(userName, password);
        newUser.setID(newUser.hashCode());
        AdminMangeUsersController.userList.add(newUser);
    }

    //i imagine it is like the admin will have a page that show all the users and there are two buttons add and delete
    //if he pressed delete we will pass the id of this user
    private void deleteUser(int userID) {
        // Check if the user ID in the arraylist matches the wanted ID
        //if he didnt find a match no user will be deleted
        AdminMangeUsersController.userList.removeIf(tempUser -> tempUser.getID() == userID);
    }

    // i will use stock class cause i dont understand securities company
    private void addStock() {
        //for testing
        String label = "appl";
        int no_of_stocks = 500;
        String state = "NEW";
        int StockID = 0;

        //7asb el constructor
        Stock newStock = (Stock)SecurityFactory.createSecurity("Stock", label, no_of_stocks, "new");
        newStock.ID = newStock.hashCode();
        stockList.add(newStock);
    }

    private void deleteStock(int stockID) {

        // Check if the user ID in the arraylist matches the wanted ID
        //if he didnt find a match no user will be deleted
        stockList.removeIf(tempStock -> tempStock.getID() == stockID);
    }
    public void acceptRequests() {
    }

    public void reject() {
    }

//    public void createCompany(){
//        companyList.add(new Company(11,"apple","appl",100,100,100));
//        //companyList.add(new Company());
//
//    }


//    public void setCompany(int companyID, double dividends, int numOfAvailableStocks, double stockPrice, String companyName, String label) {
//        c2.setID(companyID);
//        c2.setName(companyName);
//        c2.setLabel(label);
//        c2.setStockPrice(stockPrice);
//        c2.setNumOfAvailableStocks(numOfAvailableStocks);
//        c2.setDividends(dividends);
//
//    }




/*
    public void setCompanyId(int companyID){
        objectName.setID(companyID);
    }
    public void setCompanyDividends(double dividends){
        objectName.setDividends(dividends);
    }
    public void setCompanyNumOfAvailableStocks(int numOfAvailableStocks){
        objectName.setNumOfAvailableStocks(numOfAvailableStocks);
    }
    public void setCompanyStockPrice(double stockPrice){
        objectName.setStockPrice(stockPrice);
    }
    public void setCompanyName(String companyName){
        objectName.setName(companyName);
    }
    public void setCompanyLabel(String label){
        objectName.setLabel(label);
    }
*/


}
