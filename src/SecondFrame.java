import javax.swing.*;
import java.awt.*;

public class SecondFrame extends JFrame {
    //Table variables
    int rows;
    //Customer class declaration
    Customer c;
    //JFrame components declaration
    JLabel l1, l2, l3, l4, date, quantityLabel, priceLabel, consultationLabel, specialAcuLabel, medMachineLabel, tapLabel,
            p1, p2, p3, p4;
    JTextField qty1Text, qty2Text, qty3Text, qty4Text, dateText;
    JTable table;
    JButton addRowBtn;
    public SecondFrame(Customer c){
        super("Invoice");

        //Initialize Customer Class
        this.c = c;
        //Initialize JFrame
        setSize(550, 600);
        setLocationRelativeTo(null); // Set frame to center
        //setResizable(false);
        setLayout(new FlowLayout());

        addWidgets();

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void addWidgets(){

        /*  JPanel VISUALIZED
         *   _______________________________________________________
         *  |   container                                           |
         *  |    _________________________________________________  |
         *  |   |   mainPanel                                     | |
         *  |   |    __________________      _________________    | |
         *  |   |   |   leftPanel      |    |   rightPanel    |   | |
         *  |   |   |                  |    |                 |   | |
         *  |   |   |                  |    |                 |   | |
         *  |   |   |                  |    |                 |   | |
         *  |   |   |                  |    |                 |   | |
         *  |   |   |                  |    |                 |   | |
         *  |   |   |__________________|    |_________________|   | |
         *  |   |_________________________________________________| |
         *  |    _________________________________________________  |
         *  |   |   symptomsPane                                  | |
         *  |   |                                                 | |
         *  |   |_________________________________________________| |
         *  |    _________________________________________________  |
         *  |   |   btnPane                                       | |
         *  |   | ________________________________________________| |
         *  |_______________________________________________________|
         */

        //Container
        JPanel container = new JPanel(new GridLayout(0, 1));

        //MainPanel JPanel
        buildMainPanel(container);

        //Symptoms JPanel

        //Back and Next Button JPanel

        //Add container to JFrame
        add(container);

    }

    private void buildMainPanel(JPanel container){
        JPanel mainJPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        //leftPanel - adding services/products customer has bought
        JPanel leftPanel = new JPanel();
        //leftPanel.setBackground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        mainJPanel.add(leftPanel, gbc);
        //buildLeftPanel
        buildLeftPanel(mainJPanel);

        //rightPanel - Invoice Preview
        JPanel rightPanel = new JPanel();
        //rightPanel.setBackground(Color.BLUE);
        gbc.gridx = 1;
        mainJPanel.add(rightPanel, gbc);
        //Build rightPanel

        //Add mainJPanel to container
        container.add(mainJPanel);
    }

    private void buildLeftPanel(JPanel mainPanel){
        //Initialize components
        date = new JLabel("Date:");
        dateText = new JTextField(6);

        //leftPanel init
        JPanel leftPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        //Date components
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.anchor = GridBagConstraints.WEST;
        leftPanel.add(date, gbc);

        gbc.gridx = 1;
        leftPanel.add(dateText, gbc);

        //Add leftPanel to mainPanel
        mainPanel.add(leftPanel);

    }

    private void buildRightPanel(JPanel rightPanel){
        //Use JTextPane as preview? JTextPane can embed components so we can add an edit button to edit previous table

    }
}
