package controller;

import model.Order;
import model.OrderDetails;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderCrudController {
    public boolean saveOrder(Order order) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO `Order` VALUES(?,?,?,?)",
                order.getOId(), order.getCustomerId(), order.getODate(), order.getOTime());
    }

    public boolean saveOrderDetails(ArrayList<OrderDetails> details) throws SQLException, ClassNotFoundException {
        for (OrderDetails det : details
        ) {
            boolean isDetailsSaved = CrudUtil.execute("INSERT INTO `Order Details` VALUES(?,?,?,?,?,?,?,?)",
                    det.getOrderID(), det.getCusId(), det.getItemCode(), det.getItemName(), det.getOrderQty(), det.getUnitPrice(), det.getDiscount(), det.getTotal());
            if (isDetailsSaved) {
                if (!updateQty(det.getItemCode(), det.getOrderQty())) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    private boolean updateQty(String itemCode, int qty) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE ITEM SET ItemQtyOnHand=ItemQtyOnHand-? WHERE ItemCode = ?", qty, itemCode);
    }

    public String getOrderId() throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.execute("SELECT OrderID FROM  `Order` ORDER BY OrderID DESC LIMIT 1");
        if (set.next()) {
            int tempId = Integer.parseInt(set.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId <= 9) {
                return "O-00" + tempId;
            } else if (tempId <= 99) {
                return "O-0" + tempId;
            } else {
                return "O-" + tempId;
            }
        } else {
            return "O-001";
        }
    }

    //----------------Item Count-----------------//
    public int getItem() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT COUNT(ItemCode) FROM Item");
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    //----------------Customer Count-----------------//
    public int getCustomer() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT COUNT(CustomerID) FROM Customer");
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    //----------------Order Count-----------------//
    public int getSumSales() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT count(OrderID) FROM `Order`");
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    //----------------Item Sold Count---------------//
    public int getSumSoldItems() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT sum(OrderQty) FROM `Order Details`");
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }
}
