package model;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Sales {

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
