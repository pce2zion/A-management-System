package com.example.employee.system.employeesystem.service;

import com.example.employee.system.employeesystem.dto.AddressDto;
import com.example.employee.system.employeesystem.dto.EmployeeDto;
import com.example.employee.system.employeesystem.entity.Address;
import com.example.employee.system.employeesystem.entity.Employee;
import com.example.employee.system.employeesystem.exceptions.UserNameNotFoundException;
import com.example.employee.system.employeesystem.repository.AddressRepository;
import com.example.employee.system.employeesystem.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    EmployeeRepository employeeRepository;
    @Override
    public List<AddressDto> getAllAddresses(String employeeId) {

        List<AddressDto> returnValue = new ArrayList<>();
       Employee employee = employeeRepository.findEmployeeById(employeeId);
       if(employee == null) throw new UserNameNotFoundException("User does not exist");

       List<Address> addressList = addressRepository.findByEmployee(employee);

       for(Address singleAddress : addressList){
           AddressDto addressDto= new AddressDto();
           BeanUtils.copyProperties(singleAddress, addressDto);

           returnValue.add(addressDto);
       }


        return returnValue;
    }

    @Override
    public AddressDto getAddressById(String addressId, String employeeId) {
        AddressDto returnValue = new AddressDto();

        Employee returnedEmployee = employeeRepository.findEmployeeById(employeeId);
        if(returnedEmployee == null) throw new UserNameNotFoundException("User does not exist");

      Address returnedAddress  = addressRepository.findAddressById(addressId);
      BeanUtils.copyProperties(returnedAddress, returnValue);

        return returnValue;
    }

    @Override
    public void deleteEmployeeAddress(String employeeId, String addressId) {
        Employee returnedEmployee = employeeRepository.findEmployeeById(employeeId);
        if(returnedEmployee == null) throw new UserNameNotFoundException("User does not exist");

       Address returnedAddress = addressRepository.findAddressById( addressId);

        addressRepository.delete(returnedAddress);
    }


}
