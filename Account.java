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

    public Account(){
        this.name = "John Doe";
        this.address = "123 Street City";
        this.accountNumber = "00-0000-0000000-00";
        this.accountType = "Everyday";
        this.balance = 0.00;
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

    public String getData(int index){
        switch (index){
            case 0:
                return(this.name);
            case 1:
                return(this.address);
            case 2:
                return(this.accountNumber);
            case 3:
                return(this.accountType);
            case 4:
                return(Double.toString(this.balance));
            default:
                return("");
        }
    }

    public void setData(String input, int index){
        switch (index){
            case 0:
                this.name = input;
                break;
            case 1:
                this.address = input;
                break;
            case 2:
                this.accountNumber = input;
                break;
            case 3:
                this.accountType = input;
                break;
            case 4:
                this.balance = Double.parseDouble(input);
                break;
            default:
                break;
        }
    }

    public void printData(){
        System.out.print("Name: " + this.name);
        System.out.print(", Address: " + this.address);
        System.out.print(", Account Number: " + this.accountNumber);
        System.out.print(", Account type: " + this.accountType);
        System.out.println(", Balance: " + this.balance);
    }
}
