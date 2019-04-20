package com.busbooking.validator;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class EncrytedPasswordUtils {
	 
	/* ---------------- ENCRYTE PASSWORD WITH  BCryptPasswordEncoder------------------------ */
    public static String encrytePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    } 
 
	/* ---------------- TEST BCryptPasswordEncoder------------------------ */
//    public static void main(String[] args) {
//        String password = "123456789";
//        String encrytedPassword = encrytePassword(password);
//        System.out.println("Encryted Password: " + encrytedPassword);
//    }
     
}
