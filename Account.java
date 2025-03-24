public class Account {
    private String name;
    private String address;
    private String accountNumber;
    private String accountType;
    private double balance;

    public Account(String nameIn, String addressIn, String accountNumberIn, String accountTypeIn, double balanceIn){
        this.name = nameIn;
        this.address = addressIn;
        this.accountNumber = accountNumberIn;
        this.accountType = accountTypeIn;
        this.balance = balanceIn;

    }

    public void setName(String nameIn){
        this.name = nameIn;
    }

    public void setAddress(String addressIn){
        this.address = addressIn;
    }

    public void setAccountNumber(String accountNumberIn){
        this.accountNumber = accountNumberIn;
    }

    public void setAccountType(String accountTypeIn){
        this.accountType = accountTypeIn;
    }

    public void setBalance(double balanceIn){
        this.balance = balanceIn;
    }

    public String getName(){
        return(this.name);
    }

    public String getAddress(){
        return(this.address);
    }

    public String getAccountNumber(){
        return(this.accountNumber);
    }

    public String getAccountType(){
        return(this.accountType);
    }

    public double getBalance(){
        return(this.balance);
    }

    public void printData(){
        System.out.print("Name: " + this.name);
        System.out.print(", Address: " + this.address);
        System.out.print(", Account Number: " + this.accountNumber);
        System.out.print(", Account type: " + this.accountType);
        System.out.println(", Balance: " + this.balance);
    }
}
