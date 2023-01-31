package com.sharifplus.Authentication;

public class Employee extends User{
    public Employee (String passwrd, String name) throws java.security.NoSuchAlgorithmException {
        super(passwrd, name);
        isEmployee = true;
    }
}
