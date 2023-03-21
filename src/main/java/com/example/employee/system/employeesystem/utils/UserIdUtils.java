package com.example.employee.system.employeesystem.utils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component
public class UserIdUtils {

    private final Random RANDOM = new SecureRandom();
    private final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public String generatedUserId(int length) {
        return generatedRandomString(length);
    }

    public String generatedAddressId(int length) {
        return generatedRandomString(length);
    }

    public String generatedTokenId(int length) {
        return generatedRandomString(length);
    }
    private String generatedRandomString(int length) {
        StringBuilder returnValue = new StringBuilder(length);

        for(int z = 0; z < length; z++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(returnValue);
    }




}
