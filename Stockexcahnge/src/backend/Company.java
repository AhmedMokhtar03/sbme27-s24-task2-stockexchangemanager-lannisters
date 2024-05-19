package backend;

import java.util.*;

public class Company extends Observable {
    private String name;
    private String label;
    public List<Double> graphList = new ArrayList<>();
    private int ID;
    public double openningPrice;
    public double closingPrice;
    private double stockPrice;
    private double dividends;
    private int numOfAvailableStocks;
    int shareholders =0;
    double MaximumPrice;
    double MinimumPrice;
    private List<Map<String, Double>> priceHistory = new ArrayList<Map<String, Double>>();
    private double totalPrice;
    private int quantity;
    public Company() {
    }

    public Company(String label, int quantity, double stockPrice, double totalPrice, double dividends) {
        this.label = label;
        this.quantity = quantity;
        this.stockPrice = stockPrice;
        this.totalPrice = totalPrice;
        this.dividends = dividends;
    }

    public Company(String name, String label, int ID, double stockPrice, double dividends, int numOfAvailableStocks) {
        this.ID = ID;
        this.name = name;
        this.label = label;
        this.stockPrice = stockPrice;
        this.numOfAvailableStocks = numOfAvailableStocks;
        this.dividends = dividends;
    }

    public void add_price_entry(Date date, double oldPrice, double newPrice) {
        Map<String, Double> entry = new HashMap<String, Double>();
        entry.put("date", (double) date.getTime());
        entry.put("OpeningPrice", getOpenningPrice());
        entry.put("closingPrice", getClosingPrice());
        entry.put("HighestPrice", getMaximumPrice());
        entry.put("LowestPrice", getMinimumPrice());
        priceHistory.add(entry);
    }

    public void setShareholders(int shareholders) {
        this.shareholders = shareholders;
    }

    public int getShareholders() {
        return shareholders;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public double getStockPrice() {
        return stockPrice;
    }

    public double getOpenningPrice() {
        return openningPrice;
    }

    public void setOpenningPrice(double openningPrice) {
        this.openningPrice = openningPrice;
    }

    public double getClosingPrice() {
        return closingPrice;
    }

    public void setClosingPrice(double closingPrice) {
        this.closingPrice = closingPrice;
    }

    public void setStockPrice(double stockPrice, double current_price) {
        this.stockPrice = stockPrice;
        DataManager.saveCompanies(this);
        Date currentDate = new Date();
        setMaximumPrice(Math.max(current_price, stockPrice));
        setMinimumPrice(Math.min(current_price, stockPrice));
        add_price_entry(currentDate, current_price, stockPrice);
        DataManager.savePriceHistory(this);
        setChanged();
        notifyObservers(stockPrice);
    }

    public double getDividends() {
        return dividends;
    }

    public void setDividends(double dividends) {
        this.dividends = dividends;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getNumOfAvailableStocks() {
        return numOfAvailableStocks;
    }

    public void setNumOfAvailableStocks(int numOfAvailableStocks)
    {
        this.numOfAvailableStocks = numOfAvailableStocks;
    }

    public void setMaximumPrice(double Maximumprice) {
        this.MaximumPrice = Maximumprice;
    }

    public double getMaximumPrice() {
        return MaximumPrice;
    }

    public void setMinimumPrice(double Minimumprice) {
        this.MinimumPrice = Minimumprice;
    }

    public double getMinimumPrice() {
        return MinimumPrice;
    }
    public List<Map<String, Double>> getPriceHistory() //when the user asks for the list of the price history, call this method
    {
        return priceHistory;
    }
}
