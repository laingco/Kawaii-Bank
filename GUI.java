import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class GUI {
    private JFrame jFrame = new JFrame("Kawaii Bank");
    private JPanel mainPanel; //All JPanels for the porgram are added to this JPanel.
    private CardLayout cardLayout; //All JPanels in the program are handled by this CardLayout.
    private ArrayList<Account> accounts;
    private int SCREEN_WIDTH;
    private int SCREEN_HEIGHT;
    private int mainMenuItems = 6; //Number of items shown in main menu
    public BigDecimal net = new BigDecimal(0.0);
    public BigDecimal sum = new BigDecimal(0.0);

    /*
     * Takes in an arraylist of accounts and two integers.
     * 
     * This the the constructor class for the GUI and sets the local variables to the inputted values.
     * 
     * Used to create a GUI for the bank program.
     */
    public GUI(ArrayList<Account> accounts, int SCREEN_WIDTH, int SCREEN_HEIGHT){
        this.accounts = accounts;
        this.SCREEN_WIDTH = SCREEN_WIDTH;
        this.SCREEN_HEIGHT = SCREEN_HEIGHT;
    }

    /*
     * This method starts the GUI by setting the required parameters and greating the required components.
     * 
     * Used to initialize the gui after creation.
     */
    public void initialize(){
        for (int i = 0; i < accounts.size(); i++){ //Adds up all of the account balances before anything has been edited and saves to a variable for the end of day summary.
            net = net.add(new BigDecimal(accounts.get(i).getBalance()));
        }

        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrame.setSize(this.SCREEN_WIDTH, this.SCREEN_HEIGHT);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        JPanel homeScreen = createHomePanel();
        mainPanel.add(homeScreen, "Home");

        jFrame.getContentPane().add(mainPanel);
        jFrame.setVisible(true);
    }

    /*
     * Returns a JPanel with relevant components.
     * 
     * This method is run to create a home panel with all of the sessiscary components.
     * 
     * Used in the initialize method when starting the program.
     */
    public JPanel createHomePanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(mainMenuItems, 1, 0, 10));

        JLabel title = new JLabel("Main Menu", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.PLAIN, 45)); 
        panel.add(title, BorderLayout.NORTH);

        JButton listAccounts = new JButton("List/Edit accounts");
        listAccounts.addActionListener(e -> { //When the button is pressed, a new panel is made and added to the main panel and then shown by the card layout.
            mainPanel.add(createAccountListPanel(), "List accounts");
            cardLayout.show(mainPanel, "List accounts");
        });
        panel.add(listAccounts);

        JButton depositMoney = new JButton("Deposit/Withdraw money");
        depositMoney.addActionListener(e -> {
            mainPanel.add(createMoneyPanel(), "Deposit/Withdraw money");
            cardLayout.show(mainPanel, "Deposit/Withdraw money");
        });
        panel.add(depositMoney);
 
        JButton addAccounts = new JButton("Add account");
        addAccounts.addActionListener(e -> {
            mainPanel.add(createAddAccountPanel(), "Add account");
            cardLayout.show(mainPanel, "Add account");
        });
        panel.add(addAccounts);

        JButton removeAccounts = new JButton("Remove account");
        removeAccounts.addActionListener(e -> {
            mainPanel.add(createRemoveAccountPanel(), "Remove account");
            cardLayout.show(mainPanel, "Remove account");
        });
        panel.add(removeAccounts);

        JButton exit = new JButton("Save and exit");
        exit.addActionListener(e -> {
            for (int i = 0; i < accounts.size(); i++){ //Adds up all of the balances at the end of the day for the summary.
                sum = sum.add(new BigDecimal(accounts.get(i).getBalance()));
            }
            System.out.println("Total money in bank: " + sum.setScale(2,RoundingMode.HALF_EVEN).toString()); //Sets the output to 2dp for money conventions.

            net = sum.subtract(net); //Gets the net money for the bank for the day by subtracting the final sum from the initial sum.
            System.out.println("Net deposits/withdrawls: " + net.setScale(2,RoundingMode.HALF_EVEN).toString());

            new EditCSV().setData(accounts);
            jFrame.dispose(); //Deletes the JFrame containing the program.
        });
        panel.add(exit);

        return(panel);
    }

    /*
     * Returns a JPanel with relevant components.
     * 
     * This method creates a list of JButtons, each relating to an account in the accounts arraylist. 
     * When one the the accoount's JButtons is pressed a new JPanel is made using createAccountInfoPanel() with the current information of the account.
     */
    public JPanel createAccountListPanel(){
        JPanel panel = new JPanel(new BorderLayout()); //A borderlayout is used to have basic layout functionality for locating the back bar and the info panel.
        panel.add(createBackBar("Home", "Accounts list"), BorderLayout.NORTH);

        ArrayList<JPanel> infoScreens = new ArrayList<JPanel>();
        for (int i = 0; i < accounts.size(); i++){
            infoScreens.add(createAccountInfoPanel(i)); //Fills an arraylist of JPanels, each with information corresponding to an account in the accounts arraylist.
            mainPanel.add(infoScreens.get(i), accounts.get(i).getAccountNumber());
        }
        
        JPanel accountListPanel = new JPanel(new GridLayout(accounts.size(), 1, 0, 10));
        for (int i = 0; i < accounts.size(); i++){
            //Labels each JButton with the correct information for the associated account info panel.
            JButton button = new JButton(accounts.get(i).getName() + " | " + accounts.get(i).getAccountType() + " | " +  Double.toString(accounts.get(i).getBalance()));
            final int x = i; //Makes the current 'i' a static 'x' with the same value for use in the actionListener.
            button.addActionListener(e -> {
                cardLayout.show(mainPanel, accounts.get(x).getAccountNumber());
            });
            accountListPanel.add(button);
        }

        JScrollPane scrollPanel = new JScrollPane(accountListPanel);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(scrollPanel, BorderLayout.CENTER);

        return(panel);
    }

    public JPanel createAddAccountPanel(){
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(createBackBar("Home", "Create new account"), BorderLayout.NORTH);

        JPanel addAccountPanel = new JPanel(new GridLayout(4, 2, 0, 20));
        String[] labels = {"Name: ", "Address: ", "Account type: "};
        JButton save = new JButton("Create account");
        Account infoGetterAccount = new Account();

        for (int i = 0; i < 3; i++){
            JLabel text = new JLabel(labels[i], JLabel.LEFT);
            text.setFont(new Font("Arial", Font.PLAIN, 25));
            addAccountPanel.add(text);

            if (i != 2){
                JTextField textField = new JTextField(infoGetterAccount.getDefaults()[i], 10);
                addAccountPanel.add(textField);
            }else{
                JComboBox<String> textField = new JComboBox<String>(infoGetterAccount.getChoices());
                textField.setSelectedIndex(0);
                addAccountPanel.add(textField);
            }
        }

        save.addActionListener(e -> {
            accounts.add(new Account());
            final int index = accounts.size()-1;
            boolean errors = false;
            String inputs[] = new String[3];
            
            for (int i = 0; i < 2; i++){
                int compIndex = 2 * i + 1;
                JTextField textField = (JTextField) addAccountPanel.getComponent(compIndex);
                if (!accounts.get(index).checkData(textField.getText(), i)){
                    addAccountPanel.getComponent(compIndex).setForeground(new Color(255,0,0));
                    errors = true;
                }else{
                    inputs[i] = textField.getText();
                    addAccountPanel.getComponent(compIndex).setForeground(new Color(0,0,0));
                }
            }

            JComboBox<String> textField3 = (JComboBox<String>) addAccountPanel.getComponent(5);
            inputs[2] = textField3.getSelectedItem().toString();

            accounts.remove(index);
            if (!errors){
                accounts.add(new Account(accounts, inputs[0], inputs[1], inputs[2]));
                mainPanel.add(createHomePanel(), "Home");
                cardLayout.show(mainPanel, "Home");
            }
        });

        addAccountPanel.add(save);

        panel.add(addAccountPanel, BorderLayout.CENTER);
        
        return(panel);
    }

    public JPanel createBackBar(String panelName, String pageName){
        JButton back = new JButton("Back");
        back.addActionListener(e -> {
            if (!pageName.equals("Remove account(s)")){
                mainPanel.add(createAccountListPanel(), "List accounts"); 
                mainPanel.add(createAddAccountPanel(), "Add account");
                mainPanel.add(createRemoveAccountPanel(), "Remove account");
                mainPanel.add(createMoneyPanel(), "Deposit/Withdraw money");
            }
            
            ArrayList<JPanel> infoScreens = new ArrayList<JPanel>();
            for (int i = 0; i < accounts.size(); i++){
                infoScreens.add(createAccountInfoPanel(i));
                mainPanel.add(infoScreens.get(i), accounts.get(i).getAccountNumber());
            }

            cardLayout.show(mainPanel, panelName);
        });

        JLabel title = new JLabel(pageName);

        JPanel backBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backBar.add(back);
        backBar.add(title);
        return(backBar);
    }

    public JPanel createAccountInfoPanel(int accountIndex){
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(createBackBar("List accounts", "Account info"), BorderLayout.NORTH);

        JPanel infoPanel = new JPanel(new GridLayout(5, 3, 0, 20));
        
        String[] labels = {"Name: ", "Address: ", "Account number: ", "Account type: ", "Balance: "};
        String[] choices = {"Everyday", "Current", "Savings"};        
        ArrayList<JButton> textSave = new ArrayList<JButton>();

        for (int i = 0; i < 5; i++){
            JLabel text = new JLabel(labels[i], JLabel.LEFT);
            text.setFont(new Font("Arial", Font.PLAIN, 25));
            infoPanel.add(text);

            if (i != 3){
                JTextField textField = new JTextField(accounts.get(accountIndex).getData(i), 10);
                infoPanel.add(textField);
            }else{
                JComboBox<String> textField = new JComboBox<String>(choices);
                textField.setSelectedItem(accounts.get(accountIndex).getAccountType());
                infoPanel.add(textField);
            }

            final int x = i;
            textSave.add(new JButton("Save"));
            textSave.get(i).addActionListener(e -> {
                //Gets the new JComboBox from the original definition of the old one using getComponent() and since I know how many components are made in each run of the for loop
                //I use 3 * x (x = i at original definition of listener) which would give me the associated JLabel to the entry but since i want the JComboBox (which is the next component added) I add 1.
                int compIndex = 3 * x + 1;
                if (x != 3) { 
                    JTextField textField = (JTextField) infoPanel.getComponent(compIndex);
                    if (!accounts.get(accountIndex).setData(textField.getText(), x)){
                        infoPanel.getComponent(compIndex).setForeground(new Color(255,0,0));
                    }else{
                        infoPanel.getComponent(compIndex).setForeground(new Color(0,0,0));
                    }
                } else {
                    JComboBox<String> textField = (JComboBox<String>) infoPanel.getComponent(compIndex);
                    if (!accounts.get(accountIndex).setData(textField.getSelectedItem().toString(), x)){
                        infoPanel.getComponent(compIndex).setForeground(new Color(255,0,0));
                    }else{
                        infoPanel.getComponent(compIndex).setForeground(new Color(0,0,0));
                    }
                }
                mainPanel.add(createAccountListPanel(), "Accounts");
            });
            infoPanel.add(textSave.get(i));
        }

        panel.add(infoPanel, BorderLayout.CENTER);

        return(panel);
    }

    public JPanel createRemoveAccountPanel(){
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(createBackBar("Home", "Remove account(s)"), BorderLayout.NORTH);
        
        JPanel accountListPanel = new JPanel(new GridLayout(accounts.size(), 1, 0, 10));
        for (int i = 0; i < accounts.size(); i++){
            JButton button = new JButton(accounts.get(i).getName() + " | " + accounts.get(i).getAccountType() + " | " +  Double.toString(accounts.get(i).getBalance()));
            final int x = i;
            button.addActionListener(e -> {
                accounts.remove(x);
                accountListPanel.remove(accountListPanel.getComponent(x));
                mainPanel.add(createRemoveAccountPanel(), "Remove account");
                cardLayout.show(mainPanel, "Remove account");
            });
            accountListPanel.add(button);
        }

        JScrollPane scrollPanel = new JScrollPane(accountListPanel);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(scrollPanel, BorderLayout.CENTER);

        return(panel);
    }

    public JPanel createMoneyPanel(){
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(createBackBar("Home", "Deposit/Withdraw money"), BorderLayout.NORTH);
        
        JPanel moneyPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        
        JLabel accountSelectLabel = new JLabel("Select account: ");
        moneyPanel.add(accountSelectLabel);
        
        String[] menuItems = new String[accounts.size()];
        for (int i = 0; i < accounts.size(); i++){
            menuItems[i] = accounts.get(i).getName() + " " + (Integer.parseInt(accounts.get(i).getAccountNumber().substring(17)) + 1) + " | " + accounts.get(i).getAccountType() + " | " +  Double.toString(accounts.get(i).getBalance());
        }
        JComboBox<String> accountSelect = new JComboBox<String>(menuItems);
        moneyPanel.add(accountSelect);

        JLabel valueLabel = new JLabel("Withdraw/Deposit amount: ");
        moneyPanel.add(valueLabel);

        JTextField valueField = new JTextField("0");
        moneyPanel.add(valueField);

        String[] labels = {"Deposit", "Withdraw"};
        for (int i = 0; i < 2; i++){
            JButton button = new JButton(labels[i]);
            final int x = i;
            button.addActionListener(e -> {
                JComboBox<String> jBox = (JComboBox<String>)moneyPanel.getComponent(1);
                JTextField jField = (JTextField)moneyPanel.getComponent(3);
                Boolean valid = true;
                for (int j = 0; j < accounts.size(); j++){
                    Account selectedAccount = accounts.get(j);
                    if((selectedAccount.getName() + " " + (Integer.parseInt(selectedAccount.getAccountNumber().substring(17)) + 1) + " | " +
                    selectedAccount.getAccountType() + " | " +  Double.toString(selectedAccount.getBalance())).equals(jBox.getSelectedItem().toString())){
                        if (x == 0){
                            if (!selectedAccount.setData(new BigDecimal(selectedAccount.getBalance()).add(new BigDecimal(jField.getText())).setScale(2, RoundingMode.HALF_EVEN).toString(), 4)){ 
                                moneyPanel.getComponent(3).setForeground(new Color(255,0,0));
                                valid = false;
                            }else{
                                moneyPanel.getComponent(3).setForeground(new Color(0,0,0));
                            }
                        }else{
                            //BigDecimal must be used here to negate any rounding error from subtraction
                            if (!selectedAccount.setData(new BigDecimal(selectedAccount.getBalance()).subtract(new BigDecimal(jField.getText())).setScale(2, RoundingMode.HALF_EVEN).toString(), 4)){
                                moneyPanel.getComponent(3).setForeground(new Color(255,0,0));
                                valid = false;
                            }else{
                                moneyPanel.getComponent(3).setForeground(new Color(0,0,0));
                            }
                        }
                    }
                }
                if (valid){
                    mainPanel.add(createMoneyPanel(), "Deposit/Withdraw money");
                    cardLayout.show(mainPanel, "Deposit/Withdraw money");
                }
            });
            moneyPanel.add(button);
        }

        panel.add(moneyPanel, BorderLayout.CENTER);

        return(panel);
    }
}
