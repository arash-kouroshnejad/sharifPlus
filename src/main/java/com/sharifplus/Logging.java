package com.sharifplus;

import com.sharifplus.Authentication.User;

public class Logging {
    private static User usr = User.currentUsr;

    public static String formatError(String input) {
        String ouput = java.time.LocalDateTime.now() + ((usr != null) ?" - User : " + IO.Magenta + usr.name + IO.Reset
                + " Access Level : " + usr.getPrivilage() : "") + " Error : " + input + "Level : " + IO.Red + "Fatal"
                + IO.Reset + "\n";
        return ouput;
    }

    public static String foramtInfo(String input) {
        String output = java.time.LocalDateTime.now() + " -User : " + IO.Magenta + usr.name + IO.Reset
                + " Access Level : " + usr.getPrivilage() + input + "Level : " + IO.Blue + "Info" + IO.Reset + "\n";
        return output;
    }
}
