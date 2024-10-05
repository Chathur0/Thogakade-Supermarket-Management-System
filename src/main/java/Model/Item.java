package Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@AllArgsConstructor
public class Item {
    private String itemCode;
    private String description;
    private String packSize;
    private Double unitePrice;
    private int qtyOnHand;
}
