abstract class Securities {
    int availablestocks;
    String lable;
    String companyName;
    double Maximumprice;
    double Minimumprice;
    int shareholders;
    double totaldividens;

    public void setTotaldividens(double totaldividens) {
        this.totaldividens = totaldividens;
    }

    public double getTotaldividens() {
        return totaldividens;
    }

    public void setShareholders(int shareholders) {
        this.shareholders = shareholders;
    }

    public int getShareholders() {
        return shareholders;
    }

    public void setMaximumprice(double Maximumprice) {
        this.Maximumprice = Maximumprice;
    }

    public double getMaximumprice() {
        return Maximumprice;
    }

    public void setMinimumprice(double Minimumprice) {
        this.Minimumprice = Minimumprice;
    }

    public double getMinimumprice() {
        return Minimumprice;
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

    double Calculate_dividen() {
        return 0;
    }

    double Calculate_dividen_Yield() {
        return 0;
    }
}
