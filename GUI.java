import javax.swing.*;
import java.awt.*;

public class GUI {
    private JFrame jframe = new JFrame("Kawaii Bank");
    private int SCREEN_WIDTH = 500;
    private int SCREEN_HEIGHT = 500;

    public GUI(){
        JPanel mainArea = new JPanel();
        mainArea.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

        c.gridwidth = 10;
        c.gridheight = 10;

        JButton button;
        button = new JButton("Back");
        //c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;

        mainArea.add(button, c);

        button = new JButton("new");
        //c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;

        mainArea.add(button, c);

        jframe.getContentPane().add(BorderLayout.CENTER, mainArea);
        jframe.setVisible(true);
    }
}
