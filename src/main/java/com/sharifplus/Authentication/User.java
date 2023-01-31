package com.sharifplus.Authentication;
import java.security.MessageDigest;

public abstract class User {
    boolean isAdmin;
    boolean isEmployee;
    boolean isClient;
    protected final String name;
    protected final long userId;
    protected final String pawrdHsh;

    public User(String passwrd, String name) throws java.security.NoSuchAlgorithmException {
        userId = (long) Math.floor(Math.random()*Math.pow(10, 5));
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(passwrd.getBytes());
        pawrdHsh = md.digest().toString();
        this.name = name;
    }
}
