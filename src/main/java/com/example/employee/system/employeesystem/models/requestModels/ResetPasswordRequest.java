package com.example.employee.system.employeesystem.models.requestModels;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResetPasswordRequest {
    @Email
    private String email;
    @NotBlank(message = "New password field can't be empty")
    private String newPassword;
    @NotBlank(message = "Confirm password field can't be empty")
    private String confirmPassword;
}