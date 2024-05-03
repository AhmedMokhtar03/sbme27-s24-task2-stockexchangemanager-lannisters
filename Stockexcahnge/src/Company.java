public class Company {
    private String name;
    private String label;
    private int ID;
    private double stockPrice;
    private double dividends;
    private int numOfAvailableStocks;

    public Company() {
    }

    public Company( String name, String label,int ID, double stockPrice, double dividends, int numOfAvailableStocks) {
        this.ID = ID;
        this.name = name;
        this.label = label;
        this.stockPrice = stockPrice;
        this.numOfAvailableStocks = numOfAvailableStocks;
        this.dividends = dividends;
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

    public void setStockPrice(double stockPrice) {
        this.stockPrice = stockPrice;
    }

    public double getDividends() {
        return dividends;
    }

    public void setDividends(double dividends) {
        this.dividends = dividends;
    }

    public int getNumOfAvailableStocks() {
        return numOfAvailableStocks;
    }

    public void setNumOfAvailableStocks(int numOfAvailableStocks) {
        this.numOfAvailableStocks = numOfAvailableStocks;
    }



    @Override
    public String toString() {
        return "Company{" + name +"," + label +","+ID+","+stockPrice+","+numOfAvailableStocks+","+dividends+"}";
    }
}
