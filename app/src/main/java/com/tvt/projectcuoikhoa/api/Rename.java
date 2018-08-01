package com.tvt.projectcuoikhoa.api;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Rename {

    public static String printFirst(String s) {

        Pattern p = Pattern.compile("\\b[a-zA-Z]");
        Matcher m = p.matcher(s);
        StringBuilder builder = new StringBuilder();
        while (m.find()) {
            builder.append(m.group());
        }


        return builder.toString();
    }


}
