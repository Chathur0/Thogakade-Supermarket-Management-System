package Controller.ItemController;

import Model.Item;
import Model.OrderDetails;
import Util.CrudUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemController {
    public static ObservableList<Item> getAllItems() throws SQLException {
        ObservableList<Item> items = FXCollections.observableArrayList();
        ResultSet resultSet = CrudUtil.execute("Select * from item");
        while (resultSet.next()) {
            items.add(new Item(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getDouble(4),
                            resultSet.getInt(5)
                    )
            );
        }
        return items;
    }

    public static boolean addItem(Item item) throws SQLException {
        return CrudUtil.execute(
                "Insert into item values(?,?,?,?,?)",
                item.getItemCode(),
                item.getDescription(),
                item.getPackSize(),
                item.getUnitePrice(),
                item.getQtyOnHand()
        );
    }

    public static Item getItem(String id) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("Select * from item where ItemCode=?", id);
        return resultSet.next() ? new Item(
                resultSet.getString(1),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getDouble(4),
                resultSet.getInt(5)
        ) : null;
    }

    public static boolean deleteItem(String id) throws SQLException {
        return CrudUtil.execute("Delete from item where ItemCode = ?", id);
    }

    public static boolean updateItem(Item item) throws SQLException {
        return CrudUtil.execute(
                "Update item set Description=?, PackSize=?, UnitPrice=?, QtyOnHand=? where ItemCode=?",
                item.getDescription(),
                item.getPackSize(),
                item.getUnitePrice(),
                item.getQtyOnHand(),
                item.getItemCode()
        );
    }

    public static boolean updateStock(ArrayList<OrderDetails> orderDetails) throws SQLException {
        for (OrderDetails order : orderDetails) {
            if (!updateStock(order)) {
                return false;
            }
        }
        return true;
    }

    public static boolean updateStock(OrderDetails orderDetails) throws SQLException {
        return CrudUtil.execute(
                "Update item set QtyOnHand=QtyOnHand-? WHERE ItemCode=?",
                orderDetails.getQty(),
                orderDetails.getItemCode()
        );
    }
}
