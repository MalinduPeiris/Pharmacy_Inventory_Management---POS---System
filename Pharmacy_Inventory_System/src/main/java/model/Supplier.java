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
public class Supplier {

    private int id;
    private String company;
    private String name;
    private String phone;
    private String email;
    private String address;
    private LocalDate registerDate;

}