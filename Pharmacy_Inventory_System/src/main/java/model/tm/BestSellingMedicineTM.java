package model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class BestSellingMedicineTM {
    private String id;
    private String name;
    private String category;
    private String dosage;
    private int qtySold;
    private double unitPrice;
    private double total;
}
