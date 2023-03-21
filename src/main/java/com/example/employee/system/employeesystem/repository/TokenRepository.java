package com.example.employee.system.employeesystem.repository;

import com.example.employee.system.employeesystem.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Token findByToken(String token);
}
