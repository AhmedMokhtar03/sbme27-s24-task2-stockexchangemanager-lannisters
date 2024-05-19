package backend;
import java.util.*;

public class Stock extends Securities {
    private double currentPrice;
    public double Dividend;
    private Company company;
    private int numberofstocks;

    public double getDividend() {
        return Dividend;
    }
    public Stock(String label, int no_of_stocks, String state)//this constructor is to be used if the user wants to buy a stock state=sell||buy
    { this.numberofstocks= no_of_stocks;
        for (Company c : DataManager.companyList) {
            if (c.getLabel().equalsIgnoreCase(label)) {
                this.company = c;
                this.currentPrice = c.getStockPrice();
            }
        }
        if (company != null) {
            switch (state.toUpperCase()) {
                case "BUY":
                    update_available_stocks(no_of_stocks);
                    company.setShareholders(company.getShareholders() + 1);
                    updateStockPrice(no_of_stocks, state);
                    break;
                case "SELL":
                    update_available_stocks((-1)*no_of_stocks);
                    updateStockPrice(no_of_stocks, state);
                    break;
                case "NEW":
                    company.setNumOfAvailableStocks(no_of_stocks);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid state");
            }
        } else {
            throw new IllegalArgumentException("company does not exist");
        }

    }

    public void update_available_stocks(int no_of_stocks) //only call this method when you approve the user's request to buy or sell a stock
    {
        company.setNumOfAvailableStocks(Math.abs(company.getNumOfAvailableStocks() - no_of_stocks));
    }

    public void updateStockPrice(int quantity, String orderType) {
        double current_price = this.currentPrice;
        double newPrice;
        double randomFactor = generateRandomFactor();
        if (orderType.equalsIgnoreCase("buy")) {
            newPrice = current_price * (1 + randomFactor);
            newPrice = newPrice * quantity + current_price;
        } else if (orderType.equalsIgnoreCase("sell")) {
            newPrice = current_price * (1 - randomFactor);
            newPrice = newPrice * quantity + current_price;
        } else
            throw new IllegalArgumentException("Invalid order type");
        newPrice /= (quantity + 1);
        company.setStockPrice(newPrice, current_price);
    }

    private double generateRandomFactor() {
        Random random = new Random();
        double minFactor = 0.0;
        double maxFactor = 0.2;
        return minFactor + random.nextDouble() * (maxFactor - minFactor);
    }
}
