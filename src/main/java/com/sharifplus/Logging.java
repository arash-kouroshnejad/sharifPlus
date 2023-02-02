package com.sharifplus;

import com.sharifplus.Authentication.User;

public class Logging {

    public static String formatError(String input) {
        User usr = User.currentUsr;
        String ouput = java.time.LocalDateTime.now() + ((usr != null) ?" - User : " + IO.Magenta + usr.name + IO.Reset
                + " Access Level : " + usr.getPrivilage() : "") + " Error : " + input + " Level : " + IO.Red + "Fatal"
                + IO.Reset + System.lineSeparator();
        return ouput;
    }

    public static String foramtInfo(String input) {
        User usr = User.currentUsr;
        String output = java.time.LocalDateTime.now() +  " " + input + ((usr != null) ? " User : " + IO.Magenta + usr.name + IO.Reset
                + " Access Level : " + usr.getPrivilage() : "") + " Level : " + IO.Blue + "Info" + IO.Reset + System.lineSeparator();
        return output;
    }
}
