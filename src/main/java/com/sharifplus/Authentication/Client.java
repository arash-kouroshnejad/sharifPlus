package com.sharifplus.Authentication;

public class Client extends User {
    public Client(String passwrd, String name) throws java.security.NoSuchAlgorithmException {
        super(passwrd, name);
        isClient = true;
    }
}
