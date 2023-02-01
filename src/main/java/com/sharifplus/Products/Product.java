package com.sharifplus.Products;

import com.sharifplus.IO;

public abstract class Product {
    public String TYPE;
    public String name;
    public int[] ingredients;

    public void printIngredients() {
        for (int i = 0; i < ingredients.length; i++) {
            System.out.print(((ingredients[i] == 1) ? IO.Green : IO.Red) + "\t" +ProductsList.MATERIALS[i] + IO.Reset
                    + ((i % 3 == 0) ? "\n" : ""));
        }
    }

    public String getType() {
        return name;
    }
}
