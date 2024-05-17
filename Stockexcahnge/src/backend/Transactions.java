package backend;

import java.time.LocalDate;

public class Transactions {
    private String username;
    private String typeOfTransaction;
    private LocalDate date;
    private double amount;
    private double currentBalance;
    private double newBalance ;
    private String status;

    public Transactions(String username, String typeOfTransaction, LocalDate date, double amount, double currentBalance, double newBalance, String status) {
        this.username = username;
        this.typeOfTransaction = typeOfTransaction;
        this.date = date;
        this.amount = amount;
        this.currentBalance = currentBalance;
        this.newBalance = newBalance;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
