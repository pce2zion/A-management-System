package com.example.employee.system.employeesystem.service;

import com.example.employee.system.employeesystem.dto.AddressDto;
import com.example.employee.system.employeesystem.dto.EmployeeDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AddressService {


    List<AddressDto> getAllAddresses(String employeeId);


    AddressDto getAddressById(String addressId, String employeeId);


    void deleteEmployeeAddress(String employeeId, String addressId);
}
