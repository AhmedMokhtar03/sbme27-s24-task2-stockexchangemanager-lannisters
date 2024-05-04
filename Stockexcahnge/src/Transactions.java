



public class Transactions {
    private double depositValue;
    private double withdrawValue;
    private double profitPercentage;
    private static User currentUser;

    public Transactions(double depositValue, double withdrawValue) {
        this.depositValue = depositValue;
        this.withdrawValue = withdrawValue;
    }

    public double getDepositValue() {
        return depositValue;
    }

    public void setDepositValue(double depositValue) {
        this.depositValue = depositValue;
    }

    public double getWithdrawValue() {
        return withdrawValue;
    }

    public void setWithdrawValue(double withdrawValue) {
        this.withdrawValue = withdrawValue;
    }
    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    private User getCurrentUser() {
        return currentUser;
    }

    public void setProfitPercentage(double initialValue) {
        if (initialValue <= 0) {
            throw new IllegalArgumentException("Initial value must be positive for profit calculation.");
        }
        double profit = depositValue - withdrawValue - initialValue;
        profitPercentage = (profit / initialValue) * 100;
        System.out.println("Profit Percentage: " + profitPercentage + "%");
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }
        if (amount > withdrawValue) {
                throw new InsufficientFundsException("Insufficient funds for withdrawal.");
        }
        withdrawValue -= amount;
        User currentUser = getCurrentUser();
        currentUser.setCashBalance(currentUser.getCashBalance() - amount);
        System.out.println("Withdrawal successful. New balance: " + currentUser.getCashBalance());
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        depositValue += amount;
        User currentUser = getCurrentUser();
        currentUser.setCashBalance(currentUser.getCashBalance() + amount);
        System.out.println("Deposit successful. New balance: " + currentUser.getCashBalance());
    }

}
