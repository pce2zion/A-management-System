package com.example.employee.system.employeesystem.repository;

import com.example.employee.system.employeesystem.dto.EmployeeDto;
import com.example.employee.system.employeesystem.entity.Employee;
import com.example.employee.system.employeesystem.models.requestModels.EmployeeRequestModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {


    Optional<Employee> findByEmail(String email);

    Employee findEmployeeById(String employeeId);
    


}
