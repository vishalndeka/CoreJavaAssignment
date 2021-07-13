public class App {
    public static void main(String[] args) throws Exception {
        Account a1 = new SavingsAccount(1234, 500000, "abc123", 6000);
        a1.setBankName("Indian Bank");
        a1.withdraw(200000);

        CurrentAccount a2 = new CurrentAccount(9954, 30000, "lti999", 3000);
        a2.withdraw(35000);

        a2.withdraw(33000);
    }
}

abstract class Account{
    private int accountNo;
    private double accountBalance;
    private String accountPassword;

    private static String bankName;

    public Account(int accountNo, double accountBalance, String accountPassword){
        this.accountNo = accountNo;
        this.accountBalance = accountBalance;
        this.accountPassword = accountPassword;
    }

    public int getAccountNo(){return this.accountNo;}
    public double getAccountBalance(){return this.accountBalance;}
    public String getAccountPassword() {return this.accountPassword;}
    public static String getBankName() {return Account.bankName;}

    public void setAccountBalance(double newAccountBalance){this.accountBalance = newAccountBalance;}
    public void setBankName(String bankName){Account.bankName = bankName;}

    public void updateAccountPassword(String oldPassword, String newPassword){
        if(oldPassword == this.accountPassword){
            this.accountPassword = newPassword;
            System.out.println("Password changed!");
        } else {
            System.out.println("Incorrect old password! Contact bank to recover or try again.");
        }
    }

    public void displayAccount(){
            System.out.println("Your bank is : " + Account.bankName);
            System.out.println("Your account number : " +this.accountNo);
            System.out.println("Your account balance : " +this.accountBalance);
            System.out.println("Your account password : " +this.accountPassword);
    }

    abstract void withdraw(double amount);
}

class SavingsAccount extends Account {
    private double minimumBalance;

    SavingsAccount(int accountNo, double accountBalance, String accountPassword, double minimumBalance){
        super(accountNo, accountBalance, accountPassword);
        if(minimumBalance <= this.getAccountBalance()){
            this.minimumBalance = minimumBalance;
        } else {
            System.out.println("Account balance is less than minimum balance "+ minimumBalance +"! Try again.");
            return;
        }
    }

    public double getMinimumBalance(){return this.minimumBalance;}

    public void setMinimumBalance(double newMinimumBalance){
        if(newMinimumBalance <= this.getAccountBalance()){
            this.minimumBalance = newMinimumBalance;
        } else {
            System.out.println("Account balance is less than new minimum balance "+ newMinimumBalance +"! Try again.");
        }
    }

    @Override
    public void displayAccount() {
            System.out.println("Your bank is : " + Account.getBankName());
            System.out.println("Your account number : " +this.getAccountNo());
            System.out.println("Your account balance : " +this.getAccountBalance());
            System.out.println("Your account password : " +this.getAccountPassword());
            System.out.println("Your minimum account balance : " +this.minimumBalance);
    }

    @Override
    void withdraw(double amount) {
        if(amount <= this.getAccountBalance() - this.minimumBalance){
            // valid withdraw
            this.setAccountBalance(this.getAccountBalance() - amount);
            System.out.println("Account Balance after withdrawing " + amount + ": " + this.getAccountBalance());
        }
        
    }
}

class CurrentAccount extends Account {
    private double overDraftLimitAmount;

    CurrentAccount(int accountNo, double accountBalance, String accountPassword, double overDraftLimitAmount){
        super(accountNo, accountBalance, accountPassword);
        if(overDraftLimitAmount <= 0.1 * this.getAccountBalance()){
            this.overDraftLimitAmount = overDraftLimitAmount;
        } else {
            System.out.println("Too large overdraft limit :" + overDraftLimitAmount + ". Try again");
            return;
        }
    }

    public void setOverDraftLimit(double newOverDraftLimitAmount) {
        if(newOverDraftLimitAmount <= 0.1 * this.getAccountBalance()){ // assuming 10% of balance can be set as overdraft amount
            this.overDraftLimitAmount = newOverDraftLimitAmount;
            System.out.println("New overdraft limit set");
        } else {
            System.out.println("Not eligible for overdraft limit :" + newOverDraftLimitAmount + ". Try again");
        }
    }

    public double getOverDraftLimit(double overDraftLimitAmount) {
        return this.overDraftLimitAmount;
    }

    @Override
    public void displayAccount() {
            System.out.println("Your bank is : " + Account.getBankName());
            System.out.println("Your account number : " +this.getAccountNo());
            System.out.println("Your account balance : " +this.getAccountBalance());
            System.out.println("Your account password : " +this.getAccountPassword());
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
