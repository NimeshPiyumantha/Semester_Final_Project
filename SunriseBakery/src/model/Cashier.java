package model;

public class Cashier {

    private String CashierID;
    private String CashierName;
    private String CashierPsw;
    private String CashierAddress;
    private String CashierCNumber;
    private String CashierNIC;
    private double CashierSalary;
    private String CashierDescription;
    private String CashierAddDate;
    private String CashierAddTime;

    public Cashier() {
    }

    public Cashier(String cashierID, String cashierName, String cashierPsw, String cashierAddress, String cashierCNumber, String cashierNIC, double cashierSalary, String cashierDescription, String cashierAddDate, String cashierAddTime) {
        CashierID = cashierID;
        CashierName = cashierName;
        CashierPsw = cashierPsw;
        CashierAddress = cashierAddress;
        CashierCNumber = cashierCNumber;
        CashierNIC = cashierNIC;
        CashierSalary = cashierSalary;
        CashierDescription = cashierDescription;
        CashierAddDate = cashierAddDate;
        CashierAddTime = cashierAddTime;
    }

    public String getCashierID() {
        return CashierID;
    }

    public void setCashierID(String cashierID) {
        CashierID = cashierID;
    }

    public String getCashierName() {
        return CashierName;
    }

    public void setCashierName(String cashierName) {
        CashierName = cashierName;
    }

    public String getCashierPsw() {
        return CashierPsw;
    }

    public void setCashierPsw(String cashierPsw) {
        CashierPsw = cashierPsw;
    }

    public String getCashierAddress() {
        return CashierAddress;
    }

    public void setCashierAddress(String cashierAddress) {
        CashierAddress = cashierAddress;
    }

    public String getCashierCNumber() {
        return CashierCNumber;
    }

    public void setCashierCNumber(String cashierCNumber) {
        CashierCNumber = cashierCNumber;
    }

    public String getCashierNIC() {
        return CashierNIC;
    }

    public void setCashierNIC(String cashierNIC) {
        CashierNIC = cashierNIC;
    }

    public double getCashierSalary() {
        return CashierSalary;
    }

    public void setCashierSalary(double cashierSalary) {
        CashierSalary = cashierSalary;
    }

    public String getCashierDescription() {
        return CashierDescription;
    }

    public void setCashierDescription(String cashierDescription) {
        CashierDescription = cashierDescription;
    }

    public String getCashierAddDate() {
        return CashierAddDate;
    }

    public void setCashierAddDate(String cashierAddDate) {
        CashierAddDate = cashierAddDate;
    }

    public String getCashierAddTime() {
        return CashierAddTime;
    }

    public void setCashierAddTime(String cashierAddTime) {
        CashierAddTime = cashierAddTime;
    }

    @Override
    public String toString() {
        return "Cashier{" +
                "CashierID='" + CashierID + '\'' +
                ", CashierName='" + CashierName + '\'' +
                ", CashierPsw='" + CashierPsw + '\'' +
                ", CashierAddress='" + CashierAddress + '\'' +
                ", CashierCNumber='" + CashierCNumber + '\'' +
                ", CashierNIC='" + CashierNIC + '\'' +
                ", CashierSalary=" + CashierSalary +
                ", CashierDescription='" + CashierDescription + '\'' +
                ", CashierAddDate='" + CashierAddDate + '\'' +
                ", CashierAddTime='" + CashierAddTime + '\'' +
                '}';
    }
}
