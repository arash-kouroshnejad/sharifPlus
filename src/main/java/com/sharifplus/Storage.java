package com.sharifplus;

import com.sharifplus.Products.*;
import java.util.Scanner;

public class Storage {
    int[] left = new int[ProductsList.MATERIALS.length];
    Scanner reader = App.reader;

    public Storage(int[] input) {
        // POPULATE THE left ARRAY
    }

    public void updateAll(int x) {
        for (int i = 0; i < left.length; i++) {
            if (left[i] + x < 0) {
                System.out.println("Negative Left From " + IO.Red + ProductsList.MATERIALS[i] + IO.Reset);
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
            System.out.println(IO.Red + " Invalid Input !" + IO.Reset);
            return;
        }
        int[] positions = new int[parsedProducts.length];
        int[] changes = new int[parsedChange.length];
        for (int i = 0; i < parsedChange.length; i++) {
            try {
                changes[i] = Integer.parseInt(parsedChange[i]);
            } catch (NumberFormatException e) {
                System.out.println(IO.Red + " Invalid Number ! (Must Be An Integer" + IO.Reset);
                return;
            }
            outer: for (int j = 0; j < left.length; j++) {
                if (ProductsList.MATERIALS[i].equals(parsedProducts[i])) {
                    positions[i] = j;
                    continue outer;
                }
                if (j == left.length - 1) {
                    System.out.println(IO.Yellow + " Invalid Product Name " + IO.Red + parsedProducts[i] + IO.Reset);
                }
            }
        }
        for (int i = 0; i < parsedChange.length; i++) {
            left[positions[i]] += changes[i];
        }
    }

    public boolean isAvailable(Order order) {
        int size = order.products.size();
        int[] total = new int[left.length];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < left.length; j++) {
                total[j] += order.products.get(i).ingredients[j];
            }
        }
        for (int i = 0; i < left.length; i++) {
            if (total[i] > left[i]) {
                return false;
            }
        }
        order.total = total;
        return true;
    }

    public void allocate(Order order) {
        for (int i = 0; i < left.length; i++) {
            left[i] -= order.total[i];
        }
    }

    public void listResources() {
        for (int i = 0; i < left.length; i++) {
            if (left[i] == 0) {
                System.out.println(IO.Yellow + ProductsList.MATERIALS[i] + IO.Red + left[i] + IO.Reset);
            } else {
                System.out.println(IO.Yellow + ProductsList.MATERIALS[i] + IO.Green + left[i] + IO.Reset);
            }
        }
    }

    public boolean isAvailable(Product product) {
        for (int i = 0; i < left.length; i++) {
            if (product.ingredients[i] > left[i]) {
                return false;
            }
        }
        return true;
    }

    public void handle() {
        String input;
        while (true) {
            input = reader.nextLine();
            if (input.substring(0, 12).equals("List Pending")) {
                Order.list(false);
            } else if (input.substring(0, 8).equals("List All")) {
                Order.list(true);
            } else if (input.substring(0, 16).equals("Query Ware House")) {
                queryWareHouse();
            } else if (input.substring(0, 13).equals("Prepare Order")) {
                Order tmp;
                if ((tmp = getOrder(Integer.parseInt(input.split(" ")[2]))) != null) {
                    if (isAvailable(tmp)) {
                        allocate(tmp);
                        IO.PrintCheckMark();
                        System.out.println(
                                IO.Green + "Order No:" + IO.Blue + tmp.ID + IO.Green + "Has Been Served" + IO.Reset);
                    } else {
                        System.out.println(IO.Red + "Uh Uh unavailable :(" + IO.Reset);
                    }
                } else {
                    System.out.println(IO.Red + "Invalid Order Id" + IO.Reset);
                }
            } else if (input.substring(0, 10).equals("Update All")) {
                try {
                    updateAll(Integer.parseInt(input.split(" ")[2]));
                } catch (NumberFormatException e) {
                    System.out.println(IO.Red + "Invalid Input !" + IO.Reset);
                }
            } else if (input.substring(0, 7).equals("Update ") && !input.substring(7, 10).equals("All")) {
                try {
                    int i = 11;
                    while (!IO.isDigit(input.charAt(i))) {
                        i++;
                    }
                    update(input.substring(11, i - 1), input.substring(i - 1));
                } catch (NumberFormatException e) {
                    System.out.println(IO.Red + "Invalid Input !" + IO.Reset);
                }
            } else if (input.equals("History")) {

            } else if (input.substring(0, 5).equals("Check")) {
                try {
                    int orderNo = Integer.parseInt(input.substring(12));
                    Order ordr = getOrder(orderNo);
                    if (ordr == null) {
                        continue;
                    } else if (isAvailable(ordr)) {
                        System.out.println(IO.Green + "Order No :" + IO.Cyan + orderNo + "Is Available" + IO.Reset);
                    } else {
                        System.out.println(IO.Red + "Uh Uh unavailable :(" + IO.Reset);
                    }
                } catch (NumberFormatException e) {
                    System.out.println(IO.Red + "Invalid Input !" + IO.Reset);
                }
            }
            else if (input.equals("Back")) {
                return;
            }
            else {
                System.out.println(IO.Red + "Invalid Command !" +IO.Reset);
            }
        }
    }

    public void queryWareHouse() {
        for (int i = 0; i < left.length; i++) {
            if (left[i] > 10) {
                System.out.println(IO.Green + ProductsList.MATERIALS[i] + " : " + left[i] + IO.Reset);
            } else if (left[i] > 0) {
                System.out.println(IO.Yellow + ProductsList.MATERIALS[i] + " : " + left[i] + IO.Reset);
            } else {
                System.out.println(IO.Red + ProductsList.MATERIALS[i] + " : " + left[i] + IO.Reset);
            }
        }
    }

    public Order getOrder(int orderId) {
        for (Order order : App.stack) {
            if (order.ID == orderId) {
                return order;
            }
        }
        System.out.println(IO.Red + "Invalid Order Number !" + orderId + IO.Reset);
        return null;
    }
}