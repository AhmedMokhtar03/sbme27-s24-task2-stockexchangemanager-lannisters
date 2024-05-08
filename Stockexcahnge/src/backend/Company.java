//package backend;
//
//import java.util.*;
//
//public class Company {
//    private String name;
//    private String label;
//    private int ID;
//    private double stockPrice;
//    private double dividends;
//    private int numOfAvailableStocks;
//    int shareholders =0;
//    double Maximumprice;
//    double Minimumprice;
//    private List<Map<String, Double>> priceHistory = new ArrayList<Map<String, Double>>();
//
//    public Company() {
//    }
//
//    public Company( String name, String label,int ID, double stockPrice, double dividends, int numOfAvailableStocks) {
//        this.ID = ID;
//        this.name = name;
//        this.label = label;
//        this.stockPrice = stockPrice;
//        this.numOfAvailableStocks = numOfAvailableStocks;
//        this.dividends = dividends;
//    }
//
//    public void add_price_entry(Date date, double oldPrice, double newPrice) {
//        Map<String, Double> entry = new HashMap<String, Double>();
//        entry.put("date", (double) date.getTime());
//        entry.put("OpeningPrice", newPrice);
//        entry.put("closingPrice", newPrice);
//        entry.put("HighestPrice", getMaximumprice());
//        entry.put("LowestPrice", getMinimumprice());
//        priceHistory.add(entry);
//    }
//
//    public void setShareholders(int shareholders) {
//        this.shareholders = shareholders;
//    }
//
//    public int getShareholders() {
//        return shareholders;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getLabel() {
//        return label;
//    }
//
//    public void setLabel(String label) {
//        this.label = label;
//    }
//
//    public int getID() {
//        return ID;
//    }
//
//    public void setID(int ID) {
//        this.ID = ID;
//    }
//
//    public double getStockPrice() {
//        return stockPrice;
//    }
//
//    public void setStockPrice(double stockPrice) {
//        this.stockPrice = stockPrice;
//    }
//
//    public double getDividends() {
//        return dividends;
//    }
//
//    public void setDividends(double dividends) {
//        this.dividends = dividends;
//    }
//
//    public int getNumOfAvailableStocks() {
//        return numOfAvailableStocks;
//    }
//
//    public void setNumOfAvailableStocks(int numOfAvailableStocks) {
//        this.numOfAvailableStocks = numOfAvailableStocks;
//    }
//
//    public void setMaximumprice(double Maximumprice) {
//        this.Maximumprice = Maximumprice;
//    }
//
//    public double getMaximumprice() {
//        return Maximumprice;
//    }
//
//    public void setMinimumprice(double Minimumprice) {
//        this.Minimumprice = Minimumprice;
//    }
//
//    public double getMinimumprice() {
//        return Minimumprice;
//    }
//    public List<Map<String, Double>> getPriceHistory() //when the user asks for the list of the price history, call this method
//    {
//        return priceHistory;
//    }
//
//
//    @Override
//    public String toString() {
//        return "Company{" + name +"," + label +","+ID+","+stockPrice+","+numOfAvailableStocks+","+dividends+"}";
//    }
//}
