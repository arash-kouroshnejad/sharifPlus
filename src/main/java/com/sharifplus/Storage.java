package com.sharifplus;

import com.sharifplus.Products.*;
import java.util.Scanner;

public class Storage {
    int[] left = new int[ProductsList.MATERIALS.length];
    Scanner reader = App.reader;

    public Storage(int[] input) {
        // POPULATE THE left ARRAY
        left = input;
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
        outer: 
        for (int i = 0; i < parsedChange.length; i++) {
            changes[i] = Integer.parseInt(parsedChange[i]);
            for (int j = 0; j < left.length; j++) {
                if (ProductsList.MATERIALS[j].equals(parsedProducts[i])) {
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
        if (order.isComplete()) {
            IO.printError("Order Has Been Served Already !!");
        }
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
            System.out.println("Available Commands : \n" + IO.Yellow
                    + "\t -List Pending \t\t\t\t\t -List All\n"
                    + "\t -Query Ware House \t -Update All change (must be typed with +-)\n"
                    + "\t -Update product1 Product2 ... change1 change2 ... (must be typed with +-)\n"
                    + "\t -Prepare Order OrderID \t\t\t -Cancel Order OrderID\n" 
                    + "\t -Chceck Order OrderID \t\t\t\t\t -Back" + IO.Reset);
            input = reader.nextLine();
            int length = input.length();
            if (length >= 12 && input.substring(0, 12).equals("List Pending")) {
                Order.list(false);
            } else if (length >= 8 && input.substring(0, 8).equals("List All")) {
                Order.list(true);
            } else if (length >= 16 && input.substring(0, 16).equals("Query Ware House")) {
                queryWareHouse();
            } else if (length >= 16 && input.substring(0, 13).equals("Prepare Order")) {
                Order tmp;
                if ((tmp = getOrder(Integer.parseInt(input.split(" ")[2]))) != null) {
                    if (!tmp.isComplete() && isAvailable(tmp)) {
                        allocate(tmp);
                        IO.PrintCheckMark();
                        System.out.println(
                                IO.Green + "Order No: " + IO.Blue + tmp.ID + IO.Green + " Has Been Served" + IO.Reset);
                        tmp.Terminate();
                    } else if (tmp.isComplete()) {
                        System.out.println(IO.Yellow + "Order Has Been Served Already" + IO.Reset);
                    } else {
                        IO.printError("Uh Uh unavailable :(");
                    }
                } else {
                    IO.printError("Invalid Order Id");
                }
            } else if (length >= 10 && input.substring(0, 10).equals("Update All")) {
                try {
                    updateAll(Integer.parseInt(input.split(" ")[2]));
                } catch (NumberFormatException | IndexOutOfBoundsException e) {
                    IO.printError("Invalid Input !");
                }
            } else if (length >= 10 && input.substring(0, 7).equals("Update ")
                    && !input.substring(7, 10).equals("All")) {
                try {
                    int i = 7;
                    while (i < length && !IO.isDigit(input.charAt(i))) {
                        i++;
                    }
                    update(input.substring(7, i - 1), input.substring(i).strip());
                } catch (NumberFormatException | IndexOutOfBoundsException a) {
                    IO.printError("Invalid Input !!!");
                }
            } else if (length >= 12 && input.substring(0, 12).equals("Cancel Order")) {
                try {
                    int orderNo = Integer.parseInt(input.substring(13));
                    Order order = getOrder(orderNo);
                    if (order == null) {
                        IO.printError("Invalid Order Id !");
                    } else if (order.isComplete()) {
                        IO.printError("Order Has Been Served !");
                    } else {
                        order.Terminate();
                        IO.PrintCheckMark();
                        System.out.println(IO.Green + "Order " + IO.Blue + order.ID + IO.Green + "Has Been Cancelled !" + IO.Reset);
                    }
                } catch (NumberFormatException | IndexOutOfBoundsException e) {
                    IO.printError("Invalid Command !");
                }
            } else if (length >= 5 && input.substring(0, 5).equals("Check")) {
                try {
                    int orderNo = Integer.parseInt(input.substring(12));
                    Order ordr = getOrder(orderNo);
                    if (ordr == null) {
                        continue;
                    } else if (isAvailable(ordr)) {
                        System.out.println(
                                IO.Green + "Order No :" + IO.Cyan + orderNo + IO.Green + " Is Available" + IO.Reset);
                    } else {
                        IO.printError("Uh Uh unavailable :(");
                    }
                } catch (NumberFormatException | IndexOutOfBoundsException e) {
                    IO.printError("Invalid Input !");
                }
            } else if (input.equals("Back")) {
                return;
            } else {
                IO.printError("Invalid Command !");
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