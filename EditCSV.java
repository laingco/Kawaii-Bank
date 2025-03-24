import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;

public class EditCSV {
    public EditCSV(){
    }

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

    public ArrayList<String[]> getData(){
        ArrayList<String[]> output = new ArrayList<String[]>();
        try{
            Scanner scanner = new Scanner(new File("bankData.csv"));
            while(scanner.hasNextLine()){
                String[] currentLine = scanner.nextLine().split(",");
                output.add(currentLine);
            }
        }catch(IOException e){
            e.printStackTrace();
        };
        return(output);
    }
}
