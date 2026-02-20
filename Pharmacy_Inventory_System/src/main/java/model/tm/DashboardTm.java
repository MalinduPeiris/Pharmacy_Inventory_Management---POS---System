package model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class DashboardTm {
    private String id;
    private String name;
    private String category;
    private String dosage;
    private int qtySold;
    private double unitPrice;
    private double total;
    private LocalDate expiry;
    private String leftDays;
}
