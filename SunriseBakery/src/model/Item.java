package model;

public class Item {
    private String ItemCode;
    private String ItemName;
    private String ItemDescription;
    private String ItemPackSize;
    private double ItemUnitPrice;
    private int ItemQtyOnHand;
    private String ItemAddDate;
    private String ItemAddTime;

    public Item() {
    }

    public Item(String itemCode, String itemName, String itemDescription, String itemPackSize, double itemUnitPrice, int itemQtyOnHand, String itemAddDate, String itemAddTime) {
        ItemCode = itemCode;
        ItemName = itemName;
        ItemDescription = itemDescription;
        ItemPackSize = itemPackSize;
        ItemUnitPrice = itemUnitPrice;
        ItemQtyOnHand = itemQtyOnHand;
        ItemAddDate = itemAddDate;
        ItemAddTime = itemAddTime;
    }

    public Item(String itemCode, String itemName) {
        ItemCode = itemCode;
        ItemName = itemName;
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

    public String getItemDescription() {
        return ItemDescription;
    }

    public void setItemDescription(String itemDescription) {
        ItemDescription = itemDescription;
    }

    public String getItemPackSize() {
        return ItemPackSize;
    }

    public void setItemPackSize(String itemPackSize) {
        ItemPackSize = itemPackSize;
    }

    public double getItemUnitPrice() {
        return ItemUnitPrice;
    }

    public void setItemUnitPrice(double itemUnitPrice) {
        ItemUnitPrice = itemUnitPrice;
    }

    public int getItemQtyOnHand() {
        return ItemQtyOnHand;
    }

    public void setItemQtyOnHand(int itemQtyOnHand) {
        ItemQtyOnHand = itemQtyOnHand;
    }

    public String getItemAddDate() {
        return ItemAddDate;
    }

    public void setItemAddDate(String itemAddDate) {
        ItemAddDate = itemAddDate;
    }

    public String getItemAddTime() {
        return ItemAddTime;
    }

    public void setItemAddTime(String itemAddTime) {
        ItemAddTime = itemAddTime;
    }

    @Override
    public String toString() {
        return "Item{" +
                "ItemCode='" + ItemCode + '\'' +
                ", ItemName='" + ItemName + '\'' +
                ", ItemDescription='" + ItemDescription + '\'' +
                ", ItemPackSize='" + ItemPackSize + '\'' +
                ", ItemUnitPrice=" + ItemUnitPrice +
                ", ItemQtyOnHand=" + ItemQtyOnHand +
                ", ItemAddDate='" + ItemAddDate + '\'' +
                ", ItemAddTime='" + ItemAddTime + '\'' +
                '}';
    }
}
