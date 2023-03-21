package com.example.employee.system.employeesystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDto implements Serializable {
    private Long id;
    private String addressId;
    private String address;
    private EmployeeDto employeeDto;
}
