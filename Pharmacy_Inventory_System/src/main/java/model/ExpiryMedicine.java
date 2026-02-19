package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ExpiryMedicine {
    private String id;
    private String name;
    private String dosage;
    private LocalDate expiry;
    private String leftDays;
}
