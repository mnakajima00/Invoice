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

public class SecondFrame extends JFrame implements ActionListener {
    //Customer class declaration
    Customer c;
    //Products class dec
    Products p;
    //JFrame components declaration
    JLabel date;
    JTextField dateText;
    JButton addRowsButton,removeRowsButton, addToInvoiceButton;
    JTable table;
    JEditorPane invoicePreviewPane;
    //JPanel
    JPanel rightPanel;

    DefaultTableModel model;

    public SecondFrame(Customer c){
        super("Invoice");

        //Initialize Customer Class
        this.c = c;
        System.out.println(c);
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
        JPanel container = new JPanel(new GridLayout(0, 1));
        container.setBorder(new EmptyBorder(15, 15, 15, 15));

        /////MainPanel JPanel/////
        JPanel mainPanel = new JPanel(new GridLayout(0, 2));
        //Method that adds all widgets in mainPanel
        buildMainPanel(mainPanel);
        //Add mainPanel to container
        container.add(mainPanel);

        /////Symptoms JPanel/////

        /////Back and Next Button JPanel/////

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
        dateText = new JTextField(8);
        dateText.addActionListener(this);
        //JButtons initialization and add action listener
        addRowsButton = new JButton("Add Row");
        addRowsButton.addActionListener(this);
        removeRowsButton = new JButton("Remove Row");
        removeRowsButton.addActionListener(this);
        addToInvoiceButton = new JButton("Add to Invoice");
        addToInvoiceButton.addActionListener(this);

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
        JSeparator js = new JSeparator(JSeparator.HORIZONTAL);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 20, 0, 20);
        leftPanel.add(js, gbc);

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
        String[] col = {
                "No.",
                "Description",
                "Qty",
                "Price"
        };
        Object[][] data = {
                {1, "Consultation", 0, 0.0},
                {2, "Special Acupuncture Treatment", 0, 0.0},
                {3, "Medical Machine Therapy", 0, 0.0},
                {4, "Tapping Treatment", 0, 0.0}
        };

        model.setDataVector(data, col);
        /////Initialize Table/////
        table = new JTable(model);
        table.setMinimumSize(new Dimension(400, 150));
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
        jsp.setMinimumSize(new Dimension(400, 300));
        jsp.setPreferredSize(jsp.getMinimumSize());

        //GridBagLayout Constraints setup
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.HORIZONTAL;
        gbc.gridheight = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //Add EditorPane to rightPanel
        rightPanel.add(jsp, gbc);

    }

    private void setupPreview(JEditorPane invoicePreviewPane){
        //Get HTML file and show it on JEditorPane
        File f = new File("src/preview.html");
        File edited = new File("src/preview_temp.html");

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

    //This method will write html code to the html file (preview.html)
    private String writeToHTMLDoc(File f){
        Document d = null;

        try {
            d = Jsoup.parse(f, "UTF-8", "");
            for(Map.Entry<String, HashMap<String, Double>> entry : c.getAllInvoices().entrySet()) {
                Element e = d.select("div#container").first().appendElement("div")
                        .addClass("invoice");
                String date = "Date: " + entry.getKey();
                e.appendElement("h3").text(date);

                for(Map.Entry<String, Double> map: entry.getValue().entrySet()){

                    String productName = map.getKey();
                    double price = map.getValue();

                    e.appendElement("p").text(productName + ": " + price + "RM");

                }
            }
            //System.out.println(d);
        } catch (IOException e) {
            e.printStackTrace();
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
                LinkedHashMap<String, Double> products = new LinkedHashMap<>();
                for (int i = 0; i < model.getRowCount(); i++) {
                    String desc = (String) model.getValueAt(i, 1);
                    Double price = (Double) model.getValueAt(i, 3);
                    products.put(desc, price);
                }
                //Add invoice to Customer class
                c.addToInvoiceList(dateText.getText(), products);
                System.out.println("Number of invoice(s):"+c.getAllInvoices().size());
                //Show invoice(s) in preview
                setupPreview(invoicePreviewPane);
            }
        }
    }

    private boolean validateInputs(){
        boolean valid = true;

        //Date format
        DateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy");
        //Input to be parsed should strictly follow the defined date format above
        dFormat.setLenient(false);

        //Make sure Date is not empty
        if(!dateText.getText().isEmpty()){
            try {
                dFormat.parse(dateText.getText());
                dateText.setBorder(new JTextField().getBorder());
            } catch(ParseException e ){
                valid = false;
                dateText.setBorder(BorderFactory.createLineBorder(Color.RED));
                JOptionPane.showMessageDialog(this, "Enter a date in the form dd/MM/yyyy");
            }
        } else {
            valid = false;
            dateText.setBorder(BorderFactory.createLineBorder(Color.RED));
            JOptionPane.showMessageDialog(this, "Enter a date.");
        }

        return valid;
    }
}
