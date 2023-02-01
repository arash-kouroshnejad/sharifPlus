package com.sharifplus.Store;

import com.sharifplus.*;

public class Cafe extends Store{

    public Cafe() {
        super("Chocolate-Cake Ice-Cream Vanilla-Cake Coffea Hot-Chocolate Soda Tea Water");
    }

    public void getMenu() {
        String alignment = "|%-35s";
        System.out.format("+--------------------------+-----------------------------+\n");
        System.out.format("|        " + IO.Magenta + "Drinks" + IO.Reset + "          |            " + IO.Yellow + "Desserts" + IO.Reset + "           |\n");
        System.out.format("+--------------------------+-----------------------------+\n");
        for (int i = 0; i < 5 ;i++) {
            System.out.format(alignment,((storage.isAvailable(Order.find(arr[i + 3]))) ? IO.Green : IO.Red) + arr[i + 3] + IO.Reset);
            if (i < 3) {
                System.out.format(alignment,((storage.isAvailable(Order.find(arr[i]))) ? IO.Green : IO.Red) + arr[i] + IO.Reset);
            }
            System.out.print("|\n");
        }
        System.out.format("+--------------------------+-----------------------------+\n");
    }
}
