package com.sharifplus.Authentication;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.sharifplus.App;
import com.sharifplus.IO;
import java.io.Console;
import java.util.HashMap;
import java.util.Scanner;

public abstract class User {
    static HashMap<Long, User> allUsers = App.users;
    private static boolean isLogged = false;
    public static User currentUsr;
    public boolean isAdmin;
    public boolean isEmployee;
    public boolean isClient;
    public final String name;
    public final long userId;
    protected final byte[] pawrdHsh;

    public User(String passwrd, String name) throws java.security.NoSuchAlgorithmException {
        userId = (long) Math.floor(Math.random() * Math.pow(10, 5));
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(passwrd.getBytes());
        pawrdHsh = md.digest();
        md.reset();
        this.name = name;
    }

    public static void createUsr() throws NoSuchAlgorithmException {
        Scanner reader = App.reader;
        System.out.print("Enter Username : ");
        String userName = getUsername(reader);
        System.out.print(IO.Green + "Succesful!\n" + IO.Reset + " Enter Password : ");
        String passwrd = getPassword(reader);
        System.out.println(IO.Green + "Succesful \n");
        User usr = new Client(passwrd, userName);
        allUsers.put(usr.userId, usr);
        IO.PrintCheckMark();
        System.out.println(IO.Green + "Succesful... User " + IO.Blue + userName + IO.Reset + " Created At "
                + java.time.LocalDateTime.now());
    }

    private static String getUsername(Scanner reader) {
        String output = reader.nextLine();
        if (output.length() == 0) {
            System.out.print(IO.Red + " Does Not Meet Minimum Length! Enter Another : " + IO.Reset);
            getUsername(reader);
        }
        for (User usr : allUsers.values()) {
            if (usr.name.equals(output)) {
                System.out.print(IO.Red + "Username Already Exist ! Pick Another : " + IO.Reset);
                getUsername(reader);
            }
        }
        return output;
    }

    private static String getPassword(Scanner reader) {
        Console console = System.console();
        char[] password = console.readPassword();
        if (password.length == 0) {
            System.out.print(IO.Red + " Invalid Length ! Pick Another : " + IO.Reset);
            getPassword(reader);
        }
        String output = "";
        for (int i = 0; i < password.length; i++) {
            output += password[i];
        }
        return output;
    }

    public static void logIn() throws NoSuchAlgorithmException {
        Scanner reader = App.reader;
        System.out.print("Enter Username : (or enter " + IO.Red + "cancel" + IO.Reset + " to exit)\n \t");
        String name = reader.nextLine();
        if (name.equals("cancel")) {
            return;
        }
        if (name.length() == 0) {
            System.out.println(IO.Red + " Invalid Length !" + IO.Reset);
            logIn();
        }
        for (User usr : allUsers.values()) {
            if (usr.name.equals(name)) {
                System.out.println("Enter Password : ");
                String passwrd = getPassword(reader);
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                md.update(passwrd.getBytes());
                if (usr.compareHashes(md.digest())) {
                    System.out.println(IO.Green + "  Logged In As " + IO.Blue + usr.name + IO.Reset);
                    if (usr.isAdmin) {
                        System.out.println("Acoount Mode : " + IO.Magenta + "Admin" + IO.Reset);
                    } else if (usr.isClient) {
                        System.out.println("Account Mode : " + IO.White + "Client" + IO.Reset);
                    } else if (usr.isEmployee) {
                        System.out.println("Account Mode : " + IO.Yellow + "Employee" + IO.Reset);
                    }
                    isLogged = true;
                    currentUsr = usr;
                    return;
                } else {
                    System.out.println(IO.Red + " Wrong Passwrord !" + IO.Reset);
                    return;
                }
            }
        }
        logIn();
    }

    public static void logOut() {
        System.out.println(IO.Cyan + "Logged Out" + IO.Reset);
        currentUsr = null;
        isLogged = false;
    }

    private boolean compareHashes(byte[] hash) {
        if (hash.length != pawrdHsh.length) {
            return false;
        }
        for (int i = 0; i < hash.length; i++) {
            if (hash[i] != pawrdHsh[i]) {
                return false;
            }
        }
        return true;
    }

    public static boolean isLogged() {
        return isLogged;
    }
}
