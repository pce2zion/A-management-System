package com.example.employee.system.employeesystem.service;

import com.example.employee.system.employeesystem.dto.EmployeeDto;
import com.example.employee.system.employeesystem.models.requestModels.AuthenticationRequest;
import com.example.employee.system.employeesystem.models.requestModels.EmployeeRequestModel;
import com.example.employee.system.employeesystem.models.requestModels.ForgotPasswordRequest;
import com.example.employee.system.employeesystem.models.requestModels.ResetPasswordRequest;
import com.example.employee.system.employeesystem.models.responseModels.AuthenticationResponse;
import com.example.employee.system.employeesystem.models.responseModels.TokenVerificationResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeService {
    EmployeeDto createEmployee(EmployeeDto employeeDto);

    EmployeeDto findEmployeeById(String employeeId);


    List<EmployeeDto> findAllEmployees(int page, int limit);


    EmployeeDto updateEmployee(EmployeeDto employeeDto, String employeeId);

    void deleteEmployee(String employeeId);


    TokenVerificationResponse verifyToken(String token);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    String forgotPassword(ForgotPasswordRequest request);

    String resetPassword(ResetPasswordRequest request);
}
