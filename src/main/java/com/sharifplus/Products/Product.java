package com.sharifplus.Products;

public abstract class Product {
    public String TYPE;
    public String name;
    public int[] ingredients;

    public void printIngredients() {
        for (int i =0 ;i< ingredients.length;i++) {
            System.out.println( "\033[1;34m" + ProductsList.MATERIALS[i] + "\033[0;37m" + " : " + "\033[1;33m" + ingredients[i] + "\033[0m");
        }
    }
    public String getType() {return name;}
}
