package Controller.CustomerController;

import Model.Customer;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
        if (!txtCustId.getText().isEmpty() && cmbTitle.getValue() != null && !txtCustName.getText().isEmpty() && dPDOB.getValue() != null && !txtSalary.getText().isEmpty() && !txtAddress.getText().isEmpty() && !txtCity.getText().isEmpty() && !txtProvince.getText().isEmpty() && !txtPosCode.getText().isEmpty()) {
            try {
                boolean isAdded = CustomerController.addCustomer(new Customer(
                        txtCustId.getText().toUpperCase(),
                        cmbTitle.getValue(),
                        txtCustName.getText(),
                        dPDOB.getValue(),
                        Double.parseDouble(txtSalary.getText()),
                        txtAddress.getText(),
                        txtCity.getText(),
                        txtProvince.getText(),
                        txtPosCode.getText()
                ));
                if (isAdded) {
                    JOptionPane.showMessageDialog(null, "Customer Added SuccessFully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(null, "Customer Added Unsuccessfully", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error Added Customer due to SQL Error");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please fill all fields");
        }
    }

    @FXML
    void btnDelete(ActionEvent event) {
        if (!txtCustId.getText().isEmpty()) {
            try {
                if (CustomerController.deleteCustomer(txtCustId.getText())) {
                    new Alert(Alert.AlertType.INFORMATION, txtCustId.getText() + " Customer Deleted").show();
                    clearFields();
                    loadTable();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Customer Not Found").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Customer Not Found or MYSQL Sever Error").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Customer Not Selected").show();
        }
    }

    @FXML
    void btnSearch(ActionEvent event) {
        if (!txtCustId.getText().isEmpty()) {
            try {
                Customer customer = CustomerController.getCustomer(txtCustId.getText());
                if (customer != null) {
                    setTxtFields(customer);
                } else {
                    new Alert(Alert.AlertType.ERROR, "Customer not found").show();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Customer not Selected").show();
        }
    }

    @FXML
    void btnUpdate(ActionEvent event) {
        if (!txtCustId.getText().isEmpty() && cmbTitle.getValue() != null && !txtCustName.getText().isEmpty() && dPDOB.getValue() != null && !txtSalary.getText().isEmpty() && !txtAddress.getText().isEmpty() && !txtCity.getText().isEmpty() && !txtProvince.getText().isEmpty() && !txtPosCode.getText().isEmpty()) {
            try {
                boolean isUpdated = CustomerController.updateCustomer(new Customer(
                        txtCustId.getText().toUpperCase(),
                        cmbTitle.getValue(),
                        txtCustName.getText(),
                        dPDOB.getValue(),
                        Double.parseDouble(txtSalary.getText()),
                        txtAddress.getText(),
                        txtCity.getText(),
                        txtProvince.getText(),
                        txtPosCode.getText()
                ));
                if (isUpdated) {
                    JOptionPane.showMessageDialog(null, "Customer Updated SuccessFully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    clearFields();
                    loadTable();
                } else {
                    JOptionPane.showMessageDialog(null, "Customer Updated Unsuccessfully Customer Not Found", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please fill all fields");
        }
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
        setTitle();
        tableSelectionChange();
    }

    private void setTitle() {
        ObservableList<String> title = FXCollections.observableArrayList();
        title.add("Mr");
        title.add("Mrs");
        title.add("Miss");
        title.add("Ms");
        cmbTitle.setItems(title);
    }

    private void tableSelectionChange() {
        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observableValue, number, selectedTableValue) -> {
            if (selectedTableValue != null) {
                setTxtFields(selectedTableValue);
            }
        });
    }

    private void clearFields() {
        txtCustId.setText("");
        cmbTitle.setValue(null);
        txtCustName.setText("");
        dPDOB.setValue(null);
        txtSalary.setText("");
        txtAddress.setText("");
        txtCity.setText("");
        txtProvince.setText("");
        txtPosCode.setText("");
    }

    private void setTxtFields(Customer customer) {
        txtCustId.setText(customer.getId());
        cmbTitle.setValue(customer.getTitle());
        txtCustName.setText(customer.getName());
        dPDOB.setValue(customer.getDob());
        txtSalary.setText(customer.getSalary() + "");
        txtAddress.setText(customer.getAddress());
        txtCity.setText(customer.getCity());
        txtProvince.setText(customer.getProvince());
        txtPosCode.setText(customer.getPostalCode());
    }
}
