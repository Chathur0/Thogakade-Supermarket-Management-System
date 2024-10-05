package Controller.CustomerController;

import Model.Customer;
import Util.CrudUtil;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerController {

    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        ObservableList<Customer> allCustomer = FXCollections.observableArrayList();
        ResultSet resultSet = CrudUtil.execute("Select * from customer");
        while (resultSet.next()) {
            allCustomer.add(
                    new Customer(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getDate(4).toLocalDate(),
                            resultSet.getDouble(5),
                            resultSet.getString(6),
                            resultSet.getString(7),
                            resultSet.getString(8),
                            resultSet.getString(9)
                    )
            );
        }
        return allCustomer;
    }

    public static boolean addCustomer(Customer customer) throws SQLException {
        return CrudUtil.execute(
                "Insert into customer values(?,?,?,?,?,?,?,?,?)",
                customer.getId(),
                customer.getTitle(),
                customer.getName(),
                customer.getDob(),
                customer.getSalary(),
                customer.getAddress(),
                customer.getCity(),
                customer.getProvince(),
                customer.getPostalCode()
        );
    }

    public static boolean deleteCustomer(String customerID) throws SQLException {
        return CrudUtil.execute("Delete from customer where CustID=?", customerID);
    }

    public static Customer getCustomer(String customerID) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("Select * from customer where CustID=?", customerID);
        return resultSet.next() ? new Customer(
                resultSet.getString(1),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getDate(4).toLocalDate(),
                resultSet.getDouble(5),
                resultSet.getString(6),
                resultSet.getString(7),
                resultSet.getString(8),
                resultSet.getString(9)
        ) : null;
    }

    public static boolean updateCustomer(Customer customer) throws SQLException {
        return CrudUtil.execute(
                "Update customer set CustTitle=?, CustName=?, DOB=?, salary=?, CustAddress=?, City=?, Province=?, PostalCode=? where CustID=?",
                customer.getTitle(),
                customer.getName(),
                customer.getDob(),
                customer.getSalary(),
                customer.getAddress(),
                customer.getCity(),
                customer.getProvince(),
                customer.getPostalCode(),
                customer.getId()
        );
    }
}
