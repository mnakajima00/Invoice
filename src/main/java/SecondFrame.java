import apple.laf.JRSUIUtils;
import com.sun.codemodel.internal.JFieldRef;
import com.sun.xml.internal.ws.util.QNameMap;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class SecondFrame extends JFrame implements ActionListener {
    //Customer class declaration
    Customer c;
    //Products class dec
    Products p;
    //JFrame components declaration
    JLabel date, symptomsLabel, doctorLabel;
    JTextField dateText, doctorText;
    JTextArea symptomsText;
    JButton addRowsButton,removeRowsButton, addToInvoiceButton, nextButton, backButton;
    JTable table;
    JEditorPane invoicePreviewPane;
    //JPanel
    JPanel rightPanel;

    //Table
    DefaultTableModel model;
    String[] col;
    Object[][] data;

    public SecondFrame(Customer c){
        super("Invoice");

        //Initialize Customer Class
        this.c = c;

        //Init Products class
        p = new Products();
        //Initialize JFrame
        setLayout(new FlowLayout());

        addWidgets();

        pack();
        setLocationRelativeTo(null); // Set frame to center
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

        /////Container/////
        JPanel container = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        container.setBorder(new EmptyBorder(15, 15, 15, 15));

        /////MainPanel JPanel/////
        JPanel mainPanel = new JPanel(new GridLayout(0, 2));
        gbc.gridx= 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        //Method that adds all widgets in mainPanel
        buildMainPanel(mainPanel);
        //Add mainPanel to container
        container.add(mainPanel, gbc);

        /////Back and Next Button JPanel/////
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        container.add(backButton, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        container.add(nextButton, gbc);

        /////Add container to JFrame/////
        add(container);

    }

    private void buildMainPanel(JPanel mainPanel){
        Border b = BorderFactory.createTitledBorder("INVOICE EDITOR");
        /////leftPanel/////
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBorder(b);
        buildLeftPanel(leftPanel);
        //add leftPanel to mainJPanel
        mainPanel.add(leftPanel);

        /////rightPanel/////
        rightPanel = new JPanel(new GridBagLayout());
        //rightPanel.setBorder(br);
        buildRightPanel(rightPanel);
        //add rightPanel to mainJPanel
        mainPanel.add(rightPanel);
    }

    private void buildLeftPanel(JPanel leftPanel){
        //Initialize components
        date = new JLabel("Date:");
        symptomsLabel = new JLabel("Symptoms:");
        doctorLabel = new JLabel("Attending Doctor: Dr.");
        doctorText = new JTextField(12);
        dateText = new JTextField(8);
        dateText.setText("11/12/2020");
        dateText.addActionListener(this);
        //JButtons initialization and add action listener
        addRowsButton = new JButton("Add Row");
        addRowsButton.addActionListener(this);
        removeRowsButton = new JButton("Remove Row");
        removeRowsButton.addActionListener(this);
        addToInvoiceButton = new JButton("Add to Invoice");
        addToInvoiceButton.addActionListener(this);
        nextButton = new JButton("Next");
        nextButton.addActionListener(this);
        backButton = new JButton("Back");
        backButton.addActionListener(this);

        GridBagConstraints gbc = new GridBagConstraints();

        /////DATE COMPONENTS/////
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 20, 0, 20);
        gbc.anchor = GridBagConstraints.LINE_START;

        JPanel datePanel = new JPanel();
        datePanel.add(date);
        datePanel.add(dateText);
        leftPanel.add(datePanel, gbc);

        /////JTable/////
        buildTable(leftPanel, gbc);

        /////JButtons - "+", "-" & "Add to Invoice"/////
        gbc.insets = new Insets(0, 10, 0, 10);
        gbc.gridx = 0;
        gbc.gridy = 2;

        JPanel btnPanel = new JPanel();
        btnPanel.add(addRowsButton);
        btnPanel.add(removeRowsButton);
        leftPanel.add(btnPanel, gbc);

        JPanel addToInvoicePanel = new JPanel();
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.LINE_END;
        leftPanel.add(addToInvoicePanel, gbc);
        addToInvoicePanel.add(addToInvoiceButton);
        leftPanel.add(addToInvoicePanel, gbc);

        /////JSeparator/////
        JSeparator js1 = new JSeparator(JSeparator.HORIZONTAL);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 20, 0, 20);
        leftPanel.add(js1, gbc);

        ///// JTEXTAREA /////
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(0, 20, 5, 0);
        leftPanel.add(symptomsLabel, gbc);

        symptomsText = new JTextArea(5, 10);
        symptomsText.setLineWrap(true);
        JScrollPane textAreaScrollPane = new JScrollPane(symptomsText);
        gbc.insets = new Insets(0, 10, 10, 10);
        symptomsText.setBorder(new JTextField().getBorder());
        gbc.gridy = 5;
        leftPanel.add(textAreaScrollPane, gbc);

        /////JSeparator/////
        JSeparator js2 = new JSeparator(JSeparator.HORIZONTAL);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 0.1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 20, 0, 20);
        leftPanel.add(js2, gbc);

        /////DOCTOR/////
        gbc.gridy = 7;
        gbc.insets = new Insets(0, 20, 0, 20);
        gbc.fill = GridBagConstraints.NONE;

        JPanel doctorPanel = new JPanel();
        doctorPanel.add(doctorLabel);
        doctorPanel.add(doctorText);
        leftPanel.add(doctorPanel, gbc);

    }
    private void buildTable(JPanel leftPanel, GridBagConstraints gbc){
        //Initialize Default Table Data
        model = new DefaultTableModel(){

            //Depending on column, change data type
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if(columnIndex == 2){ //Quantity Column as Integer type
                    return Integer.class;
                }else if(columnIndex == 3){ //Price column as primitive type Double
                    return Double.class;
                } else { //Anything else will be of class String
                    return String.class;
                }
            }

            //When a cell is updated, this method ss called
            @Override
            public void fireTableCellUpdated(int row, int column) {
                Object data = model.getValueAt(row, column);
                //If any cell in column "qty" is changed, assuming that user has entered a valid integer,
                //calculate the price
                if(column == 2 && row < 4 && data instanceof Integer && (int)data > 0){
                    Double price = p.getProductPrice(model.getValueAt(row, 1).toString());
                    model.setValueAt(
                            price * (int)data, //Multiply qty with price of the corresponding product/service
                            row,
                            column + 1
                    );
                } else if(column == 2 && (int)data < 0){
                    model.setValueAt(0, row, column); //If qty is negative, change to 0
                    JOptionPane.showMessageDialog(leftPanel, "Enter a positive value.");
                }

            }
        };
        col = new String[]{
                "No.",
                "Description",
                "Qty",
                "Price"
        };
        data = new Object[][]{
                {1, "Consultation", 0, 0.0},
                {2, "Special Acupuncture Treatment", 0, 0.0},
                {3, "Medical Machine Therapy", 0, 0.0},
                {4, "Tapping Treatment", 0, 0.0}
        };

        model.setDataVector(data, col);
        /////Initialize Table/////
        table = new JTable(model);
        table.setMinimumSize(new Dimension(450, 150));
        JScrollPane scrollPane = new JScrollPane(table);
        table.setPreferredScrollableViewportSize(table.getMinimumSize());
        table.setFillsViewportHeight(true);
        /////JTable customization/////
        //Center table contents
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(String.class, renderer);
        table.setDefaultRenderer(Integer.class, renderer);
        table.setDefaultRenderer(Double.class, renderer);
        //Center table header
        renderer = (DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer(); //Center table headers
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        //Table resizing
        table.getTableHeader().setResizingAllowed(false); //Do not allow resizing of table
        table.getTableHeader().setReorderingAllowed(false); //Do not allow reordering of columns
        //Adjust Column width
        for(int i = 0; i < table.getColumnCount(); i++){
            TableColumn tc = table.getColumnModel().getColumn(i);
            if(i == 0){
                tc.setPreferredWidth(50);
            }else if(i == 1){
                tc.setPreferredWidth(200);
            } else if(i == 2) {
                tc.setPreferredWidth(50);
            }else if(i == 3){
                tc.setPreferredWidth(50);
            }
        }

        //Table location
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 20, 0, 20);
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        leftPanel.add(scrollPane, gbc);

    }

    private void buildRightPanel(JPanel rightPanel){

        GridBagConstraints gbc = new GridBagConstraints();

        ///// EditorPane /////
        // Initial EditorPane Setup
        invoicePreviewPane = new JEditorPane();
        HTMLEditorKit editorKit = new HTMLEditorKit();
        invoicePreviewPane.setEditorKit(editorKit);
        invoicePreviewPane.setEditable(false);
        invoicePreviewPane.setContentType("text/html");
        JScrollPane jsp = new JScrollPane(invoicePreviewPane);
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jsp.setMinimumSize(new Dimension(400, 450));
        jsp.setPreferredSize(jsp.getMinimumSize());

        //GridBagLayout Constraints setup
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //Add EditorPane to rightPanel
        rightPanel.add(jsp, gbc);

    }

    private void setupPreview(JEditorPane invoicePreviewPane){
        //Get HTML file and show it on JEditorPane
        File f = new File("src/main/java/preview.html");
        File edited = new File("src/main/java/preview_temp.html");

        try {
            edited.createNewFile(); //Creates a new temp file "preview_temp.html"
            edited.deleteOnExit(); //Deletes file on exit
            FileWriter fw = new FileWriter(edited);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(writeToHTMLDoc(f)); //Writes the HTML code to temp HTML file
            bw.close();

            //JEditorPane's setPage only renders once in itself lifetime
            //We use the 2 lines of code below as a workaround
            javax.swing.text.Document doc = invoicePreviewPane.getDocument();
            doc.putProperty(javax.swing.text.Document.StreamDescriptionProperty, null);

            invoicePreviewPane.setPage(edited.toURI().toURL());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //This method generates the html code for preview.html. returns the code as a String
    private String writeToHTMLDoc(File f){
        Document d = null;
        Element e;
        try {
            d = Jsoup.parse(f, "UTF-8", "");
            e = d.select("div#container").first().appendElement("div")
                    .addClass("invoice");
            for(Map.Entry<String, LinkedHashMap<String, Double>> entry : c.getAllInvoices().entrySet()) {
                String date = entry.getKey();
                e.appendElement("h3").text("Date: " + date);
                for(Map.Entry<String, Double> map : entry.getValue().entrySet()){
                    String productName = map.getKey();
                    Double price = map.getValue();
                    e.appendElement("p").text("Product: " + productName + " Price: " + price);
                }
            }

            e.appendElement("h3").text("Total: " + c.getTotalPrice());
            System.out.println(d);
        } catch (IOException err) {
            err.printStackTrace();
        }

        return d.toString();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        if(e.getActionCommand().equals("Add Row")){ //When + button is clicked
            model.addRow(new Object[]{ //Add new row
                    p.numCounter,
                    "",
                    0,
                    0.0
            });
            p.numCounter++;
        } else if(e.getActionCommand().equals("Remove Row")){
            if(p.numCounter > 5){
                model.removeRow(table.getRowCount()-1);
                p.numCounter--;
            }
        } else if(e.getActionCommand().equals("Add to Invoice")){
            //Validate inputs
            if(validateInputs()) {
                LinkedHashMap<String, Double> invoice = new LinkedHashMap<>();
                for (int i = 0; i < model.getRowCount(); i++) {
                    String desc = (String) model.getValueAt(i, 1);
                    Double price = (Double) model.getValueAt(i, 3);
                    invoice.put(desc, price);
                }
                //Add invoice to Customer class
                c.addToInvoiceList(dateText.getText(), invoice);
                System.out.println("Number of invoice(s):"+c.getAllInvoices().size());
                //Show invoice(s) in preview
                setupPreview(invoicePreviewPane);
                //Reset inputs
                resetInputs();
            }
        } else if(e.getActionCommand().equals("Back")){
            //If user clicks the Back button, goes back to MainFrame
            new MainFrame(c.getDate(), c.getBillTo(), c.getDateOfBirth(), c.getName(), c.getGenderIndex(c.getGender()), c.getPassportNum());
            setVisible(false);
        } else if(e.getActionCommand().equals("Next")){
            //If user clicks the Next button, it opens the PDF
            c.setSymptoms(symptomsText.getText());
            c.setAttendingDoctor(doctorText.getText());
            try {
                c.printInvoice(this);
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

        }
    }

    private boolean validateInputs(){
        boolean valid = true;

        //Date format
        DateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy");
        //Input to be parsed should strictly follow the defined date format above
        dFormat.setLenient(false);

        //Make sure Date is not empty & that the there are no two same dates in the invoice
        if(!dateText.getText().isEmpty()){
            if(!c.dateExists(dateText.getText())) {
                try {
                    dFormat.parse(dateText.getText());
                    dateText.setBorder(new JTextField().getBorder());
                } catch (ParseException e) {
                    valid = false;
                    dateText.setBorder(BorderFactory.createLineBorder(Color.RED));
                    JOptionPane.showMessageDialog(this, "Enter a date in the form dd/MM/yyyy");
                }
            } else {
                valid = false;
                dateText.setBorder(BorderFactory.createLineBorder(Color.RED));
                JOptionPane.showMessageDialog(this, "Date already exists.");
            }
        } else {
            valid = false;
            dateText.setBorder(BorderFactory.createLineBorder(Color.RED));
            JOptionPane.showMessageDialog(this, "Enter a date.");
        }

        return valid;
    }

    private void resetInputs(){
        //Reset date
        dateText.setText("");
        //Reset Table
        model.setRowCount(4);
        //Table QTY
        model.setValueAt(0, 0, 2);
        model.setValueAt(0, 1, 2);
        model.setValueAt(0, 2, 2);
        model.setValueAt(0, 3, 2);
        //Table Price
        model.setValueAt(0.0, 0, 3);
        model.setValueAt(0.0, 1, 3);
        model.setValueAt(0.0, 2, 3);
        model.setValueAt(0.0, 3, 3);
        model.fireTableDataChanged();
    }
}
