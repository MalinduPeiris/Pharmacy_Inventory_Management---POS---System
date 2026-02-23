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
public class ExpiryMedicineTM {
    private String id;
    private String name;
    private String dosage;
    private LocalDate expiry;
    private String leftDays;
}
