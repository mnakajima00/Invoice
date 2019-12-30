import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainFrame extends JFrame {

    JLabel billToLabel, nameLabel, birthLabel, genderLabel, passportNumLabel, dateLabel;
    JTextField billToText, nameText, birthText, passportText;
    JComboBox genderCombo;

    public MainFrame(){
        super("Invoice");

        setSize(500,500);
        setMaximumSize(new Dimension(650, 800));
        setMinimumSize(new Dimension(350, 450));
        setLocationRelativeTo(null); // Set frame to center
        setLayout(new BorderLayout());

        addWidgets(); //Adds all widgets inside JFrame

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void addWidgets(){

        /* JPANEL STRUCTURE: VISUALIZED
         *   __________________________________________
         *  |   Container                               |
         *  |    ___________________________________    |
         *  |   |   jpContainer                     |   |
         *  |   |    ___________________________    |   |
         *  |   |   |   jp                      |   |   |
         *  |   |   |                           |   |   |
         *  |   |   |                           |   |   |
         *  |   |   |                           |   |   |
         *  |   |   |___________________________|   |   |
         *  |   |    ___________________________    |   |
         *  |   |   |   jpBirthAndGender        |   |   |
         *  |   |   |                           |   |   |
         *  |   |   |                           |   |   |
         *  |   |   |                           |   |   |
         *  |   |   |___________________________|   |   |
         *  |   |                  .                |   |
         *  |   |                  .                |   |
         *  |   |___________________________________|   |
         *  |___________________________________________|
         */

        //Top level JPanel
        JPanel container = new JPanel(new GridLayout(0, 1));
        Border padding = BorderFactory.createEmptyBorder(10, 20, 10, 20); //Padding
        container.setBorder(padding);

        //jp Container
        JPanel jpContainer = new JPanel(new GridLayout(0, 1));
        jpContainer.setBorder(BorderFactory.createTitledBorder("CUSTOMER INFORMATION")); //Legend (Border with title)

        //Textfields and etc for Customer Info
        JPanel jp = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        //Default Settings
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10); //Padding

        //Date label
        dateLabel = new JLabel("Date:");
        gbc.anchor = GridBagConstraints.LINE_START; //Align text left
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        jp.add(dateLabel, gbc);
        //Date text


        //Bill to: Label
        billToLabel = new JLabel("Bill to:");
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START; //Align text left
        gbc.insets = new Insets(5, 10, 5, 0);
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        jp.add(billToLabel, gbc);
        //Bill to: Text
        billToText = new JTextField();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 10);
        gbc.weightx = 1;
        gbc.gridwidth = 2;
        gbc.gridx = 1;
        gbc.gridy = 1;
        jp.add(billToText, gbc);

        //Name: Label
        nameLabel = new JLabel("Name:");
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START; //Align text left
        gbc.insets = new Insets(5, 10, 5, 0);
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.gridx = 0;
        gbc.gridy = 2;
        jp.add(nameLabel, gbc);
        //Name: Text
        nameText = new JTextField();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 10);
        gbc.weightx = 1;
        gbc.gridwidth = 2;
        gbc.gridx = 1;
        gbc.gridy = 2;
        jp.add(nameText, gbc);

        jpContainer.add(jp); //Add Customer info widgets JPanel to container JPanel

        container.add(jpContainer); //jpCOntainer --> container

        add(container, BorderLayout.PAGE_START); //Add container to JFrame
    }

}
