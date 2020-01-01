public class Customer {
    String date, billTo, name, dateOfBirth, gender, passportNum;

    public Customer(String date, String billTo, String name, String dateOfBirth, String gender, String passportNum){
        this.date = date;
        this.billTo = billTo;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.passportNum = passportNum;
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
}
