package com.sharifplus;

public class NoSuchProduct extends Exception{
    public NoSuchProduct() {
        super(IO.Red + "No Scuh Product Exception" + IO.Reset);
    }
}
