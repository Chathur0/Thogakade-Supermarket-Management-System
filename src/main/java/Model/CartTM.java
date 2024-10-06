package Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class CartTM {
    private String itemCode;
    private String description;
    private int qty;
    private double unitPrice;
    private double total;

    public boolean equals(Object obj) {
        return obj instanceof CartTM ? ((CartTM) obj).itemCode.equalsIgnoreCase(this.itemCode) : false;
    }
}
