package com.sharifplus;

import java.util.*;
import java.util.Scanner;
import com.sharifplus.Authentication.*;

public class App 
{

    public static Scanner reader = new Scanner(System.in);
    public static HashMap <Long, User> users = new HashMap<>();
    public static Storage storage = new Storage(null);
    public static LinkedList <Order> stack = new LinkedList<>();

    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }
}
