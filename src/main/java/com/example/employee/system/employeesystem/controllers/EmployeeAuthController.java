package com.example.employee.system.employeesystem.controllers;

import com.example.employee.system.employeesystem.entity.Employee;
import com.example.employee.system.employeesystem.models.requestModels.AuthenticationRequest;
import com.example.employee.system.employeesystem.models.requestModels.ForgotPasswordRequest;
import com.example.employee.system.employeesystem.models.requestModels.ResetPasswordRequest;
import com.example.employee.system.employeesystem.models.responseModels.*;
import com.example.employee.system.employeesystem.security.JwtUtils;
import com.example.employee.system.employeesystem.security.DetailsService;
import com.example.employee.system.employeesystem.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class EmployeeAuthController {

    @Autowired
    EmployeeService employeeService;

    private final JwtUtils jwtUtils;

    private final DetailsService detailsService;

    private final AuthenticationManager authenticationManager;

// remember the register endpoint comes first and has already been coded, it is
    //in the employee contoller createEmployee method and it comes b4
    //the authenticate method

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.authenticate(request));
     //  return ResponseEntity.ok(employeeService.authenticate(request));
    }


    @GetMapping("/verify-token/{token}")
    public TokenVerificationResponse verifyToken(@PathVariable("token") String token) {
        return employeeService.verifyToken(token);
    }


    @PostMapping("/forgot-password")
    @ResponseStatus(HttpStatus.CREATED)
    public String forgotPassword(@RequestBody ForgotPasswordRequest request){
        return employeeService.forgotPassword(request);
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody ResetPasswordRequest request){
        return employeeService.resetPassword(request);
    }


}
