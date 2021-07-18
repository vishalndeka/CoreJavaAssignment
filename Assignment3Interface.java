import java.util.ArrayList;

public class App {
    public static void main(String[] args) throws Exception {
        SavingsAccount a1 = new SavingsAccount(1234, "ICICI", 1000, "abc", 300);
        CurrentAccount a2 = new CurrentAccount(3451, "ICICI", 2000, "xyz123", 50);
        
        SbiATM stateBankAtm = new SbiATM();
        IciciATM iciciAtm = new IciciATM();
        
        iciciAtm.withdraw(1234, 500); // withdrawing from a1
        iciciAtm.withdraw(3451, 2100); // withdrawing from a2 - exceeding overdraft limit
        iciciAtm.changePassword(1234, "abc", "def"); // change passowrd of a1
        iciciAtm.checkBalance(3451); // checking balance of a2

        stateBankAtm.withdraw(1234, 200); // 1234 is an account at ICICI, hence this shows account not found message

        System.out.println("------------------------");
        // verifying whether changes made by ATM reflect or not:
        a1.displayAccount();
        a2.displayAccount();
    }


}


abstract class Account {
    private int accountNo;
    private String bankName;

    public static ArrayList<Account> sbiBank = new ArrayList<>();
    public static ArrayList<Account> iciciBank = new ArrayList<>();

    public Account(int accountNo, String bankName){
        this.accountNo = accountNo;
        this.bankName = bankName;
        if(bankName == "SBI"){
            sbiBank.add(this);
        } else if (bankName == "ICICI") {
            iciciBank.add(this);
        }
    }

    abstract void withdraw(double amount);
    abstract void updateAccountPassword(String oldPassword, String newPassword);
    abstract double getAccountBalance();

    public int getAccountNo(){ return this.accountNo; }
    
    //public static void setBankName(String bankName){ Account.bankName = bankName; }

    public String getBankName() { return this.bankName; }

    public void displayAccount(){
        System.out.println("Your bank is : " + this.bankName);
        System.out.println("Your account number : " +this.accountNo);
    }
}

class SavingsAccount extends Account {
    private double accountBalance;
    private String accountPassword;
    private double minimumBalance;

    SavingsAccount(int accountNo, String bankName, double accountBalance, String accountPassword, double minimumBalance){
        super(accountNo, bankName);
        this.accountBalance = accountBalance;
        this.accountPassword = accountPassword;

        if(minimumBalance <= this.getAccountBalance() && minimumBalance >= 0){
            this.minimumBalance = minimumBalance;
        } else {
            System.out.println("Account balance is less than minimum balance "+ minimumBalance +"! Try again.");
            return;
        }
    }

    @Override
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

    @Override
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
         System.out.println("Your bank is : " + this.getBankName());
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

    CurrentAccount(int accountNo, String bankName, double accountBalance, String accountPassword, double overDraftLimitAmount){
        super(accountNo, bankName);
        this.accountBalance = accountBalance;
        this.accountPassword = accountPassword;

        if(overDraftLimitAmount <= 0.1 * this.getAccountBalance() && overDraftLimitAmount >= 0){
            this.overDraftLimitAmount = overDraftLimitAmount;
        } else {
            System.out.println("Not eligible for overdraft limit :" + overDraftLimitAmount + ". Try again");
            return;
        }
    }

    @Override
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

    @Override
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
         System.out.println("Your bank is : " + this.getBankName());
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

interface ATM {
    void withdraw(int accountNo, double amount);
    void changePassword(int accountNo, String oldPassword, String newPassword);
    void checkBalance(int accountNo);
}

class SbiATM implements ATM {
    
    @Override
    public void withdraw(int accountNo, double amount) {
        boolean accountFound = false;
        for(Account a : Account.sbiBank){
            if(a.getAccountNo() == accountNo){
                accountFound = true;
                a.withdraw(amount);
                break;
            }
        }
        if(!accountFound){
            System.out.println("Account not found!");
        }
    }

    @Override
    public void changePassword(int accountNo, String oldPassword, String newPassword) {
        boolean accountFound = false;
        for(Account a : Account.sbiBank){
            if(a.getAccountNo() == accountNo){
                accountFound = true;
                a.updateAccountPassword(oldPassword, newPassword);
                break;
            }
        }
        if(!accountFound){
            System.out.println("Account not found!");
        }
    }

    @Override
    public void checkBalance(int accountNo) {
        boolean accountFound = false;
        for(Account a : Account.sbiBank){
            if(a.getAccountNo() == accountNo){
                accountFound = true;
                System.out.println("Your account balance is : " +a.getAccountBalance());
                break;
            }
        }
        if(!accountFound){
            System.out.println("Account not found!");
        }
    }
}

class IciciATM implements ATM {
    @Override
    public void withdraw(int accountNo, double amount) {
        boolean accountFound = false;
        for(Account a : Account.iciciBank){
            if(a.getAccountNo() == accountNo){
                accountFound = true;
                a.withdraw(amount);
                break;
            }
        }
        if(!accountFound){
            System.out.println("Account not found!");
        }
    }

    @Override
    public void changePassword(int accountNo, String oldPassword, String newPassword) {
        boolean accountFound = false;
        for(Account a : Account.iciciBank){
            if(a.getAccountNo() == accountNo){
                accountFound = true;
                a.updateAccountPassword(oldPassword, newPassword);
                break;
            }
        }
        if(!accountFound){
            System.out.println("Account not found!");
        }
    }

    @Override
    public void checkBalance(int accountNo) {
        boolean accountFound = false;
        for(Account a : Account.iciciBank){
            if(a.getAccountNo() == accountNo){
                accountFound = true;
                System.out.println("Your account balance is : " +a.getAccountBalance());
                break;
            }
        }
        if(!accountFound){
            System.out.println("Account not found!");
        }    
    }
}
