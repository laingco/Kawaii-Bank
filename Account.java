import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;

public class Account {
    private String name;
    private String address;
    private String accountNumber;
    private String accountType;
    private double balance;
    private String[] choices = {"Everyday", "Current", "Savings"}; //Account types
    private String[] defaults = {"John Doe", "123 Street City", "00-0000-0000000-00", "Everyday", "0.00"};
    private String bankNumber = "08";
    private String branchNumber = "0101";
    private int currentOverdraftLimit = 1000;
    private int withdrawalLimit = 5000;

    /*
     * Takes in 4 strings and one double to write to the local variables for the new account.
     * 
     * Manual account constructor.
     * 
     * Used when manually adding an account in code for testing.
     */
    public Account(String nameIn, String addressIn, String accountNumberIn, String accountTypeIn, double balanceIn){
        this.name = nameIn;
        this.address = addressIn;
        this.accountNumber = accountNumberIn;
        this.accountType = accountTypeIn;
        this.balance = balanceIn;
    }

    /*
     * Takes in an arraylist of accounts(for account number creation) and three strings. 
     * A new account is created with the spcified name, address and account type with a balance of $0.00 and an appropriate account number.
     * 
     * Automatic account number account constructor.
     * 
     * Used when creating a new account. e.g. this constructor is used to create a new account with an automatically determined account number.
     */
    public Account(ArrayList<Account> accounts, String name, String address, String accountType){
        this.name = name.replace(",", ""); //Removes any commas to avoid formatting problems in .csv file.
        this.address = address.replace(",", "");

        String suffix = "00"; //Defining base values
        String personalNumber = "0000000";
        Boolean newNumber = true;

        for (int i = 0; i < accounts.size(); i++){
            String[] splitAccountNumber = accounts.get(i).getAccountNumber().split("-"); //Splits account number into blocks of numbers.
            
            if (accounts.get(i).getName().equals(name) && accounts.get(i).getAddress().equals(address)){ //If the current account in the for loop has a name and address equal to the inputted name and address then it is true.
                personalNumber = splitAccountNumber[2]; //Sets personal number(7 digit long block) to the account in the for loop's personal number because the account is for the same person.
                if (Integer.parseInt(splitAccountNumber[3]) < 9){
                    suffix = "0" + Integer.toString(Integer.parseInt(splitAccountNumber[3])+1); //Pads suffix with a 0 on the left when needed, to keep a consistient format. Also increments the suffix by one.
                }else{
                    suffix = Integer.toString(Integer.parseInt(splitAccountNumber[3])+1);
                }
                newNumber = false; //Declares the a new random number does not need to be generated
            }

            if (newNumber){
                Random random = new Random();
                personalNumber = Integer.toString(random.nextInt(9999999)); //Generates a random 7 digit number and converts to string
                while (personalNumber.length() < 7){ //Pads left with 0s when needed, to keep a consistient format.
                    personalNumber = "0" + personalNumber;
                }
                personalNumber = personalNumber.substring(0, 7);
                while (personalNumber == splitAccountNumber[2]){ //If the random number is equal to an existing account's personal number a new random number is generated.
                    personalNumber = Integer.toString(random.nextInt(9999999));
                    while (personalNumber.length() < 7){
                        personalNumber = "0" + personalNumber;
                    }
                    personalNumber = personalNumber.substring(0, 7);
                }
            }
        }

        this.accountNumber = bankNumber + "-" + branchNumber + "-" + personalNumber + "-" + suffix; //Reconstructs the account number with preset and generated values.
        this.accountType = accountType;
        this.balance = 0;
    }

    /*
     * Default account constructor.
     * 
     * Used when creating a new account. e.g. when an account is created this constructor is used to create a 'blank' placeholder account.
     */
    public Account(){
        this.name = defaults[0];
        this.address = defaults[1];
        this.accountNumber = defaults[2];
        this.accountType = choices[0];
        this.balance = Double.parseDouble(defaults[4]);
    }

