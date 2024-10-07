package Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderDetails {
    private String orderId;
    private String ItemCode;
    private int qty;
    private double discount;
}
