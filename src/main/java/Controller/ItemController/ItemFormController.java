package Controller.ItemController;

import Model.Item;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ItemFormController implements Initializable {

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colItemName;

    @FXML
    private TableColumn<?, ?> colPackSize;

    @FXML
    private TableColumn<?, ?> colQtyOnHand;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableView<Item> tblItem;

    @FXML
    private JFXTextField txtItemCode;

    @FXML
    private JFXTextField txtItemName;

    @FXML
    private JFXTextField txtPackSize;

    @FXML
    private JFXTextField txtQtyOnHand;

    @FXML
    private JFXTextField txtUnitPrice;

    @FXML
    void btnAdd(ActionEvent event) {
        if (!txtItemCode.getText().isEmpty() && !txtItemName.getText().isEmpty() && !txtPackSize.getText().isEmpty() && !txtUnitPrice.getText().isEmpty() && !txtQtyOnHand.getText().isEmpty()) {
            try {
                boolean isAdded = ItemController.addItem(new Item(
                        txtItemCode.getText().toUpperCase(),
                        txtItemName.getText(),
                        txtPackSize.getText(),
                        Double.parseDouble(txtUnitPrice.getText()),
                        Integer.parseInt(txtQtyOnHand.getText())
                ));
                if (isAdded) {
                    new Alert(Alert.AlertType.INFORMATION, "Item Added Successfully").show();
                    clearAllFields();
                    loadTable();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Item Added Unsuccessful").show();
                }
            } catch (NumberFormatException e) {
                new Alert(Alert.AlertType.ERROR, "Invalid input! Please enter valid numeric values for Unit Price and Quantity.").show();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Place fill all fields").show();
        }
    }

    private void clearAllFields() {
        txtItemCode.setText("");
        txtItemName.setText("");
        txtPackSize.setText("");
        txtUnitPrice.setText("");
        txtQtyOnHand.setText("");
    }

    @FXML
    void btnDelete(ActionEvent event) {
        if (!txtItemCode.getText().isEmpty()) {
            try {
                boolean isDeleted = ItemController.deleteItem(txtItemCode.getText());
                if (isDeleted) {
                    new Alert(Alert.AlertType.ERROR, txtItemCode.getText() + " Item Deleted").show();
                    loadTable();
                    clearAllFields();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Item not found.").show();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Place Enter Item Code").show();
        }
    }

    @FXML
    void btnSearch(ActionEvent event) {
        if (!txtItemCode.getText().isEmpty()) {
            try {
                Item item = ItemController.getItem(txtItemCode.getText());
                if (item != null) {
                    setTxtFields(item);
                } else {
                    new Alert(Alert.AlertType.ERROR, "Item not found.").show();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Place Enter Item Code").show();
        }
    }

    @FXML
    void btnUpdate(ActionEvent event) {
        if (!txtItemCode.getText().isEmpty() && !txtItemName.getText().isEmpty() && !txtPackSize.getText().isEmpty() && !txtUnitPrice.getText().isEmpty() && !txtQtyOnHand.getText().isEmpty()) {
            try {
                boolean isUpdated = ItemController.updateItem(new Item(
                        txtItemCode.getText().toUpperCase(),
                        txtItemName.getText(),
                        txtPackSize.getText(),
                        Double.parseDouble(txtUnitPrice.getText()),
                        Integer.parseInt(txtQtyOnHand.getText())
                ));
                if (isUpdated) {
                    new Alert(Alert.AlertType.INFORMATION, "Item Updated Successfully").show();
                    clearAllFields();
                    loadTable();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Item Updated Unsuccessful Item Code Not Found.").show();
                }
            } catch (NumberFormatException e) {
                new Alert(Alert.AlertType.ERROR, "Invalid input! Please enter valid numeric values for Unit Price and Quantity.").show();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Place fill all fields").show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTable();
        tableSelectionChange();
    }

    private void tableSelectionChange() {
        tblItem.getSelectionModel().selectedItemProperty().addListener((observableValue, item, selectedTableValue) -> {
            if (selectedTableValue != null) {
                setTxtFields(selectedTableValue);
            }
        });
    }

    private void setTxtFields(Item item) {
        txtItemCode.setText(item.getItemCode());
        txtItemName.setText(item.getDescription());
        txtPackSize.setText(item.getPackSize());
        txtUnitPrice.setText(item.getUnitePrice() + "");
        txtQtyOnHand.setText(item.getQtyOnHand() + "");
    }

    private void loadTable() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitePrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        try {
            tblItem.setItems(ItemController.getAllItems());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnCustomerFormOnAction(ActionEvent actionEvent) {
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Customer_form.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
