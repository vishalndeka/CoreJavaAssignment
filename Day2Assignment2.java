public class App {
    public static void main(String[] args) throws Exception {
        SavingsAccount a1 = new SavingsAccount(1234, 1000, "abc", 300);
        CurrentAccount a2 = new CurrentAccount(9876, 50000, "xyz123", 5000);

        a1.withdraw(650);
        a2.withdraw(56000);
        a2.withdraw(51000);
    }
}

abstract class Account {
    private int accountNo;

    private static String bankName;

    public Account(int accountNo){
        this.accountNo = accountNo;
    }

    abstract void withdraw(double amount);

    public int getAccountNo(){ return this.accountNo; }
    
    public void setBankName(String bankName){ Account.bankName = bankName; }

    public static String getBankName() { return Account.bankName; }

    public void displayAccount(){
        System.out.println("Your bank is : " + Account.bankName);
        System.out.println("Your account number : " +this.accountNo);
    }

}

class SavingsAccount extends Account {
    private double accountBalance;
    private String accountPassword;
    private double minimumBalance;

    SavingsAccount(int accountNo, double accountBalance, String accountPassword, double minimumBalance){
        super(accountNo);
        this.accountBalance = accountBalance;
        this.accountPassword = accountPassword;

        if(minimumBalance <= this.getAccountBalance() && minimumBalance >= 0){
            this.minimumBalance = minimumBalance;
        } else {
            System.out.println("Account balance is less than minimum balance "+ minimumBalance +"! Try again.");
            return;
        }
    }

    public double getAccountBalance(){ return this.accountBalance; }
    public double getMinimumBalance(){ return this.minimumBalance; }

    private void setAccountBalance(double accountBalance){
        this.accountBalance = accountBalance;
    }
    public void setMinimumBalance(double newMinimumBalance){
        if(newMinimumBalance <= this.getAccountBalance()){
            this.minimumBalance = newMinimumBalance;
        } else {
            System.out.println("Account balance is less than new minimum balance "+ newMinimumBalance +"! Try again.");
        }
    }

    public void updateAccountPassword(String oldPassword, String newPassword){
        if(oldPassword == this.accountPassword){
            this.accountPassword = newPassword;
            System.out.println("Password changed!");
        } else {
            System.out.println("Incorrect old password! Contact bank to recover or try again.");
        }
    }

    @Override
    public void displayAccount() {
         System.out.println("Your bank is : " + Account.getBankName());
         System.out.println("Your account number : " +this.getAccountNo());
         System.out.println("Your account balance : " +this.getAccountBalance());
         System.out.println("Your minimum account balance : " +this.minimumBalance);
    }

    @Override
    void withdraw(double amount){
        if(amount <= this.getAccountBalance() - this.minimumBalance){
            // valid withdraw
            this.setAccountBalance(this.getAccountBalance() - amount);
            System.out.println("Account Balance after withdrawing " + amount + ": " + this.getAccountBalance());
        } else {
            System.out.println("Insufficient funds");
        }
    }
}

class CurrentAccount extends Account {
    private double accountBalance;
    private String accountPassword;
    private double overDraftLimitAmount;

    CurrentAccount(int accountNo, double accountBalance, String accountPassword, double overDraftLimitAmount){
        super(accountNo);
        this.accountBalance = accountBalance;
        this.accountPassword = accountPassword;

        if(overDraftLimitAmount <= 0.1 * this.getAccountBalance() && overDraftLimitAmount >= 0){
            this.overDraftLimitAmount = overDraftLimitAmount;
        } else {
            System.out.println("Not eligible for overdraft limit :" + overDraftLimitAmount + ". Try again");
            return;
        }
    }

    public double getAccountBalance(){ return this.accountBalance; }
    public double getOverDraftLimit(double overDraftLimitAmount) { return this.overDraftLimitAmount; }

    private void setAccountBalance(double accountBalance){
        this.accountBalance = accountBalance;
    }
    public void setOverDraftLimit(double newOverDraftLimitAmount) {
        if(newOverDraftLimitAmount <= 0.1 * this.getAccountBalance()){ // assuming 10% of balance can be set as overdraft amount
            this.overDraftLimitAmount = newOverDraftLimitAmount;
            System.out.println("New overdraft limit set");
        } else {
            System.out.println("Not eligible for overdraft limit :" + newOverDraftLimitAmount + ". Try again");
        }
    }

    public void updateAccountPassword(String oldPassword, String newPassword){
        if(oldPassword == this.accountPassword){
            this.accountPassword = newPassword;
            System.out.println("Password changed!");
        } else {
            System.out.println("Incorrect old password! Contact bank to recover or try again.");
        }
    }

    @Override
    public void displayAccount() {
         System.out.println("Your bank is : " + Account.getBankName());
         System.out.println("Your account number : " +this.getAccountNo());
         System.out.println("Your account balance : " +this.getAccountBalance());
         System.out.println("Your overdraft limit : " +this.overDraftLimitAmount);
    }

    @Override
    void withdraw(double amount) {
        if(this.getAccountBalance() - amount >= -this.overDraftLimitAmount){
            // valid withdraw
            this.setAccountBalance(this.getAccountBalance() - amount);
            System.out.println("Account Balance after withdrawing " + amount + ": " + this.getAccountBalance());
        } else {
            System.out.println("Withdraw amount exceeds overdraft limit!");
        }
    }
}
