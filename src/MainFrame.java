import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainFrame extends JFrame {

    JLabel billToLabel, nameLabel, birthLabel, genderLabel, passportNumLabel, dateLabel;
    JTextField billToText, nameText, birthText, passportText, dateText;
    JComboBox genderCombo;
    JButton nextBtn, clearBtn;

    public MainFrame(){
        super("Invoice");

        setSize(550, 600);
        setLocationRelativeTo(null); // Set frame to center
        setLayout(new BorderLayout());

        addWidgets(); //Adds all widgets inside JFrame

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void addWidgets(){

        /* JPANEL STRUCTURE: VISUALIZED
         *   __________________________________________
         *  |   Container                               |
         *  |    ___________________________________    |
         *  |   |   innerContainer                  |   |
         *  |   |    ___________________________    |   |
         *  |   |   |   widgetPanel             |   |   |
         *  |   |   |                           |   |   |
         *  |   |   |                           |   |   |
         *  |   |   |                           |   |   |
         *  |   |   |___________________________|   |   |
         *  |   |___________________________________|   |
         *  |___________________________________________|
         */

        //JPanels:
        //Main Container
        JPanel container = new JPanel(new GridLayout(0, 1));
        Border padding = BorderFactory.createEmptyBorder(10, 20, 10, 20); //Padding
        container.setBorder(padding);

        //innerContainer JPanel
        JPanel innerContainer = new JPanel(new GridLayout(1, 1));
        innerContainer.setBorder(BorderFactory.createTitledBorder("CUSTOMER INFORMATION")); //Legend (Border with title)

        //widgetPanel - All widgets will go inside this JPanel
        JPanel widgetPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints(); //GridBagConstraints required for GridBagLayout

        //Initialize widgets:
        //Labels:
        dateLabel = new JLabel("Date:");
        billToLabel = new JLabel("Bill To:");
        nameLabel = new JLabel("Name:");
        birthLabel = new JLabel("Date of Birth:");
        genderLabel = new JLabel("Gender:");
        passportNumLabel = new JLabel("Passport No:");
        //TextFields
        dateText = new JTextField(12);
        billToText = new JTextField(12);
        nameText = new JTextField(12);
        birthText = new JTextField(12);
        passportText = new JTextField(12);
        //JButton
        nextBtn = new JButton("Next");
        clearBtn = new JButton("Clear");

        //JComboBox
        String[] genders = {"", "Male", "Female", "Other"};
        genderCombo = new JComboBox(genders);

        //GridBagLayout init:

        ////////// FIRST ROW //////////
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.insets = new Insets(10, 8, 8, 0);
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        widgetPanel.add(dateLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.LINE_START;
        widgetPanel.add(dateText, gbc);

        ////////// SECOND ROW //////////
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.insets = new Insets(0, 8, 8, 0);
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        widgetPanel.add(billToLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.LINE_START;
        widgetPanel.add(billToText, gbc);

        ////////// THIRD ROW //////////
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.weightx = 0.1;
        widgetPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.LINE_START;
        widgetPanel.add(nameText, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.insets = new Insets(0, 8, 8, 8);
        gbc.weightx = 0.1;
        widgetPanel.add(birthLabel, gbc);

        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.LINE_START;
        widgetPanel.add(birthText, gbc);

        ////////// FOURTH ROW //////////
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.insets = new Insets(0, 8, 8, 0);
        gbc.weightx = 0.1;
        widgetPanel.add(genderLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        widgetPanel.add(genderCombo, gbc);

        ////////// FIFTH ROW //////////
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.weightx = 0.1;
        widgetPanel.add(passportNumLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.LINE_START;
        widgetPanel.add(passportText, gbc);

        ////////// SUBMIT / CLEAR //////////
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weighty = 0;
        gbc.weightx = 0.1;
        gbc.insets = new Insets(30, 0, 8, 0);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_END;
        widgetPanel.add(clearBtn, gbc);

        gbc.gridx = 3;
        widgetPanel.add(nextBtn, gbc);

        innerContainer.add(widgetPanel);

        container.add(innerContainer);

        add(container, BorderLayout.PAGE_START); //Add container to JFrame
    }

}
