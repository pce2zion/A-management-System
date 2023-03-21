package com.example.employee.system.employeesystem.mailSender;

import com.example.employee.system.employeesystem.entity.Employee;
import com.example.employee.system.employeesystem.entity.Token;
import com.example.employee.system.employeesystem.enums.TokenStatus;
import com.example.employee.system.employeesystem.repository.EmployeeRepository;
import com.example.employee.system.employeesystem.repository.TokenRepository;
import com.example.employee.system.employeesystem.security.DetailsService;
import com.example.employee.system.employeesystem.security.JwtUtils;
import com.example.employee.system.employeesystem.utils.UserIdUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailSenderImpl implements EmailSenderService{

    private final DetailsService detailsService;

    @Autowired
    UserIdUtils userIdUtils;
    private  final JwtUtils jwtUtils;
    private final JavaMailSender mailSender;

    private final TokenRepository tokenRepository;

   private final EmployeeRepository employeeRepository;




    @Override
    public ResponseEntity<String> sendEmail(Employee employee) {


        UserDetails userDetails = detailsService.userDetailsService().loadUserByUsername(employee.getUsername());
        String generatedToken = jwtUtils.generateToken(userDetails);

        String tokenId = userIdUtils.generatedTokenId(30);
        Token token = Token.builder()
                .tokenId(tokenId)
                .token(generatedToken)
                .tokenStatus(TokenStatus.ACTIVE)
                .employee(employee)
                .build();
        employee.setEmailVerificationToken(generatedToken);

        tokenRepository.save(token);
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom("peaceobute65@gmail.com");
            simpleMailMessage.setTo(employee.getEmail());
            simpleMailMessage.setSubject("One last step to complete your registration with DecaPay!");
            simpleMailMessage.setText(
                    "`Please verify your email address`"
                            + "Thank you for registering with our application. To complete registration process and be able to log in,"
                            + " click on the following link: "
                            + " http://localhost:8080/api/v1/auth/verify-token/?token=" + token.getToken()+ ">"
                            + "Final step to complete your registration"
                            + "Thank you! And we are waiting for you inside!"
            );

            mailSender.send(simpleMailMessage);

            return new ResponseEntity<>("Message sent successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error while sending mail", HttpStatus.BAD_REQUEST);
        }


    }

    @Override
    public void sendPasswordReset(Employee employee) {

        String tokenId = userIdUtils.generatedTokenId(30);
        String generatedToken = jwtUtils.generatePasswordResetToken(employee.getEmail());

        Token token = Token.builder()
                .tokenId(tokenId)
                .token(generatedToken)
                .tokenStatus(TokenStatus.ACTIVE)
                .employee(employee)
                .build();
        tokenRepository.save(token);

        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom("peaceobute65@gmail.com");
            simpleMailMessage.setTo(employee.getEmail());
            String subject = "Reset Password";
            String text = "Kindly use the link below to reset your password  " +
                    " http://localhost:8080/api/v1/auth//reset-password/?token=" + token.getToken() + " expires in 15 minutes.>";
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(text);
            mailSender.send(simpleMailMessage);
        } catch(Exception e){
            System.out.println("Error while sending mail");
        }

    }



}
