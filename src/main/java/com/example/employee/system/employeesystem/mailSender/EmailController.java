package com.example.employee.system.employeesystem.mailSender;

import com.example.employee.system.employeesystem.entity.Employee;
import com.example.employee.system.employeesystem.models.responseModels.OperationStatusRest;
import com.example.employee.system.employeesystem.models.responseModels.RequestOperationName;
import com.example.employee.system.employeesystem.models.responseModels.RequestOperationStatusResult;
import lombok.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Data
@Builder
public class EmailController {


   private final EmailSenderService emailSenderService;

    @PostMapping("/sendEmail")
    public OperationStatusRest sendEmail(@RequestBody Employee employee){

        OperationStatusRest returnValue = new OperationStatusRest();

        this.emailSenderService.sendEmail(employee);
        returnValue.setOperationResult(RequestOperationStatusResult.SUCCESS.name());
        returnValue.setOperationName(RequestOperationName.VERIFY_EMAIL.name());

        return returnValue;
    }

    @PostMapping("/send-passwordreset")
    public void sendPasswordResetEmail(@RequestBody Employee employee){
        emailSenderService.sendPasswordReset(employee);
    }
}
