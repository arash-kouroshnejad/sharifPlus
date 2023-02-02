package com.sharifplus.Authentication;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import com.sharifplus.*;
import java.io.Console;
import java.util.HashMap;
import java.util.LinkedList;
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
    public LinkedList<Order> OrderHistory = new LinkedList<>();

    public User(String passwrd, String name) throws java.security.NoSuchAlgorithmException {
        userId = (long) Math.floor(Math.random() * Math.pow(10, 5));
        pawrdHsh = hash(passwrd);
        this.name = name;
    }

    public User(String passwrdHsh, String usrName, long ID) {
        this.pawrdHsh = parse(passwrdHsh);
        this.name = usrName;
        this.userId = ID;
        allUsers.put(ID, this);
    }

    public static void createUsr(String accssLevel) throws NoSuchAlgorithmException, InvalidType {
        Scanner reader = App.reader;
        System.out.print("Enter Username : " + IO.Red + "Or Type cancel ");
        String userName = getUsername(reader);
        if (userName.equals("cancel")) {
            throw new InvalidType();
        }
        System.out.print(IO.Green + "Succesful!\n" + IO.Reset + " Enter Password : " + IO.Red + "Or type cancel ");
        String passwrd = getPassword(reader);
        if (passwrd.equals("cancel")) {
            throw new InvalidType();
        }
        System.out.println(IO.Green + "Succesful \n");
        User usr;
        switch (accssLevel) {
            case "Admin":
                usr = new Admin(passwrd, userName);
                break;
            case "Employee":
                usr = new Employee(passwrd, userName);
                break;
            case "Client":
                usr = new Client(passwrd, userName);
                break;
            default:
                IO.printError("Invalid Access Level " + accssLevel);
                throw new InvalidType();
        }
        allUsers.put(usr.userId, usr);
        IO.PrintCheckMark();
        System.out.println(IO.Green + " Succesful... User " + IO.Blue + userName + IO.Reset + " Created At "
                + java.time.LocalDateTime.now());
        IO.logInfo("A New Account Has Been Created With Username " + IO.Cyan + userName + IO.Reset);
    }

    private static String getUsername(Scanner reader) {
        String output = reader.nextLine();
        if (output.length() == 0) {
            IO.printError(IO.Magenta + "Auth Error:" + IO.Red + "  Does Not Meet Minimum Length! Enter Another : ");
            System.out.print("Or type " + IO.Red + "cancel " + IO.Reset);
            return getUsername(reader);
        }
        for (User usr : allUsers.values()) {
            if (usr.name.equals(output)) {
                IO.printError(IO.Magenta + "Auth Error:" + IO.Red + "  Username Already Exist ! Pick Another : ");
                System.out.print("Or type " + IO.Red + "cancel " + IO.Reset);
                return getUsername(reader);
            }
        }
        return output;
    }

    private static String getPassword(Scanner reader) {
        Console console = System.console();
        char[] password = console.readPassword();
        if (password.length == 0) {
            IO.printError(IO.Magenta + "Auth Error:" + IO.Red + "  Invalid Length !" + IO.Red
                    + "Or Type cancel ");
            return getPassword(reader);
        }
        String output = "";
        for (int i = 0; i < password.length; i++) {
            output += password[i];
        }
        return output;
    }

    public static void logIn() throws NoSuchAlgorithmException, InvalidType {
        Scanner reader = App.reader;
        System.out.print("Enter Username : (or enter " + IO.Red + "cancel" + IO.Reset + " to exit)\n \t");
        String name = reader.nextLine();
        if (name.equals("cancel")) {
            return;
        }
        if (name.length() == 0) {
            IO.printError(IO.Magenta + "Auth Error:" + IO.Red + "  Invalid Length !");
            logIn();
            return;
        }
        for (User usr : allUsers.values()) {
            if (usr.name.equals(name)) {
                System.out.println("Enter Password Or Type" + IO.Red + " cancel" + IO.Reset + " to quit");
                String passwrd = getPassword(reader);
                if (passwrd.equals("cancel")) {
                    throw new InvalidType();
                }
                if (usr.compareHashes(hash(passwrd))) {
                    System.out.println(IO.Green + "  Logged In As " + IO.Blue + usr.name + IO.Reset);
                    if (usr.isAdmin) {
                        System.out.println("Acoount Mode : " + usr.getPrivilage());
                    } else if (usr.isClient) {
                        System.out.println("Account Mode : " + usr.getPrivilage());
                    } else if (usr.isEmployee) {
                        System.out.println("Account Mode : " + usr.getPrivilage());
                    }
                    isLogged = true;
                    currentUsr = usr;
                    IO.logInfo("Login By : ");
                    return;
                } else {
                    IO.printError(IO.Magenta + "Auth Error:" + IO.Red + "  Wrong Passwrord !");
                    return;
                }
            }
        }
        if (!isLogged) {
            IO.printError("Username Does Not Exist !");
        }
    }

    public static void logOut() {
        System.out.println(IO.Cyan + "Logged Out" + IO.Reset);
        IO.logInfo("Logout By : ");
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

    public void setPrivilages() throws NoSuchAlgorithmException {
        Scanner reader = App.reader;
        System.out.print("Enter Admin Password Or Type " + IO.Red + "cancel" + IO.Reset + " To Quit :");
        User admin = App.admin;
        String passwrd = getPassword(reader);
        if (passwrd.equals("cancel")) {
            return;
        }
        if (admin.compareHashes(hash(passwrd))) {
            System.out.print("Enter Access Level " + IO.Magenta + "Admin" + IO.Yellow + " Employee" + IO.Green
                    + " Client : " + IO.Reset);
            switch (reader.nextLine()) {
                case "Admin":
                    isAdmin = true;
                    isClient = false;
                    isEmployee = false;
                    IO.logInfo("User " + IO.Cyan + name + IO.Reset + " Has Been Escalated To " + IO.Magenta + "Admin"
                            + IO.Reset);
                    break;
                case "Employee":
                    isAdmin = false;
                    isClient = false;
                    isEmployee = true;
                    IO.logInfo("User " + IO.Cyan + name + IO.Reset + " Has Been Escalated To " + IO.Yellow + "Employee"
                            + IO.Reset);
                    break;
                case "Client":
                    isAdmin = false;
                    isClient = true;
                    isEmployee = false;
                    IO.logInfo("User " + IO.Cyan + name + IO.Reset + " Has Been Escalated To " + IO.Green + "Client"
                            + IO.Reset);
                    break;
                default:
                    IO.printError("Invalid Access Level !");
                    return;
            }
        } else {
            IO.printError("Wrong Password Failed Escalation");
        }
    }

    private static byte[] hash(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(input.getBytes());
        byte[] output = md.digest();
        md.reset();
        return output;
    }

    public void printOrderHistory() {
        Order.list(true, currentUsr);
        IO.logInfo("Purchase Hisotry Generated For : ");
    }

    public String getPrivilage() {
        if (isAdmin) {
            return IO.Magenta + "Admin" + IO.Reset;
        } else if (isEmployee) {
            return IO.Yellow + "Employee" + IO.Reset;
        } else {
            return IO.Green + "Client" + IO.Reset;
        }
    }

    private static byte[] parse(String input) {
        input = input.substring(1, input.length() - 1);
        String[] parsed = input.split(",");
        byte[] output = new byte[parsed.length];
        for (int i = 0; i < output.length; i++) {
            output[i] = Byte.parseByte(parsed[i].strip());
        }
        return output;
    }

    // private static void dumpBytes(byte[] input) {
    // String output = "{";
    // for (int i=0;i<input.length;i++) {
    // output += input[i] + ((i != input.length - 1) ? "," : "");
    // }
    // System.out.println(output+"}");
    // }
}
