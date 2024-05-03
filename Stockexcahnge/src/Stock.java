import java.util.*;

public class Stock extends Securities {
    double current_price;
    int no_of_stocks;
    int available_stocks;
    private List<Map<String, Double>> priceHistory = new ArrayList<Map<String, Double>>();

    public Stock(String label, int no_of_stocks, String state)//this constructor is to be used if the user wants to buy a stock state=sell||buy
    {
        switch (state) {
            case "buy":
                this.no_of_stocks = no_of_stocks;
                update_available_stocks(no_of_stocks);
                break;
            case "sell":
                this.no_of_stocks = (-1) * no_of_stocks;
                update_available_stocks(no_of_stocks);
                break;
            case "new":
                this.available_stocks = no_of_stocks;
                break;
            default:
                throw new RuntimeException("Invalid state");
        }

    }

    public void add_price_entry(Date date, double OpeningPrice, double closingPrice) //when there's a price change (from the admin) call this method and pass the appropriate date
    {
        Map<String, Double> entry = new HashMap<String, Double>();
        entry.put("date", (double) date.getTime());
        entry.put("OpeningPrice", OpeningPrice);
        entry.put("closingPrice", closingPrice);
        entry.put("HighestPrice", getMaximumprice());
        entry.put("LowestPrice", getMinimumprice());
        priceHistory.add(entry);
    }

    public List<Map<String, Double>> getPriceHistory() //when the user asks for the list of the price history, call this method
    {
        return priceHistory;
    }

    @Override
    double Calculate_dividend() {
        return (getTotaldividens() / getShareholders());
    }

    @Override
    double Calculate_dividend_Yield() {
        return (Calculate_dividend() / current_price) * 100;
    }

    public void update_available_stocks(int no_of_stocks) //only call this method when you approve the user's request to buy or sell a stock
    {
        availablestocks -= no_of_stocks;
    }
}
