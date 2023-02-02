package com.sharifplus;

import java.util.Scanner;
import com.sharifplus.Store.*;
import com.sharifplus.Authentication.User;

import java.io.FileWriter;
import java.security.NoSuchAlgorithmException;

public class IO {

    public static void handle() throws NoSuchAlgorithmException {
        Scanner reader = App.reader;
        String input;
        Resturant resturant = App.resturant;
        Cafe cafe = App.cafe;
        Storage storage = App.storage;
        while (true) {
            if (!User.isLogged()) {
                System.out.println(
                        "Available Commands : \n" + IO.Yellow + "\t -Create Account\n" + "\t -Log In\n" + "\t -Exit" + IO.Reset);
                input = reader.nextLine();
                switch (input) {
                    case "Create Account":
                        User.createUsr();
                        break;
                    case "Log In":
                        User.logIn();
                        break;
                    case "Exit" :
                        System.exit(0);
                    default:
                        printError("Invalid Command !");
                }
            } else {
                User usr = User.currentUsr;
                if (usr.isAdmin || usr.isClient) {
                    System.out.println("\tAvailable Commands : \n" + IO.Yellow + "\t -Resturant\n" + "\t -Cafe\n"
                            + IO.Red + "\t -Log Out\n" + "\t -Escalate Privilages\n" + "\t -Order History\n" + "\t -Exit" + IO.Reset);
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
                        case "Escalate Privilages":
                            usr.setPrivilages();
                            break;
                        case "Order History" :
                            usr.printOrderHistory();
                            break;
                        case "Exit" :
                            System.exit(0);
                        default:
                            printError("Invalid Command !");
                    }
                } else if (usr.isEmployee) {
                    System.out.println("\tAvailable Commands : \n" + IO.Yellow + "\t -Storage\n" + IO.Red + "\t -Log Out\n" + "\t -Exit"
                            + IO.Reset);
                    input = reader.nextLine();
                    switch (input) {
                        case "Storage":
                            storage.handle();
                            break;
                        case "Log Out":
                            User.logOut();
                            break;
                        case "Exit" :
                            System.exit(0);
                        default:
                            printError("Invalid Command !");
                    }
                }
            }
        }
    }

    public static void PrintCheckMark() {
        System.out.print(IO.Green + "✓" + IO.Reset);
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
        log(Logging.formatError(errMsg), "./logs/errors.log");
    }

    public static void logInfo(String input) {
        log(Logging.foramtInfo(input), "./logs/info.log");
    }

    public static void log(String input, String fileName)  {
        try {
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(input);
            writer.close();
        }
        catch (Exception e) {
            System.out.println(IO.Red + "Fatal Error Occured During Loggin" + IO.Reset);
            System.exit(1);
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
    public static final String Reset = "\033[0m";
}