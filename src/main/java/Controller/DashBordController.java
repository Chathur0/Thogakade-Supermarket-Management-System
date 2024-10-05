package Controller;

import com.sun.javafx.stage.EmbeddedWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DashBordController {

    @FXML
    void onActionPlaceOrderForm(ActionEvent event) {
        Stage placeOrderForm = new Stage();
        try {
            placeOrderForm.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Place_Order_Form.fxml"))));
            placeOrderForm.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onActionCustomerForm(ActionEvent event) {
        Stage customerForm = new Stage();
        try {
            customerForm.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Customer_form.fxml"))));
            customerForm.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onActionItemForm(ActionEvent event) {
        Stage itemForm = new Stage();
        try {
            itemForm.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Item_Form.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        itemForm.show();
    }

}
