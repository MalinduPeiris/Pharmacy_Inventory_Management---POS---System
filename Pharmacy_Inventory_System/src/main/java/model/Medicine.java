package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Medicine{
    private int id;
    private String name;
    private String brand;
    private String category;
    private String dosage;
    private int supplierId;
    private double purchasePrice;
    private double sellingPrice;
    private int quantity;
    private LocalDate date;
    private String status;


}