package Controller.PlaceOrder;

import Model.OrderDetails;
import Util.CrudUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsController {
    public static boolean addOrderDetails(ArrayList<OrderDetails> orderDetails) throws SQLException {
        for (OrderDetails item : orderDetails){
            if (!addOrderDetails(item)){
                return false;
            }
        }
        return true;
    }
    public static boolean addOrderDetails(OrderDetails orderDetails) throws SQLException {
        return CrudUtil.execute(
                "Insert into orderdetail values (?,?,?,?)",
                orderDetails.getOrderId(),
                orderDetails.getItemCode(),
                orderDetails.getQty(),
                orderDetails.getDiscount()
        );
    }
}
