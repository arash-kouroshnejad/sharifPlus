package com.sharifplus.Products.Foods;

import com.sharifplus.Products.Product;

public class Pizza extends Product{
    public Pizza() {
        TYPE = "Food";
        name = "Pizza";
        ingredients = new int[]{1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0};
    }
}
