package com.sharifplus.Store;

import com.sharifplus.*;

public abstract class Store {

    Storage storage = App.storage;

    public abstract void getMenu();
    public abstract void addOrder();
}
