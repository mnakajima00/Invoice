import java.time.LocalDate;
import java.util.*;

public class Customer {
    String date, billTo, name, dateOfBirth, gender, passportNum;
    TreeMap<String, HashMap<String, Double>> totalInvoice; //List of invoices the customer has
    double totalPrice;
    public Customer(String date, String billTo, String name, String dateOfBirth, String gender, String passportNum) {
        this.date = date;
        this.billTo = billTo;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.passportNum = passportNum;
        totalPrice = 0.0;
        Comparator<String> sortByDate = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String[] splitDate1 = separateDate(o1);
                int day1 = Integer.parseInt(splitDate1[0]);
                int month1 = Integer.parseInt(splitDate1[1]);
                int year1 = Integer.parseInt(splitDate1[2]);

                String[] splitDate2 = separateDate(o2);
                int day2 = Integer.parseInt(splitDate2[0]);
                int month2 = Integer.parseInt(splitDate2[1]);
                int year2 = Integer.parseInt(splitDate2[2]);

                if (year1 > year2 || (year1 == year2 && month1 > month2) ||
                        (year1 == year2 && month1 == month2 && day1 > day2) ||
                        (year1 == year2 && month1 == month2 && day1 == day2)) {
                    return 1;
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

    public String getPassportNum() {
        return passportNum;
    }

    public TreeMap<String, HashMap<String, Double>> getAllInvoices(){
        return totalInvoice;
    }

    public void addToInvoiceList(String date, LinkedHashMap<String, Double> purchases){
        this.totalInvoice.put(date, purchases);
    }

    public Double totalPrice(){
        for(HashMap<String, Double> invoice : totalInvoice.values()) {
            for(Double price : invoice.values()){
                totalPrice += price;
            }
        }

        return totalPrice;
    }

    private String[] separateDate(String date){
        return date.split("/");
    }


}
