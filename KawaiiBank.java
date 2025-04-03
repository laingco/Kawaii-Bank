import java.util.ArrayList;

public class KawaiiBank {
    public static void main(String[] args){
        ArrayList<Account> accounts = new ArrayList<Account>();

        EditCSV csvEditor = new EditCSV();
        accounts = csvEditor.fillArray(accounts);

        for (int i = 0; i < accounts.size(); i++){
            accounts.get(i).printData();
        }

        /*Account test = new Account("Viraaj Ravji", "123 silly lane", "12-4-14-23--43234-3-33", "Current", 0);
        accounts.add(test);
        csvEditor.setData(accounts);*/

        GUI gui = new GUI(accounts, 750, 750);
        gui.initialize();
    }

}