package model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SalesTM {

    private int id;
    private LocalDate orderDate;
    private String customerName;
    private String phoneNumber;
    private String email;
    private String medicineItems;
    private int orderQty;
    private double subTotal;
    private double discount;
    private double netTotal;


}
