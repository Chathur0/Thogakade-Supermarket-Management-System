package Controller.PlaceOrder;

import Controller.ItemController.ItemController;
import Model.Order;
import db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderController {
    public static boolean placeOrder(Order order) throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        try {
            conn.setAutoCommit(false);
            PreparedStatement stmt = conn.prepareStatement("Insert into orders values(?,?,?)");
            stmt.setObject(1, order.getOrderId());
            stmt.setObject(2, order.getDate());
            stmt.setObject(3, order.getCustomerId());
            boolean isAdded = stmt.executeUpdate() > 0;
            if (isAdded) {
                if (OrderDetailsController.addOrderDetails(order.getOrderDetails())) {
                    if (ItemController.updateStock(order.getOrderDetails())) {
                        conn.commit();
                        return true;
                    }
                }
            }
            conn.rollback();
            return false;
        } finally {
            conn.setAutoCommit(true);
        }
    }
}