    /*
     * Takes in an index as an integer and outputs the correspoding information for the account.
     * 
     * Used when multiple pieces of information are needed from a single account. e.g. used in a for loop when setting current information for an account in the list/edit accounts section.
     */
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

    /*
     * Takes in an input as a string and an index as an integer. 
     * Returns a boolean representing whether the input is valid for the field determined by the index. 
     * 
     * If the string can go into the corresponding field based on the index the method returns true and writes the input to the corresponding local variable.
     * Used for setting data for an existing account. e.g. when editing an account in the list/edit accounts section.
     */
    public Boolean setData(String input, int index){
        input = input.replace(",", ""); //Removes any commas to avoid formatting problems in .csv file.
        switch (index){
            case 0:
                if (checkData(input, index)){
                    this.name = input;
                    return(true);
                }else{
                    return(false);
                }
            case 1:
                if (checkData(input, index)){
                    this.address = input;
                    return(true);
                }else{
                    return(false);
                }
            case 2:
                if (checkData(input, index)){
                    this.accountNumber = input;
                    return(true);
                }else{
                    return(false);
                }
            case 3:
                if (checkData(input, index)){
                    this.accountType = input;
                    return(true);
                }else{
                    return(false);
                }
            case 4:
                if (checkData(input, index)){
                    BigDecimal decimal = new BigDecimal(input); //A BigDecimal is used to set the balance to negate any rounding error in String.parseDouble().
                    this.balance = decimal.doubleValue();
                    return(true);
                }else{
                    return(false);
                }
            default:
                return(true);
        }
    }

    /*
     * Takes in an input as a string and an index as an integer. 
     * Returns a boolean representing whether the input is valid for the field determined by the index. 
     * 
     * If the inputted string is a valid input for the referenced field determined by the index the method will return true.
     * If the input is invalid the method will return false.
     * Used when checking inputs for a new account before creation and in the setData method for checking inputs.
     */
    public Boolean checkData(String input, int index){
        switch (index){
            case 0:
                if (input.isBlank()){
                    return(false);
                }
                for (int i = 0; i < input.length(); i++){
                    if (Character.isDigit(input.charAt(i))){ //Doesn't allow a blank input or an input containing numbers.
                        return(false);
                    }
                }
                return(true);
            case 1:
                if (input.isBlank()){ //Doesn't allow a blank input
                    return(false);
                }
                return(true);
            case 2:
                if (!(input.length() == 18)){ //Only allows correct account number length.
                    return(false);
                }else if (!(input.charAt(2) == '-' && input.charAt(7) == '-' && input.charAt(15) == '-')){ //Doesn't allow incorrect '-' placement.
                    return(false);
                }
                return(true);
            case 3:
                if ((this.balance < 0) && (input != "Current")){ //Only allows account to be 'Current' if balance < 0.
                    return(false);
                }else if((this.balance < -1000) && (input == "Current")){ //Does not allow 'Current' account if past overdraft limit.
                    return(false);
                }
                return(true);
            case 4:
                try{
                    if (!(this.accountType.equals("Current")) && Double.parseDouble(input) < 0 || //Only allows 'Current' accounts to go into overdraft.
                    this.accountType.equals("Current") && Double.parseDouble(input) < -currentOverdraftLimit || //Only allows 'Current' accounts to go $1000 into overdraft.
                    (Double.parseDouble(input) - this.balance) < -withdrawalLimit){ //Only allows withdrawls less than or equal to the withdrawal limit.
                        return(false);
                    }else{
                        return(true);
                    }
                }catch(NumberFormatException e){ //Only allows strings containing doubles.
                    return(false);
                }
            default:
                return(true);
        }
    }

    /*
     * Prints all current information about the account to the console.
     * 
     * Used when debugging.
     */
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

    /*
     * Setters and getters.
     */
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
}
