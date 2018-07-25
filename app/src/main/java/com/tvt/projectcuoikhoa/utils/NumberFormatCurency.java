package com.tvt.projectcuoikhoa.utils;

import java.text.DecimalFormat;

public class NumberFormatCurency {


    public static String numBerForMat(int price) {

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        return decimalFormat.format(price) + " đ";
    }
}
