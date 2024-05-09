package backend;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DataManager {
    private static final String companiesFile = "companies.csv";
    private static final String usersFile = "users.csv";
    private static final String PRICE_HISTORY_DIR = "price_history";
    public static void saveCompanies(Company company) {
        try {
            File file = new File(companiesFile);
            List<String> lines = new ArrayList<>();
            // Read the existing data from the CSV file
            if (file.exists()) {
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] data = line.split(",");
                        if (!data[1].equalsIgnoreCase(company.getLabel())) {
                            lines.add(line);
                        }
                    }
                }
            }
            // Add the updated company data to the lines
            String line = company.getName() + "," + company.getLabel() + "," + company.getID() + "," + company.getStockPrice() + "," + company.getDividends() + "," + company.getNumOfAvailableStocks();
            lines.add(line);
            // Write the updated lines to the CSV file
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                for (String l : lines) {
                    bw.write(l);
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void savePriceHistory(Company company) {
        String fileName = company.getLabel() + ".csv"; // Use the company label as the file name
        File file = new File(fileName);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            // Write the header row
            bw.write("Date,OpeningPrice,ClosingPrice,HighestPrice,LowestPrice");
            bw.newLine();
            // Write each price history entry to the CSV file
            for (Map<String, Double> entry : company.getPriceHistory()) {
                long dateMillis = entry.get("date").longValue();
                Date date = new Date(dateMillis);
                double openingPrice = entry.get("OpeningPrice");
                double closingPrice = entry.get("closingPrice");
                double highestPrice = entry.get("HighestPrice");
                double lowestPrice = entry.get("LowestPrice");
                String line = String.format("%s,%.2f,%.2f,%.2f,%.2f", date, openingPrice, closingPrice, highestPrice, lowestPrice);
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    }


