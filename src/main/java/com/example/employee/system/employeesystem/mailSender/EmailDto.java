package com.example.employee.system.employeesystem.mailSender;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class EmailDto {
    private String to;
    private String subject;
    private String message;
}
