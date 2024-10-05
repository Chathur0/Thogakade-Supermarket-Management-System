package Controller.CustomerController;

import Model.Customer;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerFormController implements Initializable {

    public TableColumn<?, ?> colTitle;
    public TableColumn<?, ?> colAdders;
    public TableView<Customer> tblCustomer;
    public JFXButton btnReload;
    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXComboBox<String> cmbTitle;


    @FXML
    private TableColumn<?, ?> colCity;

    @FXML
    private TableColumn<?, ?> colDOB;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPosCode;

    @FXML
    private TableColumn<?, ?> colProvince;

    @FXML
    private TableColumn<?, ?> colSalary;


    @FXML
    private DatePicker dPDOB;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtCity;

    @FXML
    private JFXTextField txtCustId;

    @FXML
    private JFXTextField txtCustName;

    @FXML
    private JFXTextField txtPosCode;

    @FXML
    private JFXTextField txtProvince;

    @FXML
    private JFXTextField txtSalary;

    @FXML
    void btnAdd(ActionEvent event) {
        try {
            int isAdded = CustomerController.addCustomer(new Customer(txtCustId.getText().toUpperCase(), cmbTitle.getValue(), txtCustName.getText(), dPDOB.getValue(), Double.parseDouble(txtSalary.getText()), txtAddress.getText(), txtCity.getText(), txtProvince.getText(), txtPosCode.getText()));
            if (isAdded > 0) {
                JOptionPane.showMessageDialog(null, "Customer Added SuccessFully", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Customer Added Unsuccessfully", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error Added Customer due to SQL Error");
        }
    }

    @FXML
    void btnDelete(ActionEvent event) {

    }

    @FXML
    void btnSearch(ActionEvent event) {

    }

    @FXML
    void btnUpdate(ActionEvent event) {

    }

    public void btnOnActionReload(ActionEvent actionEvent) {
        loadTable();
    }

    @FXML
    void onActionAddress(ActionEvent event) {
        txtCity.requestFocus();
    }

    @FXML
    void onActionCity(ActionEvent event) {
        btnAdd.requestFocus();
    }

    @FXML
    void onActionCustId(ActionEvent event) {
        txtCustName.requestFocus();
    }

    @FXML
    void onActionCustName(ActionEvent event) {
        cmbTitle.requestFocus();
    }

    @FXML
    void onActionDOB(ActionEvent event) {
        txtAddress.requestFocus();
    }

    @FXML
    void onActionPosCode(ActionEvent event) {
        txtSalary.requestFocus();
    }

    @FXML
    void onActionProvince(ActionEvent event) {
        txtPosCode.requestFocus();
    }

    @FXML
    void onActionSalary(ActionEvent event) {
        dPDOB.requestFocus();
    }

    @FXML
    void onActionTitle(ActionEvent event) {
        txtProvince.requestFocus();
    }

    private void loadTable() {
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colAdders.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
        colPosCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        try {
            tblCustomer.setItems(CustomerController.getAllCustomers());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error Load Customers", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnReload.fire(); // this fire() will act like manual button click, so it will execute "btnOnActionReload" function
        ObservableList<String> title = FXCollections.observableArrayList();
        title.add("Mr");
        title.add("Mrs");
        title.add("Miss");
        title.add("Ms");
        cmbTitle.setItems(title);
    }
}
