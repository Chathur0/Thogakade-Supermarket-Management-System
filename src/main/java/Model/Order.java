package Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
@Getter
@Setter
@AllArgsConstructor
public class Order {
    private String orderId;
    private LocalDate date;
    private String customerId;
    private ArrayList<OrderDetails> orderDetails;
}
