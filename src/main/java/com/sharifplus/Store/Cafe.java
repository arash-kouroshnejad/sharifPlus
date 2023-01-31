package com.sharifplus.Store;

import com.sharifplus.*;

public class Cafe extends Store{

    Storage storage = App.storage;
    String Menu = "Chocolate-Cake Ice-Cream Vanilla-Cake Coffea Hot-Chocolate Soda Tea Water";

    public void getMenu() {
        String alignment = "|%-40s";
        System.out.format("+--------------------------+-----------------------------+\n");
        System.out.format("|        " + IO.Magenta + "Desserts" + IO.Reset + "          |            " + IO.Yellow + "Drinks" + IO.Reset + "       |\n");
        System.out.format("+--------------------------+-----------------------------+\n");
        String[] arr = Menu.split(" ");
        for (int i = 0; i < 4 ;i++) {
            System.out.format(alignment,((storage.isAvailable(Order.find(arr[i]))) ? IO.Green : IO.Red) + arr[i] + IO.Reset);
            if (i < 2) {
                System.out.format(alignment,((storage.isAvailable(Order.find(arr[i + 4]))) ? IO.Green : IO.Red) + arr[i + 4] + IO.Reset);
            }
            System.out.print("|\n");
        }
        System.out.format("+--------------------------+-----------------------------+\n");
    }
    public void addOrder() {

    }
}
