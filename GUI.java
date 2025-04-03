import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GUI {
    private JFrame jframe = new JFrame("Kawaii Bank");
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private ArrayList<Account> accounts;
    private int SCREEN_WIDTH;
    private int SCREEN_HEIGHT;
    private int mainMenuItems = 5; //Number of items shown in main menu

    public GUI(ArrayList<Account> accounts, int SCREEN_WIDTH, int SCREEN_HEIGHT){
        this.accounts = accounts;
        this.SCREEN_WIDTH = SCREEN_WIDTH;
        this.SCREEN_HEIGHT = SCREEN_HEIGHT;
    }

    public void initialize(){
        jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jframe.setSize(this.SCREEN_WIDTH, this.SCREEN_HEIGHT);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        JPanel homeScreen = createHomePanel();
        mainPanel.add(homeScreen, "Home");

        jframe.getContentPane().add(mainPanel);
        jframe.setVisible(true);
    }

    public JPanel createHomePanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(mainMenuItems, 1, 0, 10));

        JLabel title = new JLabel("Main Menu", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.PLAIN, 45)); 
        panel.add(title, BorderLayout.NORTH);

        JButton listAccounts = new JButton("List/Edit accounts");
        listAccounts.addActionListener(e -> {
            mainPanel.add(createAccountListPanel(), "List accounts");
            cardLayout.show(mainPanel, "List accounts");
            ArrayList<JPanel> infoScreens = new ArrayList<JPanel>();
            for (int i = 0; i < accounts.size(); i++){
                infoScreens.add(createAccountInfoPanel(i));
                mainPanel.add(infoScreens.get(i), accounts.get(i).getAccountNumber());
            }
        });
        panel.add(listAccounts);

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
            new EditCSV().setData(accounts); 
            jframe.dispose();
        });
        panel.add(exit);

        return(panel);
    }

    public JPanel createAccountListPanel(){
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(createBackBar("Home", "Accounts list"), BorderLayout.NORTH);
        
        JPanel accountListPanel = new JPanel(new GridLayout(accounts.size(), 1, 0, 10));
        for (int i = 0; i < accounts.size(); i++){
            JButton button = new JButton(accounts.get(i).getName() + " | " + accounts.get(i).getAccountType() + " | " +  Double.toString(accounts.get(i).getBalance()));
            int x = i;
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

        JPanel addAccountPanel = new JPanel(new GridLayout(6, 2, 0, 20));
        String[] labels = {"Name: ", "Address: ", "Account number: ", "Account type: ", "Balance: "};
        JButton save = new JButton("Create account");

        for (int i = 0; i < 5; i++){
            JLabel text = new JLabel(labels[i], JLabel.LEFT);
            text.setFont(new Font("Arial", Font.PLAIN, 25));
            addAccountPanel.add(text);

            if (i != 3){
                JTextField textField = new JTextField(accounts.get(accounts.size()-1).getDefaults()[i], 10);
                addAccountPanel.add(textField);
            }else{
                JComboBox<String> textField = new JComboBox<String>(accounts.get(accounts.size()-1).getChoices());
                textField.setSelectedIndex(0);
                addAccountPanel.add(textField);
            }
        }

        save.addActionListener(e -> {
            accounts.add(new Account());
            final int index = accounts.size()-1;
            boolean errors = false;
            
            for (int i = 0; i < 5 && i != 3; i++){
                int compIndex = 2 * i + 1;
                JTextField textField = (JTextField) addAccountPanel.getComponent(compIndex);
                if (!accounts.get(index).setData(textField.getText(), i)){
                    addAccountPanel.getComponent(compIndex).setForeground(new Color(255,0,0));
                    errors = true;
                }else{
                    addAccountPanel.getComponent(compIndex).setForeground(new Color(0,0,0));
                }
            }

            JComboBox<String> textField4 = (JComboBox<String>) addAccountPanel.getComponent(7);
            accounts.get(index).setData(textField4.getSelectedItem().toString(), 3);

            if (!errors){
                mainPanel.add(createHomePanel(), "Home");
                cardLayout.show(mainPanel, "Home");
            }else{
                accounts.remove(index);
            }
        });

        addAccountPanel.add(save);

        panel.add(addAccountPanel, BorderLayout.CENTER);
        
        return(panel);
    }

    public JPanel createBackBar(String panelName, String pageName){
        JButton back = new JButton("Back");
        back.addActionListener(e -> {
            mainPanel.add(createAccountListPanel(), "List accounts"); 
            mainPanel.add(createAddAccountPanel(), "Add account");
            mainPanel.add(createRemoveAccountPanel(), "Remove account");
            
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
}
