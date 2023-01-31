package com.sharifplus.Store;

import com.sharifplus.*;
import com.sharifplus.Products.*;

public class Cafe extends Store{

    String Menu = "Chocolate-Cake Ice-Cream Vanilla-Cake Coffea Hot-Chocolate Soda Tea Water";
    String[] arr = Menu.split(" ");

    public void getMenu() {
        String alignment = "|%-40s";
        System.out.format("+--------------------------+-----------------------------+\n");
        System.out.format("|        " + IO.Magenta + "Desserts" + IO.Reset + "          |            " + IO.Yellow + "Drinks" + IO.Reset + "       |\n");
        System.out.format("+--------------------------+-----------------------------+\n");
        for (int i = 0; i < 4 ;i++) {
            System.out.format(alignment,((storage.isAvailable(Order.find(arr[i]))) ? IO.Green : IO.Red) + arr[i] + IO.Reset);
            if (i < 2) {
                System.out.format(alignment,((storage.isAvailable(Order.find(arr[i + 4]))) ? IO.Green : IO.Red) + arr[i + 4] + IO.Reset);
            }
            System.out.print("|\n");
        }
        System.out.format("+--------------------------+-----------------------------+\n");
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
            System.out.println(IO.Yellow + "Uh Uh Unfortunately This Order Is Not Available Right Now :(" + IO.Reset);
        }
    }
}
