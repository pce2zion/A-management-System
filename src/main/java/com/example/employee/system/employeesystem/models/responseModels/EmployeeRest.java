package com.example.employee.system.employeesystem.models.responseModels;

import com.example.employee.system.employeesystem.models.requestModels.AddressRequestModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeRest {
    private String employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private List<AddressRequestModel> addresses;
}
