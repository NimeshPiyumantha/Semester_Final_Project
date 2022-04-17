package model;

import util.NotificationController;

public class OrderDetails {

    private String OrderID;
    private String CusId;
    private String ItemCode;
    private String ItemName;
    private int OrderQty;
    private double unitPrice;
    private double Discount;
    private double Total;

    public OrderDetails() {
    }

    public OrderDetails(String orderID, String cusId, String itemCode, String itemName, int orderQty, double unitPrice, double discount, double total) {
        OrderID = orderID;
        CusId = cusId;
        ItemCode = itemCode;
        ItemName = itemName;
        OrderQty = orderQty;
        this.unitPrice = unitPrice;
        Discount = discount;
        Total = total;
    }

    public OrderDetails(String itemCode, String itemName, int orderQty, double unitPrice) {
        ItemCode = itemCode;
        ItemName = itemName;
        OrderQty = orderQty;
        this.unitPrice = unitPrice;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public String getCusId() {
        return CusId;
    }

    public void setCusId(String cusId) {
        CusId = cusId;
    }

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public int getOrderQty() {
        return OrderQty;
    }

    public void setOrderQty(int orderQty) {
        OrderQty = orderQty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getDiscount() {
        return Discount;
    }

    public void setDiscount(double discount) {
        Discount = discount;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(double total) {
        Total = total;
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "OrderID='" + OrderID + '\'' +
                ", CusId='" + CusId + '\'' +
                ", ItemCode='" + ItemCode + '\'' +
                ", ItemName='" + ItemName + '\'' +
                ", OrderQty=" + OrderQty +
                ", unitPrice=" + unitPrice +
                ", Discount=" + Discount +
                ", Total=" + Total +
                '}';
    }
}
