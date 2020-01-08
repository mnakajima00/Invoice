import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class PrintInvoice extends JFrame {
    //Customer object
    Customer c;
    //JFrame Components
    JPanel container;
    JEditorPane invoicePreview;
    GridBagConstraints gbc;

    public PrintInvoice(Customer c){
        super("Invoice");

        //Initialize Customer
        this.c = c;

        //Initialize JFrame
        setLayout(new FlowLayout());

        addWidgets();

        pack();
        setLocationRelativeTo(null); // Set frame to center
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void addWidgets(){
        container = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();

        //Setup JEditorPane
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 3;
        setupPane(container);
        container.setBorder(BorderFactory.createTitledBorder("PREVIEW"));


        add(container);
    }

    private void setupPane(JPanel container){
        invoicePreview = new JEditorPane();
        HTMLEditorKit editorKit = new HTMLEditorKit();
        invoicePreview.setEditorKit(editorKit);
        invoicePreview.setEditable(false);
        invoicePreview.setContentType("text/html");
        JScrollPane jsp = new JScrollPane(invoicePreview);
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jsp.setMinimumSize(new Dimension(400, 300));
        jsp.setPreferredSize(jsp.getMinimumSize());

        File invoiceTemp = new File("src/invoice_template.html");
        File finalInvoice = new File("src/invoice_"+c.getPassportNum());

        try {
            finalInvoice.createNewFile(); //Creates a new temp file "preview_temp.html"
            FileWriter fw = new FileWriter(finalInvoice);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(generateInvoiceHTML(invoiceTemp)); //Writes the HTML code to temp HTML file
            bw.close();

            //JEditorPane's setPage only renders once in itself lifetime
            //We use the 2 lines of code below as a workaround
            javax.swing.text.Document doc = invoicePreview.getDocument();
            doc.putProperty(javax.swing.text.Document.StreamDescriptionProperty, null);

            invoicePreview.setPage(finalInvoice.toURI().toURL());
        } catch (IOException e) {
            e.printStackTrace();
        }

        container.add(jsp, gbc);

    }

    private String generateInvoiceHTML(File f){
        Document d = null;
        try {
            d = Jsoup.parse(f, "UTF-8", "");
        } catch (IOException err) {
            err.printStackTrace();
        }

        Elements e = d.select("span#billTo").append(c.getBillTo());
        e = d.select("span#name").append(c.name);
        e = d.select("span#dateOfBirth").append(c.getDateOfBirth());
        e = d.select("span#gender").append(c.getGender());
        e = d.select("span#passportNo").append(c.getPassportNum());
        e = d.select("span#date").append(c.getDate());


        return d.toString();
    }

}
