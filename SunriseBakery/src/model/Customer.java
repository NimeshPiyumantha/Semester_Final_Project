package model;

public class Customer {
    private String CustomerID;
    private String CustomerTitle;
    private String CustomerName;
    private String CustomerAddress;
    private String CustomerCity;
    private String CustomerProvince;
    private String CustomerPostCode;
    private String CustomerAddDate;
    private String CustomerAddTime;

    public Customer() {
    }

    public Customer(String customerID, String customerTitle, String customerName, String customerAddress, String customerCity, String customerProvince, String customerPostCode, String customerAddDate, String customerAddTime) {
        CustomerID = customerID;
        CustomerTitle = customerTitle;
        CustomerName = customerName;
        CustomerAddress = customerAddress;
        CustomerCity = customerCity;
        CustomerProvince = customerProvince;
        CustomerPostCode = customerPostCode;
        CustomerAddDate = customerAddDate;
        CustomerAddTime = customerAddTime;
    }

    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String customerID) {
        CustomerID = customerID;
    }

    public String getCustomerTitle() {
        return CustomerTitle;
    }

    public void setCustomerTitle(String customerTitle) {
        CustomerTitle = customerTitle;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getCustomerAddress() {
        return CustomerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        CustomerAddress = customerAddress;
    }

    public String getCustomerCity() {
        return CustomerCity;
    }

    public void setCustomerCity(String customerCity) {
        CustomerCity = customerCity;
    }

    public String getCustomerProvince() {
        return CustomerProvince;
    }

    public void setCustomerProvince(String customerProvince) {
        CustomerProvince = customerProvince;
    }

    public String getCustomerPostCode() {
        return CustomerPostCode;
    }

    public void setCustomerPostCode(String customerPostCode) {
        CustomerPostCode = customerPostCode;
    }

    public String getCustomerAddDate() {
        return CustomerAddDate;
    }

    public void setCustomerAddDate(String customerAddDate) {
        CustomerAddDate = customerAddDate;
    }

    public String getCustomerAddTime() {
        return CustomerAddTime;
    }

    public void setCustomerAddTime(String customerAddTime) {
        CustomerAddTime = customerAddTime;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "CustomerID='" + CustomerID + '\'' +
                ", CustomerTitle='" + CustomerTitle + '\'' +
                ", CustomerName='" + CustomerName + '\'' +
                ", CustomerAddress='" + CustomerAddress + '\'' +
                ", CustomerCity='" + CustomerCity + '\'' +
                ", CustomerProvince='" + CustomerProvince + '\'' +
                ", CustomerPostCode='" + CustomerPostCode + '\'' +
                ", CustomerAddDate='" + CustomerAddDate + '\'' +
                ", CustomerAddTime='" + CustomerAddTime + '\'' +
                '}';
    }
}
