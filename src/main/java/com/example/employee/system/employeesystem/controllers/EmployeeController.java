package com.example.employee.system.employeesystem.controllers;

import com.example.employee.system.employeesystem.dto.AddressDto;
import com.example.employee.system.employeesystem.dto.EmployeeDto;
import com.example.employee.system.employeesystem.exceptions.MissingRequiredFieldException;
import com.example.employee.system.employeesystem.exceptions.UserNameNotFoundException;
import com.example.employee.system.employeesystem.models.requestModels.EmployeeRequestModel;
import com.example.employee.system.employeesystem.models.responseModels.*;
import com.example.employee.system.employeesystem.service.AddressService;
import com.example.employee.system.employeesystem.service.EmployeeService;
import com.example.employee.system.employeesystem.utils.UserIdUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("employees")


public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    AddressService addressService;

    @Autowired
    UserIdUtils userIdUtils;

    @PostMapping("/register")
    public EmployeeRest createEmployee (@RequestBody EmployeeRequestModel employeeRequestModel){
        EmployeeRest returnValue = new EmployeeRest();
        if(employeeRequestModel == null) throw new RuntimeException("Fill in your details");

        if(employeeRequestModel.getFirstName().isEmpty()) throw new MissingRequiredFieldException("Missing required field");

        EmployeeDto employeeDto = new EmployeeDto();
        BeanUtils.copyProperties(employeeRequestModel, employeeDto);


       EmployeeDto createdEmployee = employeeService.createEmployee(employeeDto);
       BeanUtils.copyProperties(createdEmployee, returnValue);

        return returnValue;
    }

    @GetMapping("/{id}")
    public EmployeeRest getEmployeeById(@PathVariable String employeeId){
         EmployeeRest returnValue = new EmployeeRest();
         EmployeeDto employeeDto = employeeService.findEmployeeById(employeeId);
         BeanUtils.copyProperties(employeeDto, returnValue);
        return returnValue;
    }

    @GetMapping()
    public List<EmployeeRest> getAllEmployees(@RequestParam(value = "page", defaultValue = "0" )int page,
                                              @RequestParam(value = "limit", defaultValue = "25")int limit){
        List<EmployeeRest> returnValue = new ArrayList<>();
       List<EmployeeDto> employeeDto = employeeService.findAllEmployees(page, limit);

       for(EmployeeDto singleDto : employeeDto){
           EmployeeRest singleEmployee = new EmployeeRest();
           BeanUtils.copyProperties(singleDto, singleEmployee);
           returnValue.add(singleEmployee);
       }
        return returnValue;
    }

    @PutMapping("/{id}")
    public EmployeeRest updateEmployee(@PathVariable String employeeId, @RequestBody EmployeeRequestModel employeeRequestModel){
        EmployeeRest returnValue = new EmployeeRest();

       EmployeeDto foundEmployee =employeeService.findEmployeeById(employeeId);

       if(foundEmployee == null) throw new UserNameNotFoundException("user not found");

        EmployeeDto employeeDto = new EmployeeDto();
        BeanUtils.copyProperties(employeeRequestModel, employeeDto);

        EmployeeDto updatedEmployee = employeeService.updateEmployee(employeeDto,employeeId);
        BeanUtils.copyProperties(updatedEmployee, returnValue);

        return returnValue;
    }

    @DeleteMapping("/{id}")
    public OperationStatusRest deleteEmployee(@PathVariable String employeeId){
        OperationStatusRest returnValue = new OperationStatusRest();
        employeeService.deleteEmployee(employeeId);

        returnValue.setOperationResult(RequestOperationStatusResult.SUCCESS.name());
        returnValue.setOperationName(RequestOperationName.DELETE.name());


        return returnValue;
    }

    @GetMapping("/{id}/addresses")
    public List<AddressRest> getEmployeeAddresses(@PathVariable String employeeId){
        List<AddressRest> returnValue = new ArrayList<>();

        EmployeeDto returnedEmployee = employeeService.findEmployeeById(employeeId);
        if(returnedEmployee == null) throw new UserNameNotFoundException("User not found");

        List<AddressDto> addresses = addressService.getAllAddresses(employeeId);

        for(AddressDto singleAddress: addresses){
            AddressRest addressRest = new AddressRest();
            BeanUtils.copyProperties(singleAddress, addressRest);
            returnValue.add(addressRest);
        }
        return  returnValue;
    }


    @GetMapping("/{id}/addresses/{addressId}")
    public AddressRest getAddressById(@PathVariable String employeeId, @PathVariable String addressId){
        AddressRest returnValue = new AddressRest();
      EmployeeDto returnedEmployee  = employeeService.findEmployeeById(employeeId);

      if(returnedEmployee == null) throw new UserNameNotFoundException("employee not found");

      AddressDto addressDto = addressService.getAddressById(addressId, employeeId);
      BeanUtils.copyProperties(addressDto, returnValue);

        return returnValue;
    }

    @DeleteMapping("/{id}/addresses/{addressId}")
    public OperationStatusRest deleteMapping(@PathVariable String employeeId, @PathVariable String addressId){
        OperationStatusRest returnValue = new OperationStatusRest();

      EmployeeDto returnedEmployee  = employeeService.findEmployeeById(employeeId);
      if(returnedEmployee == null) throw  new UserNameNotFoundException("employee not found");

      addressService.deleteEmployeeAddress(employeeId, addressId);

      returnValue.setOperationName(RequestOperationName.DELETE.name());
      returnValue.setOperationResult(RequestOperationStatusResult.SUCCESS.name());

        return returnValue;
    }
}
