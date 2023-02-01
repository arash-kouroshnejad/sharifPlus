package com.sharifplus.Store;

import com.sharifplus.*;
import com.sharifplus.Authentication.User;
import java.util.*;
import com.sharifplus.Products.*;

public abstract class Store {

    Storage storage = App.storage;
    LinkedList<Order> queue = App.stack;
    User usr = User.currentUsr;
    Scanner reader = App.reader;
    public final String Menu;
    String[] arr;

    public Store(String Menu) {
        this.Menu = Menu;
        arr = Menu.split(" ");
    }

    public abstract void getMenu();
    
    public void Handle() {
        String input;
        while (true) {
            System.out.println("Available Commands : " + IO.Yellow +"\n \t -Get Menu" + "\n \t -Add Order " + IO.Magenta + "Product0 Product1 ..." + IO.Yellow + "\n \t -Back" + IO.Reset);
            input = reader.nextLine();
            int length = input.length();
            if (input.equals("Get Menu")) {
                getMenu();
            }
            else if (length >= 9 && input.substring(0, 9).equals("Add Order")) {
                addOrder(input);
            }
            else if (input.equals("Back")) {
                return;
            }
            else {
                IO.printError("Invalid Command !");
            }
        }
    }
    public void addOrder(String input) {
        Order order = new Order(usr.userId, usr, input);
        for (Product product : order.products) {
            outer :
            for (int i=0;i<arr.length;i++) {
                if (arr[i].equals(product.name)) {
                    continue outer;
                }
                if (i == arr.length - 1) {
                    System.out.println("Invalid Product : " + IO.Red + product.name);
                    return;
                }
            }
        }
        if (storage.isAvailable(order)) {
            IO.PrintCheckMark();
            System.out.println(IO.Green + "\t Order Created On " + IO.Yellow + java.time.LocalDateTime.now() + IO.Reset + "\tOrder Id : " + IO.Blue + order.ID + IO.Reset);
            storage.allocate(order);
            queue.add(order);
        }
        else {
            System.out.println(IO.Yellow + "Unfortunately This Order Is Not Available Right Now :(" + IO.Reset);
        }
    }
}
