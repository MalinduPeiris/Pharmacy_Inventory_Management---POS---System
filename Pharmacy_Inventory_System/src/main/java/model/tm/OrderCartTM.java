package model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderCartTM {

    private int medicineId;
    private String orderName;
    private String dosage;
    private double unitPrice;
    private int orderQty;
    private double orderTotal;

}
