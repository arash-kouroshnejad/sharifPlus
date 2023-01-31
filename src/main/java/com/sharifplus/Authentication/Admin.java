package com.sharifplus.Authentication;

public class Admin extends User {
    public Admin (String passwrd, String name) throws java.security.NoSuchAlgorithmException {
        super(passwrd, name);
        isAdmin = true;
    }
}
