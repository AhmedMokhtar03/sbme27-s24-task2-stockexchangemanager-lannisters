package backend;

abstract class Securities {
   protected String label;
   public double totalDividends;

    public void setTotalDividends(double totalDividens) {
        this.totalDividends = totalDividens;
    }

    public double getTotalDividends() {
        return totalDividends;
    }
    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    abstract double Calculate_dividend();

    abstract double Calculate_dividend_Yield();
}
