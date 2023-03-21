package com.example.employee.system.employeesystem.models.responseModels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OperationStatusRest {
    private String operationResult;
    private String operationName;
}
