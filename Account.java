public class Account {
    private String name;
    private String address;
    private String accountNumber;
    private String accountType;
    private double balance;
    private String[] choices = {"Everyday", "Current", "Savings"};
    private String[] defaults = {"John Doe", "123 Street City", "00-0000-0000000-00", "Everyday", "0.00"};

    public Account(String nameIn, String addressIn, String accountNumberIn, String accountTypeIn, double balanceIn){
        this.name = nameIn;
        this.address = addressIn;
        this.accountNumber = accountNumberIn;
        this.accountType = accountTypeIn;
        this.balance = balanceIn;
    }

    public Account(){
        this.name = defaults[0];
        this.address = defaults[1];
        this.accountNumber = defaults[2];
        this.accountType = choices[0];
        this.balance = Double.parseDouble(defaults[4]);
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

    public Boolean setData(String input, int index){
        switch (index){
            case 0:
                if (input.isBlank()){
                    return(false);
                }
                this.name = input;
                return(true);
            case 1:
                if (input.isBlank()){
                    return(false);
                }
                this.address = input;
                return(true);
            case 2:
                if (!(input.length() == 18)){
                    return(false);
                }else if (!(input.charAt(2) == '-' && input.charAt(7) == '-' && input.charAt(15) == '-')){
                    return(false);
                }
                this.accountNumber = input;
                return(true);
            case 3:
                if ((this.balance < 0) && (input != "Current")){         //Only allows account to be current if balance < 0
                    return(false);
                }else if((this.balance < -1000) && (input == "Current")){ //Does not allow current account if past overdraft limit
                    return(false);
                }
                this.accountType = input;
                return(true);
            case 4:
                try{
                    if (!(this.accountType == "Current") && Double.parseDouble(input) < 0 || //Only allows current accounts to overdraft
                    this.accountType == "Current" && Double.parseDouble(input) < -1000 ||    //Only allows current accounts to go $1000 into overdraft
                    (Double.parseDouble(input) - this.balance) < -5000){                     //Only allows withdrawls less than $5000
                        return(false);
                    }else{
                        this.balance = Double.parseDouble(input);
                        return(true);
                    }
                }catch(NumberFormatException e){ //Only allows strings containing doubles
                    return(false);
                }
            default:
                return(true);
        }
    }

    public void printData(){
        System.out.print("Name: " + this.name);
        System.out.print(", Address: " + this.address);
        System.out.print(", Account Number: " + this.accountNumber);
        System.out.print(", Account type: " + this.accountType);
        System.out.println(", Balance: " + this.balance);
    }

    public String[] getChoices(){
        return(choices);
    }

    public String[] getDefaults(){
        return(defaults);
    }
}
