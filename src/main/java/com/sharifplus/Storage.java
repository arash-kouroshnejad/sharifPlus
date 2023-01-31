package com.sharifplus;

import com.sharifplus.Products.ProductsList;
import com.sharifplus.Products.Product;

public class Storage {
    public int[] left = new int[ProductsList.MATERIALS.length];

    public Storage(int[] input) {
        // POPULATE THE left ARRAY
    }

    public void updateAll(int x) {
        for (int i = 0; i < left.length; i++) {
            if (left[i] + x < 0) {
                System.out.println("Negative Left From " + ProductsList.MATERIALS[i]);
                return;
            }
        }
        for (int i = 0; i < left.length; i++) {
            left[i] += x;
        }
    }

    public void update(String products, String change) {
        String[] parsedProducts = products.split(" ");
        String[] parsedChange = change.split(" ");
        if (parsedChange.length != parsedProducts.length) {
            System.out.println(" Invalid Input !");
            return;
        }
        int[] positions = new int[parsedProducts.length];
        int[] changes = new int[parsedChange.length];
        for (int i = 0; i < parsedChange.length; i++) {
            try {
                changes[i] = Integer.parseInt(parsedChange[i]);
            } catch (NumberFormatException e) {
                System.out.println(" Invalid Number ! (Must Be An Integer");
                return;
            }
            outer:
            for (int j = 0; j < left.length; j++) {
                if (ProductsList.MATERIALS[i].equals(parsedProducts[i])) {
                    positions[i] = j;
                    continue outer;
                }
                if (j == left.length - 1){
                    System.out.println(IO.Yellow + " Invalid Product Name " + IO.Red + parsedProducts[i] + IO.Reset);
                }
            }
        }
        for (int i = 0; i < parsedChange.length; i++) {
            left[positions[i]] += changes[i];
        }
    }

    public boolean isAvailable(Order order) {
        
    }
}