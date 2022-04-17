package model;

public class Order {
    private String OId;
    private String customerId;
    private String ODate;
    private String OTime;



    public Order() {
    }

    public Order(String OId, String customerId, String ODate, String OTime) {
        this.OId = OId;
        this.customerId = customerId;
        this.ODate = ODate;
        this.OTime = OTime;
    }

    public String getOId() {
        return OId;
    }

    public void setOId(String OId) {
        this.OId = OId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getODate() {
        return ODate;
    }

    public void setODate(String ODate) {
        this.ODate = ODate;
    }

    public String getOTime() {
        return OTime;
    }

    public void setOTime(String OTime) {
        this.OTime = OTime;
    }

    @Override
    public String toString() {
        return "Order{" +
                "OId='" + OId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", ODate='" + ODate + '\'' +
                ", OTime='" + OTime + '\'' +
                '}';
    }
}
