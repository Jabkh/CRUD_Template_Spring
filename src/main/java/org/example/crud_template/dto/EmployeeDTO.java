package org.example.crud_template.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeDTO {
    private Long id;
    private String name;
    private String identificationNumber;
    private String address;
    private String phone;
    private String email;
    private LocalDate birthDate;
    private LocalDate contractStart;
    private LocalDate contractEnd;
    private String occupation;
    private String password;
    private boolean admin;
    private double salary;
    private String observation;
}
