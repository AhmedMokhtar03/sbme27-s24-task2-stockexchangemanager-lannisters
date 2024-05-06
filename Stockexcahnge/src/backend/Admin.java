package backend;

import java.util.ArrayList;

public class Admin {

    //private Company objectName = new Company();
    private Calendar calendar = new Calendar();

    public static ArrayList<Company> companyList = new ArrayList<Company>();

    private int adminID;
    private int sessionID;
    private double openingPrice;
    private String userName;
    private String password;
    private boolean onSession;


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

    public void startSession(){
        onSession = true;
        //I want to use last data of the last session
        // opening price = closing price


    }
    public void endSession(){
        onSession = false;
        //closing price = current price
        //store closing price
        calendar.advanceDay();
    }
    public void approval(){

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
