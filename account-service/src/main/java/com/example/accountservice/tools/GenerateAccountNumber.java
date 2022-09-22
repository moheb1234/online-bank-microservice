package com.example.accountservice.tools;

public class GenerateAccountNumber {
    public static String generateAccountNumber(){
        int random;
        StringBuilder number = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            random = (int) (Math.random() * (10));
            number.append(random);
        }
        return number.toString();
    }
}
