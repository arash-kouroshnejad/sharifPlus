package com.sharifplus.Authentication;

public class Admin extends User {
    public Admin (String passwrd, String name) throws java.security.NoSuchAlgorithmException {
        super(passwrd, name);
        isAdmin = true;
    }
    public Admin(String passwrdHsh, String name, Long id){
        super(passwrdHsh, name, id);
        isAdmin = true;
    }
}
