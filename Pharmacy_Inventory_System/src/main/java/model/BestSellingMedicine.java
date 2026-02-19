package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class BestSellingMedicine {
    private String id;
    private String name;
    private String category;
    private String dosage;
    private int qtySold;
    private double unitPrice;
    private double total;
}
