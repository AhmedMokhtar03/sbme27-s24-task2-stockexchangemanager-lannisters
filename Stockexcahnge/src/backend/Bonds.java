package backend;


import java.time.LocalDate;
import java.time.Period;

public class Bonds extends Securities{
private String company;
private double bondPrice;
private double bondInterest;
private Period duration;
private LocalDate date;


    public Bonds(String company, double bondPrice, double bondInterest, Period duration) {
        this.company = company;
        this.bondPrice = bondPrice;
        this.bondInterest = bondInterest;
        this.duration = duration;



    }

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
