public class App {
    public static void main(String[] args) throws Exception {
        Account a1 = new Account(1234, 50000.00, "abc123");
        a1.setBankName("Bank of Baroda"); // setting bank name
        System.out.println("Account No. : " +a1.getAccountNo()); // using getter
        System.out.println("Account Balance : " + a1.getAccountBalance()); // using getter
        
        a1.updateAccountPassword("abc123", "xyz789");

        a1.displayAccount(); // displaying acc details through 'displayAccount()'

        SavingsAccount a2 = new SavingsAccount(9999, 1000000, "lti1912", 50000);

        a2.displayAccount();

        CurrentAccount a3 = new CurrentAccount(6666, 250000, "get940", 10000);

        a3.displayAccount();
    }
}

class Account {
    private int accountNo;
    private double accountBalance;
    private String accountPassword;

    private static String bankName;

    public Account(int accountNo, double accountBalance, String accountPassword){
        this.accountNo = accountNo;
        this.accountBalance = accountBalance;
        this.accountPassword = accountPassword;
    }

    public int getAccountNo(){
        return this.accountNo;
    }
    public double getAccountBalance(){
        return this.accountBalance;
    }

    public void updateAccountPassword(String oldPassword, String newPassword){
        if(oldPassword == this.accountPassword){
            this.accountPassword = newPassword;
            System.out.println("Password changed!");
        } else {
            System.out.println("Incorrect old password! Contact bank to recover or try again.");
        }
    }

    public void setBankName(String bankName){
        Account.bankName = bankName;
    }

    public static String getBankName() {
        return Account.bankName;
    }

    public void displayAccount(){
        System.out.println("Your bank is : " + Account.bankName);
        System.out.println("Your account number : " +this.accountNo);
        System.out.println("Your account balance : " +this.accountBalance);
    }

}

class SavingsAccount extends Account {
    private double minimumBalance;

    SavingsAccount(int accountNo, double accountBalance, String accountPassword, double minimumBalance){
        super(accountNo, accountBalance, accountPassword);
        if(minimumBalance <= this.getAccountBalance()){
            this.minimumBalance = minimumBalance;
        } else {
            System.out.println("Account balance is less than minimum balance "+ minimumBalance +"! Try again.");
        }
    }

    public double getMinimumBalance(){
        return this.minimumBalance;
    }

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
         System.out.println("Your minimum account balance : " +this.minimumBalance);
    }
}

class CurrentAccount extends Account {
    private double overDraftLimitAmount;

    CurrentAccount(int accountNo, double accountBalance, String accountPassword, double overDraftLimitAmount){
        super(accountNo, accountBalance, accountPassword);
        if(overDraftLimitAmount <= 0.1 * this.getAccountBalance()){
            this.overDraftLimitAmount = overDraftLimitAmount;
        } else {
            System.out.println("Not eligible for overdraft limit :" + overDraftLimitAmount + ". Try again");
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
         System.out.println("Your overdraft limit : " +this.overDraftLimitAmount);
    }
}
