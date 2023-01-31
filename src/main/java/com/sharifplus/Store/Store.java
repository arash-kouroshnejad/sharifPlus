package com.sharifplus.Store;

import com.sharifplus.*;
import com.sharifplus.Authentication.User;
import java.util.*;

public abstract class Store {

    Storage storage = App.storage;
    LinkedList<Order> queue = App.stack;
    User usr = User.currentUsr;
    Scanner reader = App.reader;

    public abstract void getMenu();
    public abstract void addOrder();
}
