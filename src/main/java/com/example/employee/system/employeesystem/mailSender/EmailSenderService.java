package com.example.employee.system.employeesystem.mailSender;

import com.example.employee.system.employeesystem.entity.Employee;
import com.example.employee.system.employeesystem.models.responseModels.TokenVerificationResponse;
import org.springframework.http.ResponseEntity;

public interface EmailSenderService {

    ResponseEntity<String> sendEmail(Employee employee);

    void sendPasswordReset(Employee employee);
}
