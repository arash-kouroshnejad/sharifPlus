package com.sharifplus.Store;

import com.sharifplus.IO;
import com.sharifplus.Order;

public class Resturant extends Store{
    static String Menu = "Burger Fried-Chicken Pizza Steak French-Fries";
    public void getMenu() {
        String alignment = "|%-40s|";
        System.out.format("+--------------------------+-----------------------------+");
        System.out.format("|        " + IO.Magenta + "Food" + IO.Reset + "          |            " + IO.Yellow + "Appetizer" + IO.Reset + "       |");
        System.out.format("+--------------------------+-----------------------------+");
        Order tmp = new Order(0, null, "Add Order " + Menu);
        
    }
    public void addOrder() {

    }
}
