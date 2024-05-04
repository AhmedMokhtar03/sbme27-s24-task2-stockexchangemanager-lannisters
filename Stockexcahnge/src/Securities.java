abstract class Securities {
    int availablestocks;
    String lable;
    String companyName;
    double totaldividens;

    public void setTotaldividens(double totaldividens) {
        this.totaldividens = totaldividens;
    }

    public double getTotaldividens() {
        return totaldividens;
    }
    public void setLabel(String label) {
        this.lable = label;
    }

    public String getLabel() {
        return lable;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    abstract double Calculate_dividend();

    abstract double Calculate_dividend_Yield();
}
