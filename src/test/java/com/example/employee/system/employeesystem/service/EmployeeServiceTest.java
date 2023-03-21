package com.example.employee.system.employeesystem.service;

import com.example.employee.system.employeesystem.dto.AddressDto;
import com.example.employee.system.employeesystem.dto.EmployeeDto;
import com.example.employee.system.employeesystem.entity.Employee;
import com.example.employee.system.employeesystem.enums.Role;
import com.example.employee.system.employeesystem.exceptions.MissingRequiredFieldException;
import com.example.employee.system.employeesystem.mailSender.EmailSenderService;
import com.example.employee.system.employeesystem.repository.EmployeeRepository;
import com.example.employee.system.employeesystem.utils.UserIdUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeeServiceTest {

    Employee employee;
    String encryptedPassword = "GSTH&&SK";
    String emailVerificationToken = "wje7hshdusn";

    @MockBean
    EmployeeRepository employeeRepository;

    @MockBean
    UserIdUtils userIdUtils;

    @MockBean
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @MockBean
    EmailSenderService emailSenderService;

    List<AddressDto> addressDtos;

    @Autowired
    EmployeeService employeeService;


    @BeforeEach
    void setUp() {
        employee = Employee.builder()
                .employeeId("1jsysbhshdn")
                .id(1)
                .email("peaceobute@gmail.com")
                .firstName("peace")
                .lastName("obute")
                .emailVerificationStatus(false)
                .emailVerificationToken(emailVerificationToken)
                .encryptedPassword(encryptedPassword)
                .build();
    }

    @Test
    void createEmployee(){
        Mockito.when(employeeRepository.findByEmail("peaceobute@gmail.com")).thenReturn(null);
        Mockito.when(userIdUtils.generatedUserId(30)).thenReturn("1jsysbhshdn");
        Mockito.when(userIdUtils.generatedAddressId(30)).thenReturn("hh738uduuedb");
        Mockito.when(bCryptPasswordEncoder.encode("783u4undidm,s")).thenReturn(encryptedPassword);
        Mockito.when(employeeRepository.save(new Employee())).thenReturn(employee);
        //Mockito.when(emailSenderService.sendEmail(employee)).thenReturn("Message sent successfullu");

        AddressDto addresses = new AddressDto();
        addresses.setAddressId ("kdkjsiqwj");
        addresses.setAddress("no2");
        addresses.setId(1L);


        AddressDto addresses2 = new AddressDto();
        addresses.setAddressId ("udjkndiuh");
        addresses.setAddress("no1");
        addresses.setId(2L);

        addressDtos = new ArrayList<>();
        addressDtos.add(addresses);
        addressDtos.add(addresses2);

        EmployeeDto createdEmployee = new EmployeeDto();
        createdEmployee.setEmployeeId("1jsysbhshdn");
        createdEmployee.setAddressDtos(addressDtos);
        createdEmployee.setId(1L);
        createdEmployee.setEmail("peaceobute@gmail.com");
        createdEmployee.setFirstName("peace");
        createdEmployee.setLastName("obute");
        createdEmployee.setEmailVerificationStatus(false);
        createdEmployee.setEmailVerificationToken(emailVerificationToken);
        createdEmployee.setEncryptedPassword(encryptedPassword);

       EmployeeDto found = employeeService.createEmployee(createdEmployee);

       assertNotNull(found);
       assertEquals(employee.getFirstName(), found.getFirstName());

    }

    @Test
    void findEmployeeById(){
        Mockito.when(employeeRepository.findEmployeeById("1jsysbhshdn")).thenReturn(employee);
        String employeeId = "ty3d6huumdi8d";
        EmployeeDto found = employeeService.findEmployeeById(employeeId);
        assertEquals(employee.getFirstName(), found.getFirstName());

    }

    @Test
    void createEmployee_MissingRequiredFieldException(){
        Mockito.when(employeeRepository.findByEmail("peaceobute@gmail.com")).thenReturn(null);

        assertThrows(MissingRequiredFieldException.class, ()->{

            EmployeeDto employeeDto = new EmployeeDto();
            employeeDto.setFirstName("Peace");
            employeeDto.setLastName("Obute");
            employeeDto.setPassword("22344d");
            employeeService.createEmployee(employeeDto);
        });

    }
}