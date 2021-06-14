package com.util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class StringHelper {
    private static final String CAPITAL_CASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER_CASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String SPECIAL_CHARACTERS = "!@#$";
    private static final String NUMBERS = "1234567890";

    public static String generateUserNameFromFullName(String fullName){
        String[] parts = fullName.trim().split(" ");
        String username = "";
        username += parts[parts.length - 1];
        for(int i = 0; i < parts.length - 1; i++){
            if(!parts[i].isEmpty()){
                char[] chars = parts[i].toLowerCase().toCharArray();
                chars[0] = Character.toLowerCase(chars[0]);
                username += ((String.valueOf(chars[0]).trim()));
            }
        }
        return username.toLowerCase().trim();
    }

    public static String generatePassword(int length){
        String combinedChars = CAPITAL_CASE_LETTERS + LOWER_CASE_LETTERS + SPECIAL_CHARACTERS + NUMBERS;
        Random random = new Random();
        char[] password = new char[length];

        password[0] = LOWER_CASE_LETTERS.charAt(random.nextInt(LOWER_CASE_LETTERS.length()));
        password[1] = CAPITAL_CASE_LETTERS.charAt(random.nextInt(CAPITAL_CASE_LETTERS.length()));
        password[2] = SPECIAL_CHARACTERS.charAt(random.nextInt(SPECIAL_CHARACTERS.length()));
        password[3] = NUMBERS.charAt(random.nextInt(NUMBERS.length()));

        for(int i = 4; i < length ; i++) {
            password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
        }
        return new String(password);
    }

    public static boolean isNullOrEmpty(String str){
        return (str == null || str.isEmpty());
    }

    public static byte[] getSHA(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    public static String toHexString(byte[] hash) {
        BigInteger number = new BigInteger(1, hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));

        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

}
