import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;

public class EditCSV {
    public EditCSV(){
    }

    /*
     * Takes in an arraylist of accounts and writes its contents to a .csv file
     * 
     * The method takes an arraylist of accounts when loops through the arraylist, writing each value of the account to a line seperated by commas.
     * 
     * Used to save the current account data when "Save and exit" button is pressed.
     */
    public void setData(ArrayList<Account> input){
        try{
            FileWriter fileWriter = new FileWriter(new File("bankData.csv"));
            for (int i = 0; i < input.size(); i++){
                fileWriter.write(input.get(i).getName()+",");
                fileWriter.write(input.get(i).getAddress()+",");
                fileWriter.write(input.get(i).getAccountNumber()+",");
                fileWriter.write(input.get(i).getAccountType()+",");
                fileWriter.write(input.get(i).getBalance()+"\n");
            }
            fileWriter.flush();
            fileWriter.close();
        }catch(IOException e){
            e.printStackTrace();
        };
    }

    /*
     * Returns the contenets of the 'bankData.csv' file in the format of ArrayList<String[]>.
     * 
     * This method gets the contents of the bank data file and while there is a new line containing data, the line is split at each comma
     * and saved as an array of strings whic his added to an arraylist.
     * 
     * Used in the fillArray() method when entering the file data to an arraylist of accounts.
     */
    public ArrayList<String[]> getData(){
        ArrayList<String[]> output = new ArrayList<String[]>();
        try{
            Scanner scanner = new Scanner(new File("bankData.csv"));
            while(scanner.hasNextLine()){
                String[] currentLine = scanner.nextLine().split(",");
                output.add(currentLine);
            }
            scanner.close();
        }catch(IOException e){
            e.printStackTrace();
        };
        return(output);
    }

    /*
     * Takes in an arraylist of accounts and outputs an arraylist of accounts.
     * 
     * This method takes an arraylist of accoutns(usually empty) and fills it with the contents of the bank data file and returns the same, filled, arraylist of accounts.
     * 
     * Used to fill the main accounts arraylist at the start of the program.
     */
    public ArrayList<Account> fillArray(ArrayList<Account> accounts){
        ArrayList<Account> accountsOut = new ArrayList<Account>();
        ArrayList<String[]> fileContents = new ArrayList<String[]>();
        fileContents = getData();
        for (int i = 0; i < fileContents.size(); i++){
            accountsOut.add(new Account(fileContents.get(i)[0], fileContents.get(i)[1], fileContents.get(i)[2], fileContents.get(i)[3], Double.parseDouble(fileContents.get(i)[4])));
        }
        return(accountsOut);
    }
}
