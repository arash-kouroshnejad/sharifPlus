package com.sharifplus;

import java.util.Scanner;
import com.sharifplus.Store.*;;

public class IO {
    Scanner reader = new Scanner(System.in);
    String input;
    Resturant resturant = new Resturant();

    public IO() {
        while (true) {
           switch (input.split(" ")[0]) {
            case "ADD" :
              new Order(0, null, null, input);
            case "GET" :
                resturant.getMenu();
            case ""
           } 
        }
    }

    public static final String Black = "\u001b[30m";
    public static final String Red = "\u001b[31m";
    public static final String Green = "\u001b[32m";
    public static final String Yellow = "\u001b[33m";
    public static final String Blue = "\u001b[34m";
    public static final String Magenta = "\u001b[35m";
    public static final String Cyan = "\u001b[36m";
    public static final String White = "\u001b[37m";
    public static final String Reset = "\\033[0m";
}