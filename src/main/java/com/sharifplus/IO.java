package com.sharifplus;

import java.util.Scanner;
import com.sharifplus.Store.*;
import com.sharifplus.Authentication.*;
import com.sharifplus.Products.ProductsList;

import java.io.*;
import java.security.NoSuchAlgorithmException;

public class IO {

    public static void handle() throws NoSuchAlgorithmException, InvalidType {
        Scanner reader = App.reader;
        String input;
        Resturant resturant = App.resturant;
        Cafe cafe = App.cafe;
        Storage storage = App.storage;
        while (true) {
            if (!User.isLogged()) {
                System.out.println(
                        "Available Commands : \n" + IO.Yellow + "\t -Create Account\n" + "\t -Log In\n" + "\t -Exit"
                                + IO.Reset);
                input = reader.nextLine();
                switch (input) {
                    case "Create Account":
                        try {
                            User.createUsr("Client");
                        }
                        catch (Exception e) {
                            printError("Cancelled");
                        }
                        break;
                    case "Log In":
                        try {
                            User.logIn();
                        }
                        catch (Exception e) {
                            printError("Cancelled");
                        }
                        break;
                    case "Exit":
                        return;
                    default:
                        printError("Invalid Command !");
                }
            } else {
                User usr = User.currentUsr;
                if (usr.isAdmin || usr.isClient) {
                    System.out.println("\tAvailable Commands : \n" + IO.Yellow + "\t -Resturant\n" + "\t -Cafe\n"
                            + IO.Red + "\t -Log Out\n" + ((usr.isAdmin) ? "\t -Escalate Privilages\n" : "")
                            + "\t -Order History\n" + "\t -Exit" + IO.Reset);
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
                            if (!usr.isAdmin) {
                                printError("Invalif Command !");
                                break;
                            }
                            usr.setPrivilages();
                            break;
                        case "Order History":
                            usr.printOrderHistory();
                            break;
                        case "Exit":
                            return;
                        default:
                            printError("Invalid Command !");
                    }
                } else if (usr.isEmployee) {
                    System.out.println("\tAvailable Commands : \n" + IO.Yellow + "\t -Storage\n" + IO.Red
                            + "\t -Log Out\n" + "\t -Exit"
                            + IO.Reset);
                    input = reader.nextLine();
                    switch (input) {
                        case "Storage":
                            storage.handle();
                            break;
                        case "Log Out":
                            User.logOut();
                            break;
                        case "Exit":
                            return;
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

    public static void log(String input, String fileName) {
        try {
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(input);
            writer.close();
        } catch (Exception e) {
            System.out.println(IO.Red + "Fatal Error Occured During Loggin" + IO.Reset);
            System.exit(1);
        }
    }

    public static void getAccountActivity(User usr) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("./logs/info.log"));
            String line = reader.readLine();
            while (line != null) {
                if (line.contains(" User : " + IO.Magenta + usr.name + IO.Reset)) {
                    System.out.println(line);
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            printError("Error Occured During Log Reading");
        }
    }

    public static int[] getInventory() {
        int[] inventory = new int[ProductsList.MATERIALS.length];
        try {
            BufferedReader reader = new BufferedReader(new FileReader("./database/inventory.txt"));
            String line = reader.readLine();
            String[] parsed;
            while (line != null) {
                parsed = line.split(" ");
                if (parsed.length < 3) {
                    printError(" Invalid Inventory Entry " + line);
                    System.exit(1);
                }
                for (int j = 0; j < inventory.length; j++) {
                    if (parsed[0].equals(ProductsList.MATERIALS[j])) {
                        inventory[j] = Integer.parseInt(parsed[2]); // Meat : 5
                    }
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            printError("Inventory File Could Not Be Read Properly !");
            System.exit(1);
        }
        return inventory;
    }

    public static void getUsers() { // userId usrName passwrdHsh accssLvl
        try {
            BufferedReader reader = new BufferedReader(new FileReader("./database/accounts.txt"));
            String line = reader.readLine();
            User usr = null;
            while (line != null) {
                String[] parsed = line.split(" ");
                if (parsed.length < 4) {
                    printError("Invalid Userlist Entry " + line);
                    System.exit(1);
                }
                switch (parsed[3]) {
                    case "Admin":
                        usr = new Admin(parsed[2], parsed[1], Long.parseLong(parsed[0]));
                        break;
                    case "Employee" :
                        usr = new Employee(parsed[2], parsed[1], Long.parseLong(parsed[0]));
                        break;
                    case "Client" :
                        usr = new Client(parsed[2], parsed[1], Long.parseLong(parsed[0]));
                        break;
                    default :
                        printError("Invalid Type");
                }
                if (usr == null) {
                    printError(" Error ");
                }
                else {
                    logInfo(" User " + parsed[1] + " Inported From File" + " Access Level : " + usr.getPrivilage());
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            printError(" User Accounts Could Not Be Read Properly !");
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