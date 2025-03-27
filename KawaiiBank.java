import java.util.ArrayList;

public class KawaiiBank {
    public static void main(String[] args){
        Account test = new Account("Viraaj Ravji", "123 silly lane", "12-4-14-23--43234-3-33", "Current", 0);
        ArrayList<Account> accounts = new ArrayList<Account>();

        EditCSV csvEditor = new EditCSV();
        ArrayList<String[]> fileContents = new ArrayList<String[]>();
        fileContents = csvEditor.getData();
        for (int i = 0; i < fileContents.size(); i++){
            accounts.add(new Account(fileContents.get(i)[0], fileContents.get(i)[1], fileContents.get(i)[2], fileContents.get(i)[3], Double.parseDouble(fileContents.get(i)[4])));
        }

        for (int i = 0; i < accounts.size(); i++){
            accounts.get(i).printData();
        }
        //accounts.add(test);
        csvEditor.setData(accounts);
        GUI gui = new GUI(accounts);
    }

}
