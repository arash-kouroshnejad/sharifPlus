package com.sharifplus;

import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.Scanner;
import com.sharifplus.Authentication.*;
import com.sharifplus.Store.*;

public class App 
{

    static int[] tmpStorage = {15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15};

    public static Scanner reader = new Scanner(System.in);
    public static Admin admin;
    public static HashMap <Long, User> users = new HashMap<>();
    public static Storage storage = new Storage(tmpStorage);
    public static LinkedList <Order> stack = new LinkedList<>();
    public static Resturant resturant = new Resturant();
    public static Cafe cafe = new Cafe();

    public static void main( String[] args ) throws NoSuchAlgorithmException
    {
        admin = new Admin("admin", "admin");
        users.put(admin.userId, admin);
        User Employee = new Employee("a", "employee");
        users.put(Employee.userId, Employee);
        User Client = new Client("a", "client");
        users.put(Client.userId, Client);
        IO logic = new IO();
        System.out.println( "Hello World!" );
    }
}
