
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class SecondFrame extends JFrame implements ActionListener, TableModelListener {
    //Table variables
    int rows;
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

    DefaultTableModel model;

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
        Border br = BorderFactory.createLineBorder(Color.RED);
        JPanel rightPanel = new JPanel(new GridBagLayout());
        //rightPanel.setBorder(br);
        buildRightPanel(rightPanel);
        //add rightPanel to mainJPanel
        mainPanel.add(rightPanel);
    }

    private void buildLeftPanel(JPanel leftPanel){
        //Initialize components
        date = new JLabel("Date:");
        dateText = new JTextField(6);
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
        gbc.insets = new Insets(0, 20, 0, 20);
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

        //JSeparator
        JSeparator js = new JSeparator(JSeparator.HORIZONTAL);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 20, 0, 20);
        leftPanel.add(js, gbc);

        /////JButton - Add to Invoice/////
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 20, 0, 20);
        gbc.fill = GridBagConstraints.NONE;

    }
    private void buildTable(JPanel leftPanel, GridBagConstraints gbc){
        //Initialize Default Table Data
        model = new DefaultTableModel();
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
        //Initialize Table
        table = new JTable(model);
        table.setMinimumSize(new Dimension(400, 150));
        JScrollPane scrollPane = new JScrollPane(table);
        table.setPreferredScrollableViewportSize(table.getMinimumSize());
        table.setFillsViewportHeight(true);
        //JTable customization
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, renderer); //Centers all cells
        renderer = (DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer(); //Center table headers
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        //JTable action
        table.getModel().addTableModelListener(this);
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
       invoicePreviewPane = new JEditorPane();
       invoicePreviewPane.setEditable(false);
       JScrollPane jsp = new JScrollPane(invoicePreviewPane);
       jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
       jsp.setMinimumSize(new Dimension(400, 300));
       jsp.setPreferredSize(jsp.getMinimumSize());

       JPanel editorPanel = new JPanel();
       editorPanel.add(jsp);

       gbc.gridx = 0;
       gbc.gridy = 0;
       gbc.gridwidth = GridBagConstraints.HORIZONTAL;
       gbc.gridheight = GridBagConstraints.VERTICAL;
       gbc.anchor = GridBagConstraints.LINE_START;
       gbc.fill = GridBagConstraints.HORIZONTAL;

       rightPanel.add(editorPanel, gbc);

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
            HashMap<String, Double> products = new HashMap<>();
            for(int i = 0; i < model.getRowCount(); i++) {
                String desc = (String)model.getValueAt(i, 1);
                Double price = (Double)model.getValueAt(i, 3);
                products.put(desc, price);
            }
            //Add invoice to Customer class
            c.addToInvoiceList(dateText.toString(), products);
        }
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        int col = e.getColumn();
        int row = e.getFirstRow();
        TableModel model = (TableModel)e.getSource();

        /*
         * If a qty cell is changed, update the corresponding price cell. But only if the row is < 4 and col == 2.
         *
         */
        if(col == 2 && row < 4){
            Object data = model.getValueAt(row, col);
            model.setValueAt(
                    Integer.parseInt(data.toString())*p.getProductPrice((String)model.getValueAt(row, 1)),
                    row,
                    col+1);
        }
    }
}
