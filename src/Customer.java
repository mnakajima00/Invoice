import java.util.HashMap;

public class Customer {
    String date, billTo, name, dateOfBirth, gender, passportNum;
    HashMap<String, HashMap<String, Double>> purchases; //List of invoices the customer has
    private double total;

    public Customer(String date, String billTo, String name, String dateOfBirth, String gender, String passportNum){
        this.date = date;
        this.billTo = billTo;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.passportNum = passportNum;
        total = 0;
        purchases = new HashMap<>();
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

    public void addToInvoiceList(String date, HashMap<String, Double> purchases){
        this.purchases.put(date, purchases);
    }

    public Double getTotal(){
        for(HashMap<String, Double> value : purchases.values()){
            for(Double price : value.values()){
                total += price;
            }
        }

        return total;
    }
}
