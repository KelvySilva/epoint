package br.com.epoint.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {
    public static String encode(String string) {
        //admin - aj[lo12po
        //spring - spring
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(string);
    }

    public static void main(String[] args) {
        System.out.println(       PasswordEncoder.encode("aj[lo12po"));
    }
}
