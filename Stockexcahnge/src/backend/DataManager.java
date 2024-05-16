package backend;

import frontendmalak.ViewControl.AdminManageUsersController;
import frontendmalak.ViewControl.LogIn;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import static frontendmalak.ViewControl.AdminManageUsersController.userList;
import static frontendmalak.ViewControl.UserTransactions.TransactionsList;

public class DataManager {
    private static final String SUBSCRIPTION_FILE = "subscriptions.csv";
    private static final String companiesFile = "companies.csv";
    private static final String CSV_FILE_PATH = "Stockexcahnge/src/frontendmalak/transactions.csv";
    private static final String CSV_FILE = "Stockexcahnge/src/frontendmalak/users.csv";
    public static ObservableList<Company> companyList;
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
        companyList= FXCollections.observableArrayList();
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
    public static void loadPriceHistoryFromCSV() {
        for (Company company : companyList) {
            String fileName = company.getLabel() + ".csv"; // Use the company label as the file name
            File file = new File(fileName);

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                br.readLine(); // Skip the header row

                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 5) {
                        Date date = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(parts[0]);
                        double openingPrice = Double.parseDouble(parts[1]);
                        double closingPrice = Double.parseDouble(parts[2]);
                        double highestPrice = Double.parseDouble(parts[3]);
                        double lowestPrice = Double.parseDouble(parts[4]);

                        Map<String, Double> priceHistoryEntry = new HashMap<>();
                        priceHistoryEntry.put("date", (double) date.getTime()); // Store date as milliseconds since epoch
                        priceHistoryEntry.put("OpeningPrice", openingPrice);
                        priceHistoryEntry.put("closingPrice", closingPrice);
                        priceHistoryEntry.put("HighestPrice", highestPrice);
                        priceHistoryEntry.put("LowestPrice", lowestPrice);

                        company.getPriceHistory().add(priceHistoryEntry);
                    }
                }
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        }
    }
    public static void loadUsersFromCSV() {
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
            AdminManageUsersController.showAlert("Error", "Failed to load users from CSV file.");
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
    public static void loadTransactionsFromCSV() {
        TransactionsList.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String username = parts[0];
                String typeOfTransaction = parts[1];
                LocalDate date = LocalDate.parse(parts[2]);
                double amount = Double.parseDouble(parts[3]);
                double currentBalance = Double.parseDouble(parts[4]);
                double newBalance = Double.parseDouble(parts[5]);

                Transactions transaction = new Transactions(username, typeOfTransaction, date, amount, currentBalance, newBalance);
                TransactionsList.add(transaction);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void saveTransactionsToCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_PATH))) {
            for (Transactions transaction : TransactionsList) {
                writer.write(transaction.getUsername() + "," +
                        transaction.getTypeOfTransaction() + "," +
                        transaction.getDate() + "," +
                        transaction.getAmount() + "," +
                        transaction.getCurrentBalance() + "," +
                        transaction.getNewBalance());
                writer.newLine();
            }
            System.out.println("Transactions saved successfully to CSV file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static int countRowsInCSV() {
        int rowCount = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            while (br.readLine() != null) {
                rowCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rowCount;
    }
    public static void saveSubscriptions(Map<String, List<String>> subscriptions) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SUBSCRIPTION_FILE))) {
            for (Map.Entry<String, List<String>> entry : subscriptions.entrySet()) {
                String username = entry.getKey();
                List<String> subscribedCompanyLabels = entry.getValue();
                String line = username + "," + String.join(",", subscribedCompanyLabels);
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Map<String, List<String>> loadSubscriptions() {
        User currentUser= LogIn.currentUser;
        Map<String, List<String>> subscriptions = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(SUBSCRIPTION_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String username = parts[0];
                List<String> subscribedCompanyLabels = new ArrayList<>(Arrays.asList(parts).subList(1, parts.length));
                subscriptions.put(username, subscribedCompanyLabels);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(Map.Entry<String, List<String>> entry : subscriptions.entrySet()) {
//            for(User user : userList) {
//                if(user.getUserName().equalsIgnoreCase(username)){
//                    currentUser = user;
//                }
//            }
            if(entry.getKey().equalsIgnoreCase(currentUser.getUserName())){
            for(String companies : entry.getValue()) {
                for(Company company : companyList) {
                    if(company.getLabel().equalsIgnoreCase(companies)){
                        company.addObserver(currentUser);
                        //assert currentUser != null;
                        System.out.println(currentUser.getUserName());
                        currentUser.isSubscribed = true;
                    }
                }
            }
        }}
        return subscriptions;
    }
}


