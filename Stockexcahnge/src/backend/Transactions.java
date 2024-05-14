package backend;

import java.time.LocalDate;

public class Transactions {
    private String username;
    private String typeOfTransaction;
    private LocalDate date;
    private double amount;
    private double currentBalance;
    private double newBalance ;
    private boolean decision;

    public Transactions(String username, String typeOfTransaction, LocalDate date, double amount, double currentBalance, double newBalance) {
        this.username = username;
        this.typeOfTransaction = typeOfTransaction;
        this.date = date;
        this.amount = amount;
        this.currentBalance = currentBalance;
        this.newBalance = newBalance;
        this.decision = false;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTypeOfTransaction() {
        return typeOfTransaction;
    }

    public void setTypeOfTransaction(String typeOfTransaction) {
        this.typeOfTransaction = typeOfTransaction;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public double getNewBalance() {
        return newBalance;
    }

    public void setNewBalance(double newBalance) {
        this.newBalance = newBalance;
    }

    public boolean getDecision() {
        return decision;
    }

    public void setDecision(boolean decision) {
        this.decision = decision;
    }
//    public void setProfitPercentage(double initialValue) {
//        if (initialValue <= 0) {
//            throw new IllegalArgumentException("Initial value must be positive for profit calculation.");
//        }
//        double profit = depositValue - withdrawValue - initialValue;
//        profitPercentage = (profit / initialValue) * 100;
//        System.out.println("Profit Percentage: " + profitPercentage + "%");
//    }
//
//    public void withdraw(double amount) {
//        if (amount <= 0) {
//            throw new IllegalArgumentException("Withdrawal amount must be positive.");
//        }
//        if (amount > withdrawValue) {
//                throw new RuntimeException("Withdrawal amount exceeds withdraw limit.");
//        }
//        withdrawValue -= amount;
//
//
//    }
//
//    public void deposit(double amount) {
//        if (amount <= 0) {
//            throw new IllegalArgumentException("Deposit amount must be positive.");
//        }
//        depositValue += amount;
//        User currentUser = getCurrentUser();
//        currentUser.setCashBalance(currentUser.getCashBalance() + amount);
//        System.out.println("Deposit successful. New balance: " + currentUser.getCashBalance());
//    }

}
