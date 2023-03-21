package com.example.employee.system.employeesystem.service;

import com.example.employee.system.employeesystem.dto.AddressDto;
import com.example.employee.system.employeesystem.dto.EmployeeDto;
import com.example.employee.system.employeesystem.entity.Employee;
import com.example.employee.system.employeesystem.entity.Token;
import com.example.employee.system.employeesystem.enums.TokenStatus;
import com.example.employee.system.employeesystem.exceptions.PasswordMismatchException;
import com.example.employee.system.employeesystem.exceptions.UserAlreadyExistsException;
import com.example.employee.system.employeesystem.exceptions.UserNameNotFoundException;
import com.example.employee.system.employeesystem.mailSender.EmailSenderService;
import com.example.employee.system.employeesystem.models.requestModels.AuthenticationRequest;
import com.example.employee.system.employeesystem.models.requestModels.ForgotPasswordRequest;
import com.example.employee.system.employeesystem.models.requestModels.ResetPasswordRequest;
import com.example.employee.system.employeesystem.models.responseModels.AuthenticationResponse;
import com.example.employee.system.employeesystem.models.responseModels.TokenVerificationResponse;
import com.example.employee.system.employeesystem.repository.EmployeeRepository;
import com.example.employee.system.employeesystem.repository.TokenRepository;
import com.example.employee.system.employeesystem.security.JwtUtils;
import com.example.employee.system.employeesystem.utils.UserIdUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    UserIdUtils userIdUtils;
    @Autowired
    EmailSenderService emailSenderService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;





    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
      Optional<Employee> returnedUEmployee =employeeRepository.findByEmail(employeeDto.getEmail());

      if(returnedUEmployee != null) throw new UserAlreadyExistsException("Employee already exists");

//     for (AddressDto singleAddressDto : employeeDto.getAddressDtos()){
//         singleAddressDto.setAddressId(userIdUtils.generatedAddressId(30));
//         singleAddressDto.setEmployeeDto(employeeDto);
//         employeeDto.getAddressDtos().set(1, singleAddressDto);
//     }

      for(int i = 0; i < employeeDto.getAddressDtos().size(); i++){
          AddressDto  address = employeeDto.getAddressDtos().get(i);
          address.setEmployeeDto(employeeDto);
          address.setAddressId(userIdUtils.generatedAddressId(30));
          employeeDto.getAddressDtos().set(i, address);
      }
         EmployeeDto returnValue = new EmployeeDto();
         Employee employee = new Employee();
         BeanUtils.copyProperties(employeeDto, employee);

        String publicEmployeeId = userIdUtils.generatedUserId(30);
        employee.setEmployeeId(publicEmployeeId);

        employee.setEncryptedPassword(bCryptPasswordEncoder.encode(employeeDto.getPassword()));

        Employee savedEmployee =employeeRepository.save(employee);

        emailSenderService.sendEmail(savedEmployee);

        BeanUtils.copyProperties( savedEmployee, returnValue);
        return returnValue;
    }

    @Override
    @ResponseStatus(HttpStatus.FOUND)
    public EmployeeDto findEmployeeById(String employeeId) {
        EmployeeDto returnValue = new EmployeeDto();
        Employee returnedEmployee = employeeRepository.findEmployeeById(employeeId);
        if(returnedEmployee == null) throw new UserNameNotFoundException("User does not exist");

       Employee employee =  employeeRepository.findEmployeeById(employeeId);
       BeanUtils.copyProperties(employee, returnValue);

        return returnValue;
    }

    @Override
    @ResponseStatus(HttpStatus.FOUND)
    public List<EmployeeDto> findAllEmployees(int page, int limit) {
        List<EmployeeDto> returnValue = new ArrayList<>();

        if (page > 0) page = page - 1;

        org.springframework.data.domain.Pageable pageableRequest =  PageRequest.of(page, limit);
        Page<Employee> employeesPage= employeeRepository.findAll(pageableRequest);
        List<Employee> employees = employeesPage.getContent();

        for(Employee singleEmployee : employees){
            EmployeeDto employeeDto = new EmployeeDto();
            BeanUtils.copyProperties(singleEmployee, employeeDto);
            returnValue.add(employeeDto);
        }


        return returnValue;
    }

    @Override
    @ResponseStatus(HttpStatus.CONTINUE)
    public EmployeeDto updateEmployee(EmployeeDto employeeDto, String employeeId) {
        EmployeeDto returnValue = new EmployeeDto();

        Employee returnedEmployee = employeeRepository.findEmployeeById(employeeId);
        if(returnedEmployee == null) throw new UserNameNotFoundException("User does not exist");


        returnedEmployee.setFirstName(employeeDto.getFirstName());
        returnedEmployee.setLastName(employeeDto.getLastName());
        Employee updatedEmployee = employeeRepository.save(returnedEmployee);
        BeanUtils.copyProperties(updatedEmployee, returnValue);

        return returnValue;
    }

    @Override
    public void deleteEmployee(String employeeId) {
        Employee employee = employeeRepository.findEmployeeById(employeeId);
        if(employee == null){
            throw new UserNameNotFoundException("User does not exist");
        }
        employeeRepository.delete(employee);
    }

    @Override
    public TokenVerificationResponse verifyToken(String token) {

        TokenVerificationResponse returnValue = new TokenVerificationResponse();
        Token getToken  =tokenRepository.findByToken(token);
        if(getToken != null) throw new RuntimeException("Token already exists");

        getToken.setTokenStatus(TokenStatus.VERIFIED);
        tokenRepository.save(getToken);

        Employee employee = employeeRepository.findEmployeeById(getToken.getEmployee().getEmail());
              employee.setEmailVerificationStatus(true);
              employeeRepository.save(employee);

        returnValue.setToken(getToken.getToken());
        returnValue.setTokenStatus(getToken.getTokenStatus());
        returnValue.setEmail(getToken.getEmployee().getEmail());
        return returnValue;
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        AuthenticationResponse returnValue = new AuthenticationResponse();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        Employee employee = employeeRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new UserNameNotFoundException("Employee does not exist. "));
        String generatedToken = jwtUtils.generateToken(employee);

        returnValue.setToken(generatedToken);
        return returnValue;
//        return AuthenticationResponse.builder()
//                .token(generatedToken)
//                .build();
    }

    @Override
    public String forgotPassword(ForgotPasswordRequest request) {
        String email = request.getEmail();

        Optional<Employee> returnedEmployee = employeeRepository.findByEmail(email);
        if(returnedEmployee.isPresent()) {

            emailSenderService.sendPasswordReset(returnedEmployee.get());
            return "Check your email for password reset instructions";
        }

        else throw new UserNameNotFoundException("Employee email does not exist");

    }

    @Override
    public String resetPassword(ResetPasswordRequest request) {
      Optional<Employee> returnedEmployee  =employeeRepository.findByEmail(request.getEmail());
        if(returnedEmployee.isPresent() && request.getNewPassword() == request.getConfirmPassword()) {
            String encryptedPassword = bCryptPasswordEncoder.encode(request.getConfirmPassword());
            returnedEmployee.get().setEncryptedPassword(encryptedPassword);
            employeeRepository.save(returnedEmployee.get());
        }else{
            throw new PasswordMismatchException("Your password does not match");
        }
        return "Password reset successful";
    }

}
