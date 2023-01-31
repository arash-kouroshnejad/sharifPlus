package com.sharifplus.Products.Foods;

import com.sharifplus.Products.Product;

public class Burger extends Product{
    public Burger() {
        TYPE = "FOOD";
        name = "Burger";
        ingredients = new int[]{0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    }
}
