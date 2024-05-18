package backend;

import java.time.Period;

public class Bonds extends Securities {
    private String company;
    private double bondPrice;
    private double bondInterest;
    private Period duration;
    private int quantity;

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
}
