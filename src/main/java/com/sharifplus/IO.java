package com.sharifplus;

import java.io.PrintWriter;
import java.util.Scanner;
import com.sharifplus.Store.*;
import com.sharifplus.Authentication.User;
import java.security.NoSuchAlgorithmException;

public class IO {
    Scanner reader = App.reader;
    String input;
    Resturant resturant = App.resturant;
    Cafe cafe = App.cafe;
    Boolean isLogged = User.isLogged;
    User usr = User.currentUsr;
    Storage storage = App.storage;

    public IO() throws NoSuchAlgorithmException {
        while (true) {
            if (!isLogged) {
                System.out.println(
                        "Available Commands : \n" + IO.Yellow + "\t -Create Account\n" + "\t -Log In" + IO.Reset);
                input = reader.nextLine();
                switch (input) {
                    case "Create Account":
                        User.createUsr();
                        break;
                    case "Log In":
                        User.logIn();
                        break;
                    default:
                        System.out.println(IO.Red + "Invalid Command !" + IO.Reset);
                }
            } else {
                if (usr.isClient) {
                    System.out.println("\tAvailable Commands : \n" + IO.Yellow + "\t -Resturant\n" + "\t -Cafe\n"
                            + IO.Red + "\t-Log Out" + IO.Reset);
                    input = reader.nextLine();
                    switch (input) {
                        case "Log Out":
                            User.logOut();
                            break;
                        case "Resturant":
                            resturant.Handle();
                            break;
                        case "Cafe":
                            cafe.Handle();
                            break;
                        default:
                            System.out.println(IO.Red + "Invalid Command !");
                    }
                } else if (usr.isEmployee) {
                    System.out.println("\tAvailable Commands : \n" + IO.Yellow + "\t -Storage\n" + IO.Red + "\t-Log Out"
                            + IO.Reset);
                    input = reader.nextLine();
                    switch (input) {
                        case "Storage":
                            storage.handle();
                            break;
                        case "Log Out":
                            User.logOut();
                            break;
                        default:
                            System.out.println(IO.Red + "Invalid Command !" + IO.Reset);
                    }
                }
            }
        }
    }

    public static void PrintCheckMark() {
        PrintWriter writer = new PrintWriter(System.out, true);
        char check = '\u2705';
        writer.print(check);
    }

    public static boolean isDigit(char c) {
        if (c == 45) {
            return true;
        } else if (c < 58 && c > 47) {
            return true;
        } else if (c == 43) {
            return true;
        }
        return false;
    }

    public static void printError(String errMsg) {
        System.out.println(IO.Red + errMsg + IO.Reset);
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