
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.pdf.PDFCreationListener;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Customer {
    String date, billTo, name, dateOfBirth, gender, passportNum, symptoms, attendingDoctor;
    TreeMap<String, LinkedHashMap<String, Double>> totalInvoice; //List of invoices the customer has
    public Customer(String date, String billTo, String name, String dateOfBirth, String gender, String passportNum) {
        this.date = date;
        this.billTo = billTo;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.passportNum = passportNum;
        this.symptoms = "";
        this.attendingDoctor = "";
        Comparator<String> sortByDate = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String[] splitDate1 = o1.split("/");
                int day1 = Integer.parseInt(splitDate1[0]);
                int month1 = Integer.parseInt(splitDate1[1]);
                int year1 = Integer.parseInt(splitDate1[2]);

                String[] splitDate2 = o2.split("/");
                int day2 = Integer.parseInt(splitDate2[0]);
                int month2 = Integer.parseInt(splitDate2[1]);
                int year2 = Integer.parseInt(splitDate2[2]);

                if (year1 > year2 //If the year for o1 is > the year for o2
                        || (year1 == year2 && month1 > month2) //If they both have the same year but month of o1 > month of o2
                        || (year1 == year2 && month1 == month2 && day1 > day2)){ //Same month and year but day of o1 > day of
                    return 1;
                } else if((year1 == year2 && month1 == month2 && day1 == day2)){ //If they have the same year, month and day
                    return 0;
                } else {
                    return -1;
                }
            }
        };

        totalInvoice = new TreeMap<>(sortByDate);
    }

    public String getDate() {
        return date;
    }

    public String getBillTo() {
        return billTo;
    }

    public String getName() {
        return name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public int getGenderIndex(String gender){
        if(gender == "Male"){
            return 1;
        } else if(gender == "Female"){
            return 2;
        } else {
            return 0;
        }
    }

    public String getPassportNum() {
        return passportNum;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public String getAttendingDoctor() {
        return attendingDoctor;
    }

    public void setAttendingDoctor(String attendingDoctor) {
        this.attendingDoctor = attendingDoctor;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public TreeMap<String, LinkedHashMap<String, Double>> getAllInvoices(){
        return totalInvoice;
    }

    public void addToInvoiceList(String date, LinkedHashMap<String, Double> purchases){
        this.totalInvoice.put(date, purchases);
    }

    public Double getTotalPrice(){
        double totalPrice = 0.0;

        for(LinkedHashMap<String, Double> entry : totalInvoice.values()){
            for(Double d : entry.values()){
                totalPrice+=d;
            }
        }
        return totalPrice;
    }

    //Checks for duplicate keys in Treemap
    public boolean dateExists(String date){
        return totalInvoice.containsKey(date);
    }

    public void printInvoice(SecondFrame sf) throws IOException, InterruptedException {
        //invoice template
        File invoiceTemp = new File("src/main/java/invoice_template.html");
        //New file to save final invoice
        File finalInvoice = new File("src/main/java/invoice_"+getPassportNum()+".html");

        try {
            finalInvoice.createNewFile(); //Creates a new html file where the final invoice will be generated (in HTML)
            finalInvoice.deleteOnExit(); //The HTML file will be deleted when the use exits from the application
            FileWriter fw = new FileWriter(finalInvoice);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(generateInvoiceHTML(invoiceTemp)); //Writes the HTML code to temp HTML file - finalInvoice
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String inputFile = "src/main/java/invoice_"+getPassportNum()+".html"; //Final invoice in HTML
        String outputFile = "src/main/java/invoice_"+getPassportNum()+".pdf"; //Final invoice location in pdf

        String html = new String(Files.readAllBytes(Paths.get(inputFile)));
        final Document document = Jsoup.parse(html);
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml); //Parse html in XHTML

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(document.html());
        renderer.layout();

        try(OutputStream os = Files.newOutputStream(Paths.get(outputFile))){
            renderer.setListener(new PDFCreationListener() {
                JProgressBar pb;
                @Override
                public void preOpen(ITextRenderer iTextRenderer) {
                    pb = new JProgressBar(0, 100);
                    pb.setStringPainted(true);
                    pb.setValue(33);
                    sf.setVisible(true);
                    sf.add(pb);

                }

                @Override
                public void preWrite(ITextRenderer iTextRenderer, int i){
                    pb.setValue(66);
                }

                @Override
                public void onClose(ITextRenderer iTextRenderer) {
                    pb.setValue(100);
                    pb.setVisible(false);
                }
            });
            renderer.createPDF(os);
            Desktop desktop = Desktop.getDesktop();
            File output = new File(outputFile);
            if(output.exists()){
                desktop.open(output);
            }
        }


    }

    public String generateInvoiceHTML(File f){
        Document d = null;
        try {
            d = Jsoup.parse(f, "UTF-8", "");
        } catch (IOException err) {
            err.printStackTrace();
        }
        File absPathToCSS = new File("src/main/java/invoice_template.css");

        //Get the absolute  path to CSS file
        Element abs = d.select("link").first().attr("href", "file://"+absPathToCSS.getAbsolutePath());
        abs = d.select("link").last().attr("href", "file://"+absPathToCSS.getAbsolutePath());

        //Adding HTML code for Customer Info
        Elements e = d.select("span#billTo").append(" "+getBillTo());
        e = d.select("span#name").append(" "+getName());
        e = d.select("span#dateOfBirth").append(" "+getDateOfBirth());
        e = d.select("span#gender").append(" "+getGender());
        e = d.select("span#passportNo").append(" "+getPassportNum());
        e = d.select("span#date").append(" "+getDate());

        //Adding HTML code for Invoice
        for(Map.Entry<String, LinkedHashMap<String, Double>> entry : totalInvoice.entrySet()){
            String date = entry.getKey();
            int productCount = 0;

            Element tableInvoiceElement = d.getElementById("invoiceColumn")
                    .appendElement("table")
                    .appendElement("tr").addClass("date")
                    .appendElement("td").text(date); //Date row
            Element tableAmountElement = d.getElementById("amountColumn")
                    .appendElement("table")
                    .appendElement("tr").appendElement("td").text("-");
            for(Map.Entry<String, Double> map : entry.getValue().entrySet()){
                 String product = map.getKey();
                 double price = map.getValue();
                productCount += 1;

                //Invoice Date
                tableInvoiceElement = d.getElementById("invoiceColumn")
                        .getElementsByTag("table").last()
                        .appendElement("tr")//Product No. & Description
                        .appendElement("td").addClass("productCount").text(Integer.toString(productCount));
                tableInvoiceElement.appendElement("td").addClass("product").text(product);

                tableAmountElement = d.getElementById("amountColumn") //Add an empty row for date
                        .getElementsByTag("table").last()
                        .appendElement("tr")
                        .appendElement("td").text(Double.toString(price));
            }
        }

        //Adding Symptoms
        Element symptomsHTML = d.getElementById("symptoms")
                .appendElement("td").text("*Symptoms: "+getSymptoms());
        symptomsHTML = d.getElementById("symptoms")
                .appendElement("td");

        //Adding Total
        Element totalHTML = d.getElementById("totalPrice")
                .appendElement("td");
        totalHTML = d.getElementById("totalPrice")
                .appendElement("td").text("Total: " + getTotalPrice());

        //Adding doctor
        Element doctorHTML = d.getElementById("doctor")
                .text("(Dr. "+getAttendingDoctor()+")");

        return d.toString();
    }


}
