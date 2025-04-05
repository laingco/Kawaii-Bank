import java.util.ArrayList;

public class KawaiiBank {
    public static void main(String[] args){
        ArrayList<Account> accounts = new ArrayList<Account>();

        EditCSV csvEditor = new EditCSV();
        accounts = csvEditor.fillArray(accounts);


        GUI gui = new GUI(accounts, 750, 750);
        gui.initialize();
    }
}