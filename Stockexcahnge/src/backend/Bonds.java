package backend;

import java.time.LocalDate;
import java.time.Period;

public class Bonds extends Securities {
    private String company;
    private double bondPrice;
    private double bondInterest;
    private Period duration;
    private int quantity;
//==============================
private String userName;
private int numberOfOwnedBonds;
private LocalDate maturityDate;
private double totalReturnedMoney;

    public Bonds(String userName, String company, int numberOfOwnedBonds, LocalDate maturityDate, double totalReturnedMoney) {
        this.userName = userName;
        this.company = company;
        this.numberOfOwnedBonds = numberOfOwnedBonds;
        this.maturityDate = maturityDate;
        this.totalReturnedMoney = totalReturnedMoney;
    }

    public Bonds(String company, double bondPrice, double bondInterest, Period duration, int quantity) {
        this.company = company;
        this.bondPrice = bondPrice;
        this.bondInterest = bondInterest;
        this.duration = duration;
        this.quantity = quantity;
    }

    // Getters and Setters
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public double getBondPrice() {
        return bondPrice;
    }

    public void setBondPrice(double bondPrice) {
        this.bondPrice = bondPrice;
    }

    public double getBondInterest() {
        return bondInterest;
    }

    public void setBondInterest(double bondInterest) {
        this.bondInterest = bondInterest;
    }

    public Period getDuration() {
        return duration;
    }

    public void setDuration(Period duration) {
        this.duration = duration;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getNumberOfOwnedBonds() {
        return numberOfOwnedBonds;
    }

    public void setNumberOfOwnedBonds(int numberOfOwnedBonds) {
        this.numberOfOwnedBonds = numberOfOwnedBonds;
    }

    public LocalDate getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(LocalDate maturityDate) {
        this.maturityDate = maturityDate;
    }

    public double getTotalReturnedMoney() {
        return totalReturnedMoney;
    }

    public void setTotalReturnedMoney(double totalReturnedMoney) {
        this.totalReturnedMoney = totalReturnedMoney;
    }
}

