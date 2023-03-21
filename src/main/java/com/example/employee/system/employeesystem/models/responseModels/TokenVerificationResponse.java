package com.example.employee.system.employeesystem.models.responseModels;

import com.example.employee.system.employeesystem.enums.TokenStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenVerificationResponse {
    private String token;
    private TokenStatus tokenStatus;
    private String email;
}
