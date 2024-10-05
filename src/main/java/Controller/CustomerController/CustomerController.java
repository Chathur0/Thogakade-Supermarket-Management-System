package Controller.CustomerController;

import Model.Customer;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerController {

    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        ObservableList<Customer> allCustomer = FXCollections.observableArrayList();
        ResultSet resultSet = DBConnection.getInstance().getConnection().prepareStatement("Select * from customer").executeQuery();
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
        PreparedStatement stmt = DBConnection.getInstance().getConnection().prepareStatement("Insert into customer values(?,?,?,?,?,?,?,?,?)");
        stmt.setObject(1, customer.getId());
        stmt.setObject(2, customer.getTitle());
        stmt.setObject(3, customer.getName());
        stmt.setObject(4, customer.getDob());
        stmt.setObject(5, customer.getSalary());
        stmt.setObject(6, customer.getAddress());
        stmt.setObject(7, customer.getCity());
        stmt.setObject(8, customer.getProvince());
        stmt.setObject(9, customer.getPostalCode());
        return stmt.executeUpdate() > 0;
    }

    public static boolean deleteCustomer(String customerID) throws SQLException {
        PreparedStatement stmt = DBConnection.getInstance().getConnection().prepareStatement("Delete from customer where CustID=?");
        stmt.setObject(1, customerID);
        return stmt.executeUpdate() > 0;
    }

    public static Customer getCustomer(String customerID) throws SQLException {
        PreparedStatement stmt = DBConnection.getInstance().getConnection().prepareStatement("Select * from customer where CustID=?");
        stmt.setObject(1, customerID);
        ResultSet resultSet = stmt.executeQuery();
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
        PreparedStatement stmt = DBConnection.getInstance().getConnection().prepareStatement("Update customer set CustTitle=?, CustName=?, DOB=?, salary=?, CustAddress=?, City=?, Province=?, PostalCode=? where CustID=?");
        stmt.setObject(9, customer.getId());
        stmt.setObject(1, customer.getTitle());
        stmt.setObject(2, customer.getName());
        stmt.setObject(3, customer.getDob());
        stmt.setObject(4, customer.getSalary());
        stmt.setObject(5, customer.getAddress());
        stmt.setObject(6, customer.getCity());
        stmt.setObject(7, customer.getProvince());
        stmt.setObject(8, customer.getPostalCode());
        return stmt.executeUpdate() > 0;
    }
}
