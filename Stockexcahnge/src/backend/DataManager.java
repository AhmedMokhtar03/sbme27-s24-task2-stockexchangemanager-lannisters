package backend;

import frontendmalak.ViewControl.AdminMangeUsersController;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

import static frontendmalak.ViewControl.AdminMangeUsersController.userList;

public class DataManager {
    private static final String companiesFile = "companies.csv";
    private static final String usersFile = "users.csv";
    public static ArrayList<Company> companyList;
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
    public static void loadCompanies() {
        companyList = new ArrayList<>();
        try {
            File file = new File(companiesFile);
            if (file.exists()) {
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] data = line.split(",");
                        if (data.length == 6) {
                            String name = data[0];
                            String label = data[1];
                            int id = Integer.parseInt(data[2]);
                            double stockPrice = Double.parseDouble(data[3]);
                            double dividends = Double.parseDouble(data[4]);
                            int numOfAvailableStocks = Integer.parseInt(data[5]);
                            Company company = new Company(name, label, id, stockPrice, dividends, numOfAvailableStocks);
                            companyList.add(company);
                        }
                    }
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
            bw.write("Date,OpeningPrice,ClosingPrice,LowestPrice,HighestPrice");
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
    public static void loadUsersFromCSV() {
        final String CSV_FILE = "Stockexcahnge/src/frontendmalak/users.csv";
        userList.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            int columnOffset = 6; // Offset to account for the basic user info columns
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= columnOffset + DataManager.companyList.size()) {
                    int id = Integer.parseInt(parts[0]);
                    String username = parts[1];
                    String password = parts[2];
                    double cashBalance = Double.parseDouble(parts[3]);
                    boolean isPremium = Boolean.parseBoolean(parts[4]);
                    LocalDate firstDateOfPremium = null;
                    if (!parts[5].equals("null")) { // Check if parts[5] is not "null"
                        firstDateOfPremium = LocalDate.parse(parts[5]);
                    }

                    User user = new User(username, password);
                    user.setID(id);
                    user.setCashBalance(cashBalance);
                    user.setPremium(isPremium);
                    user.setFirstDateOfPremium(firstDateOfPremium);

                    Map<String, Integer> ownedStocks = new HashMap<>();
                    for (int i = 0; i < DataManager.companyList.size(); i++) {
                        Company company = DataManager.companyList.get(i);
                        int quantity = Integer.parseInt(parts[columnOffset + i]);
                        if (quantity > 0) {
                            ownedStocks.put(company.getLabel(), quantity);
                        }
                    }
                    user.setOwnedStocks(ownedStocks);

                    userList.add(user);
                }
            }
        } catch (IOException e) {
            AdminMangeUsersController.showAlert("Error", "Failed to load users from CSV file.");
            e.printStackTrace();
        }
    }
    public static void saveUsersToCSV() {
        final String CSV_FILE = "Stockexcahnge/src/frontendmalak/users.csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(CSV_FILE))) {
            for (User user : userList) {
                writer.println(user.toCSV());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }

