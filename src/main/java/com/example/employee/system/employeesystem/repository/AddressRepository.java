package com.example.employee.system.employeesystem.repository;

import com.example.employee.system.employeesystem.entity.Address;
import com.example.employee.system.employeesystem.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    Address findAddressById(String addressId);

    List<Address> findByEmployee(Employee employee);
}
