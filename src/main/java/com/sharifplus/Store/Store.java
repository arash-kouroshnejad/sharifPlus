package com.sharifplus.Store;

import com.sharifplus.*;
import com.sharifplus.Authentication.User;
import java.util.*;

public abstract class Store {

    Storage storage = App.storage;
    LinkedList<Order> queue = App.stack;
    User usr = User.currentUsr;
    Scanner reader = App.reader;

    public abstract void getMenu();
    public abstract void addOrder(String input);

    
    public void Handle() {
        String input;
        while (true) {
            System.out.println("Available Commands : " + IO.Yellow +"\n \t -Get Menu" + "\n \t -Add Order" + IO.Magenta + "Product0 Product1 ..." + IO.Yellow + "\n \t Back" + IO.Reset);
            input = reader.nextLine();
            switch (input.substring(0, 9)) {
                case "Get Menu" :
                    getMenu();
                    break;
                case "Add Order" :
                    addOrder(input);
                    break;
                case "Back" :
                    return;
                default :
                    System.out.println(IO.Red + "Invalid Command !");
                    Handle();
            }
        }
    }
}
