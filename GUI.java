import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GUI {
    private JFrame jframe = new JFrame("Kawaii Bank");
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private int SCREEN_WIDTH = 500;
    private int SCREEN_HEIGHT = 500;
    private int mainMenuItems = 4; //Number of items shown in main menu

    public GUI(ArrayList<Account> accounts){
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        JPanel homeScreen = createHomePanel();
        JPanel accountListScreen = createAccountListPanel(accounts);

        mainPanel.add(homeScreen, "Home");
        mainPanel.add(accountListScreen, "Accounts");
        
        ArrayList<JPanel> infoScreens = new ArrayList<JPanel>();
        for (int i = 0; i < accounts.size(); i++){
            infoScreens.add(createAccountInfoPanel(accounts, i));
            mainPanel.add(infoScreens.get(i), accounts.get(i).getAccountNumber());
        }

        jframe.getContentPane().add(mainPanel);
        jframe.setVisible(true);
    }

    public JPanel createHomePanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(mainMenuItems, 1, 0, 10));

        JLabel title = new JLabel("Main Menu", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.PLAIN, 45)); 
        panel.add(title, BorderLayout.NORTH);

        JButton listAccounts = new JButton("List accounts");
        listAccounts.addActionListener(e -> cardLayout.show(mainPanel, "Accounts"));
        panel.add(listAccounts);

        JButton addAccounts = new JButton("Add account");
        panel.add(addAccounts);

        JButton removeAccounts = new JButton("Remove account");
        panel.add(removeAccounts);

        return(panel);
    }

    public JPanel createAccountListPanel(ArrayList<Account> accounts){
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(createBackBar("Home"), BorderLayout.NORTH);
        
        JPanel accountListPanel = new JPanel(new GridLayout(accounts.size(), 1, 0, 10));
        for (int i = 0; i < accounts.size(); i++){
            JButton button = new JButton(accounts.get(i).getName() + " | " + accounts.get(i).getAccountType() + " | " +  Double.toString(accounts.get(i).getBalance()));
            int x = i;
            button.addActionListener(e -> cardLayout.show(mainPanel, accounts.get(x).getAccountNumber()));
            accountListPanel.add(button);
        }

        JScrollPane scrollPanel = new JScrollPane(accountListPanel);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(scrollPanel, BorderLayout.CENTER);

        return(panel);
    }

    public JPanel createBackBar(String panelName){
        JButton back = new JButton("Back");
        back.addActionListener(e -> cardLayout.show(mainPanel, panelName));
        JPanel backBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backBar.add(back);
        return(backBar);
    }

    public JPanel createAccountInfoPanel(ArrayList<Account> accounts, int accountIndex){
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(createBackBar("Accounts"), BorderLayout.NORTH);

        JPanel infoPanel = new JPanel(new GridLayout(5, 3, 0, 20));
        

        JLabel name = new JLabel("Name: ", JLabel.LEFT);
        JTextField nameField = new JTextField(15);
        name.setToolTipText(accounts.get(accountIndex).getName());
        JButton nameSave = new JButton();
        JLabel address = new JLabel("Address: ", JLabel.LEFT);

        JLabel accountNumber = new JLabel("Account Number: ", JLabel.LEFT);

        JLabel accountType = new JLabel("Account type: ", JLabel.LEFT);

        JLabel balance = new JLabel("Balance: ", JLabel.LEFT);

        return(panel);
    }
}
