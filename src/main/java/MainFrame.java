import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MainFrame extends JFrame implements ActionListener {

    //Declaring JFrame Components
    JLabel billToLabel, nameLabel, birthLabel, genderLabel, passportNumLabel, dateLabel, currentDateLabel;
    JTextField billToText, nameText, birthText, passportText, dateText;
    JComboBox genderCombo;
    JButton nextBtn, clearBtn;
    Border errorBorder, defaultBorder, defaultJComboBorder;

    //Date format
    DateTimeFormatter dFormat;

    //Initializes the JFrame
    public MainFrame(){
        super("Invoice");

        setSize(550, 600);
        setLocationRelativeTo(null); // Set frame to center
        //setResizable(false);
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

        addWidgets(); //Adds all widgets inside JFrame

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public MainFrame(String date, String billTo, String dob, String name, int gender, String passport) {
        super("Invoice");

        setSize(550, 600);
        setLocationRelativeTo(null); // Set frame to center
        //setResizable(false);
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

        addWidgets(); //Adds all widgets inside JFrame
        dateText.setText(date);
        billToText.setText(billTo);
        birthText.setText(dob);
        nameText.setText(name);
        genderCombo.setSelectedIndex(gender);
        passportText.setText(passport);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void addWidgets(){

        //Current date
        LocalDate now = LocalDate.now();
        //Initialize date format
        dFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        //Current Date
        currentDateLabel = new JLabel(LocalDate.now().format(dFormat));

        /* JPANEL STRUCTURE: VISUALIZED
         *   ___________________________________________
         *  |   container                               |
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
        //Current Date Container
        JPanel currentDatePanel = new JPanel(new GridBagLayout());
        GridBagConstraints dateGbc = new GridBagConstraints();

        dateGbc.gridx = 0;
        dateGbc.gridy = 0;
        dateGbc.weightx = 1;
        dateGbc.weighty = 1;
        dateGbc.anchor = GridBagConstraints.CENTER;
        dateGbc.insets = new Insets(15, 0, 20, 0);
        currentDatePanel.add(currentDateLabel, dateGbc);

        add(currentDatePanel); //Add currentDatePanel to JFrame


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
        dateText.setText("12/11/2020");
        billToText = new JTextField(12);
        billToText.setText("Maiku");
        nameText = new JTextField(12);
        nameText.setText("Maiku");
        birthText = new JTextField(12);
        birthText.setText("12/11/2009");
        passportText = new JTextField(12);
        passportText.setText("123456");
        //JButton
        nextBtn = new JButton("Next");
        clearBtn = new JButton("Clear");
        //JButton ActionListener
        nextBtn.addActionListener(this);
        clearBtn.addActionListener(this);
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

        ////////// SEPARATOR //////////
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1;
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(10, 8, 10, 8);
        widgetPanel.add(new JSeparator(JSeparator.HORIZONTAL),gbc);


        ////////// SUBMIT / CLEAR //////////
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weighty = 0;
        gbc.weightx = 0.1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 0, 8, 0);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_END;
        widgetPanel.add(clearBtn, gbc);

        gbc.gridx = 3;
        widgetPanel.add(nextBtn, gbc);

        //Adding JPanels to parent JPanel
        innerContainer.add(widgetPanel);

        container.add(innerContainer);

        //Add container to JFrame
        add(container, BorderLayout.PAGE_START);
    }

    //This method gets called when either "Clear" or "Next" button is clicked
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Clear")){ //If Clear button is clicked
            clear();
        } else if(e.getActionCommand().equals("Next")){ //If Next button is clicked
            if(validateInputs()){ //CHANGE TO MAKE VALIDATION WORK!!!!!!
                //Go to next step: Entering information for Products/Services purchased
                Customer c = new Customer(
                        dateText.getText(),
                        billToText.getText(),
                        nameText.getText(),
                        birthText.getText(),
                        genderCombo.getSelectedItem().toString(),
                        passportText.getText());
                new SecondFrame(c);
                setVisible(false);
            }
        }
    }

    //Clears entered values and resets borders to default
    private void clear(){
        dateText.setText("");
        dateText.setBorder(defaultBorder);
        billToText.setText("");
        billToText.setBorder(defaultBorder);
        nameText.setText("");
        nameText.setBorder(defaultBorder);
        birthText.setText("");
        birthText.setBorder(defaultBorder);
        genderCombo.setSelectedIndex(0);
        genderCombo.setBorder(defaultJComboBorder);
        passportText.setText("");
        passportText.setBorder(defaultBorder);
    }

    //Makes sure values entered are valid
    private boolean validateInputs() {
        //Custom borders for invalid input values
        errorBorder = BorderFactory.createLineBorder(Color.RED, 1);
        defaultBorder = new JTextField().getBorder();
        defaultJComboBorder = new JComboBox().getBorder();

        boolean valid = true;

        if(!(validateDate() && validateText() && validateGender())){
            valid = false;
        }

        return valid;

    }

    //Validates inputs from date and date of birth
    private boolean validateDate(){
        boolean valid = true;
        DateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy");
        //Input to be parsed should strictly follow the defined date format above
        dFormat.setLenient(false);

        try {
            dFormat.parse(dateText.getText());
            dateText.setBorder(defaultBorder);
        } catch(ParseException e ){
            dateText.setBorder(errorBorder);
            valid = false;
        }

        try {
            dFormat.parse(birthText.getText());
            birthText.setBorder(defaultBorder);
        } catch (ParseException e){
            birthText.setBorder(errorBorder);
            valid = false;
        }

        if(!valid) {
            JOptionPane.showMessageDialog(this, "The date should be in the format 'dd/MM/yyyy'");
        }

        return valid;
    }

    //Validates inputs from name, billTo and passport number
    private boolean validateText(){

        boolean valid = true;

        if(billToText.getText().isEmpty()){
            valid = false;
            billToText.setBorder(errorBorder);
        } else {
            billToText.setBorder(defaultBorder);
        }

        if(nameText.getText().isEmpty()){
            valid = false;
            nameText.setBorder(errorBorder);
        } else {
            nameText.setBorder(defaultBorder);
        }

        if(passportText.getText().isEmpty()){
            valid = false;
            passportText.setBorder(errorBorder);
        } else {
            passportText.setBorder(defaultBorder);
        }

        if(!valid){
            JOptionPane.showMessageDialog(this,"Please enter valid text.");
        }

        return valid;
    }

    //Validates gender JCombobox
    private boolean validateGender(){
        boolean valid = true;
        if(genderCombo.getSelectedIndex() == 0){
            valid = false;
            genderCombo.setBorder(errorBorder);
            JOptionPane.showMessageDialog(this, "Select a valid gender.");
            System.out.println("Error at gender selection");
        } else {
            genderCombo.setBorder(defaultJComboBorder);
        }

        return valid;
    }
}
