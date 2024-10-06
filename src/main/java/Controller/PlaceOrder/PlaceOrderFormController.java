package Controller.PlaceOrder;

import Controller.CustomerController.CustomerController;
import Controller.ItemController.ItemController;
import Model.CartTM;
import Model.Customer;
import Model.Item;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

public class PlaceOrderFormController implements Initializable {

    @FXML
    private JFXComboBox<String> cmbCustomerId;

    @FXML
    private JFXComboBox<String> cmbItemCode;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblTime;

    @FXML
    private TableView<CartTM> tblCart;

    @FXML
    private JFXTextField txtCity;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtDiscount;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtOrderId;

    @FXML
    private JFXTextField txtQty;

    @FXML
    private JFXTextField txtSalary;

    @FXML
    private JFXTextField txtStock;

    @FXML
    private JFXTextField txtUnitPrice;
    private ObservableList<CartTM> cart = FXCollections.observableArrayList();

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        if (cmbItemCode.getValue() != null) {
            if (!txtQty.getText().isEmpty()) {
                int qty = 0;
                try {
                    qty = Integer.parseInt(txtQty.getText());
                } catch (NumberFormatException e) {
                    new Alert(Alert.AlertType.WARNING, "Invalid QTY Input").show();
                }
                double unitPrice = Double.parseDouble(txtUnitPrice.getText());
                if (qty > Integer.parseInt(txtStock.getText())) {
                    new Alert(Alert.AlertType.INFORMATION, "Sorry, the available stock is insufficient for the quantity you selected. Please reduce your order quantity.").show();
                } else {
                    if (txtDiscount.getText().isEmpty()) {
                        addToCart(new CartTM(
                                cmbItemCode.getValue(),
                                txtDescription.getText(),
                                qty,
                                unitPrice,
                                qty * unitPrice
                        ));
                    } else {
                        try {
                            addToCart(new CartTM(
                                    cmbItemCode.getValue(),
                                    txtDescription.getText(),
                                    qty,
                                    unitPrice,
                                    qty * unitPrice * (100 - Integer.parseInt(txtDiscount.getText())) / 100
                            ));
                        } catch (NumberFormatException e) {
                            new Alert(Alert.AlertType.WARNING, "Invalid Discount Input").show();
                        }
                    }
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "QTY Not Selected.").show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Item Not Selected.").show();
        }
    }

    private void addToCart(CartTM newItem) {
        if (!cart.contains(newItem)) {
            cart.add(newItem);
        } else {
            CartTM cartDuplicatedItem = cart.get(cart.indexOf(newItem));
            cartDuplicatedItem.setQty(cartDuplicatedItem.getQty() + newItem.getQty());
            cartDuplicatedItem.setTotal(cartDuplicatedItem.getTotal() + newItem.getTotal());
        }
        loadTable();
        calNetTotal();
    }

    private void calNetTotal() {
        double total = 0.0;
        for (CartTM c : cart) {
            total += c.getTotal();
        }
        lblNetTotal.setText(total + "");
    }

    private void loadTable() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        for(CartTM c : cart){
            System.out.println(c);
        }
        tblCart.setItems(cart);
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDateAndTime();
        setCustomerAndItemIds();
        customerSelection();
        itemSelection();
        setFieldNotEditable();
    }

    private void setFieldNotEditable() {
        txtName.setEditable(false);
        txtCity.setEditable(false);
        txtSalary.setEditable(false);
        txtDescription.setEditable(false);
        txtUnitPrice.setEditable(false);
        txtStock.setEditable(false);
    }

    private void clearFields() {
        cmbCustomerId.setValue("");
        txtName.setText("");
        txtCity.setText("");
        txtSalary.setText("");
        cmbItemCode.setValue("");
        txtDescription.setText("");
        txtUnitPrice.setText("");
        txtStock.setText("");
    }

    private void itemSelection() {
        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observableValue, s, newValue) -> {
            try {
                if (newValue != null) {
                    Item item = ItemController.getItem(newValue);
                    if (item != null) {
                        txtDescription.setText(item.getDescription());
                        txtUnitPrice.setText(item.getUnitePrice() + "");
                        txtStock.setText(item.getQtyOnHand() + "");
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void customerSelection() {
        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener((observableValue, s, newValue) -> {
            try {
                Customer customer = CustomerController.getCustomer(newValue);
                if (customer != null) {
                    txtName.setText(customer.getName());
                    txtCity.setText(customer.getCity());
                    txtSalary.setText(customer.getSalary() + "");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void setCustomerAndItemIds() {
        try {
            ObservableList<String> customerIds = FXCollections.observableArrayList();
            ObservableList<Customer> allCustomers = CustomerController.getAllCustomers();
            allCustomers.forEach(customer -> {
                customerIds.add(customer.getId());
            });
            cmbCustomerId.setItems(customerIds);
            ObservableList<String> itemIds = FXCollections.observableArrayList();
            ObservableList<Item> allItems = ItemController.getAllItems();
            allItems.forEach(item -> {
                itemIds.add(item.getItemCode());
            });
            cmbItemCode.setItems(itemIds);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadDateAndTime() {
//      Date date = new Date();
//      SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
//      lblDate.setText(f.format(date));
        lblDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime now = LocalTime.now();
            lblTime.setText(now.getHour() + " : " + now.getMinute() + " : " + now.getSecond());
        }), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void btnRemoveToCartOnAction(ActionEvent actionEvent) {
    }
}