package com.sharifplus.Products;

import com.sharifplus.IO;

public class NoSuchProduct extends Exception{
    public NoSuchProduct() {
        super(IO.Red + "No Scuh Product Exception" + IO.Reset);
    }
}
