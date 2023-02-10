package com.sharifplus;

import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.Scanner;
import com.sharifplus.Authentication.*;
import com.sharifplus.Store.*;

public class App 
{

    public static Scanner reader = new Scanner(System.in);
    public static Admin admin;
    public static HashMap <Long, User> users = new HashMap<>();
    public static Storage storage = new Storage(IO.getInventory());
    public static LinkedList <Order> stack = new LinkedList<>();
    public static Resturant resturant = new Resturant();
    public static Cafe cafe = new Cafe();

    public static void main( String[] args ) throws NoSuchAlgorithmException, InvalidType
    {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                IO.saveState();
            }
        }, "Shutdown-Hook"));
        System.out.println(IO.Green + "Welcome to this goddamed sht hole" + IO.Reset);
        IO.getUsers();
        IO.handle();
        System.out.println(IO.Yellow + "See Ya" + IO.Reset);
    }
}
