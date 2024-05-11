package backend;

import frontendmalak.ViewControl.UserManagementController;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static frontendmalak.ViewControl.UserManagementController.userList;

public class DataManager {
    private static final String companiesFile = "companies.csv";
    private static final String usersFile = "users.csv";
    private static final String PRICE_HISTORY_DIR = "price_history";
    public static ArrayList<User> users = new ArrayList<>();
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
    public static void loadUsersFromCSV() {
         final String CSV_FILE = "Stockexcahnge/src/frontendmalak/users.csv";

        userList.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 6) {
                int id = Integer.parseInt(parts[0]);
                String username = parts[1];
                String password = parts[2];
                double cashBalance = Double.parseDouble(parts[3]);
                boolean isPremium = Boolean.parseBoolean(parts[4]);
                LocalDate firstDateOfPremium = null;
                if (!parts[5].equals("null")) { // Check if date string is not 'null'
                    firstDateOfPremium = LocalDate.parse(parts[5]); // Assuming date is in ISO format
                }

                User user = new User(username, password);
                user.setID(id);
                user.setCashBalance(cashBalance);
                user.setPremium(isPremium);
                if (firstDateOfPremium != null) {
                    user.setFirstDateOfPremium(firstDateOfPremium);
                } else {

                    //wethar set it to arbitery date or leave it as null
                    //       user.setFirstDateOfPremium(LocalDate.MIN); // Set an arbitrary date
                    user.setFirstDateOfPremium(null);
                }

                userList.add(user);
            }
        }
    } catch (IOException e) {
        UserManagementController.showAlert("Error", "Failed to load users from CSV file.");
        e.printStackTrace();
    }
}
    /*
    public static void loadUsersFromCSV(String csvFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            br.readLine(); // Skip the header row
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                String username = fields[1];
                String password = fields[2];
                for(User u : users){
                    if(username.equals(u.getUserName())){
                        continue;
                    }
                    User user = new User(username, password);
                    user.setID(user.hashCode());
                    users.add(user);
                }

            }
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
    }
*/
    }


