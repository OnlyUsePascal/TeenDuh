package com.example.teenduh.util;

public class Validators {
    public static String[] homeNumberValid(String phoneNumber) {
        String[] result = phoneNumber.split("\\s+", 2);

        if (result.length == 1) {
            return null;
        }

        result[1] = result[1].replaceAll("[^0-9]","");
        return result;
    }
}
