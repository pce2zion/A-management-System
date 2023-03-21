package com.example.employee.system.employeesystem.entity;

import com.example.employee.system.employeesystem.enums.TokenStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "token")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="token_id", length = 100)
    private String tokenId;

    @Column(name = "token", length = 100)
    private String token;

    @Enumerated(value = EnumType.STRING)
    private TokenStatus tokenStatus;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
