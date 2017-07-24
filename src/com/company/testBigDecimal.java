package com.company;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println(("a" + "b" + "c").intern() == "abc");
        BigDecimal num = new BigDecimal("12");
        DecimalFormat format = new DecimalFormat();

        format.applyPattern("#0.###");
        System.out.println("#0.##-->"  + format.format(num));
        format.applyPattern("00.##");
        System.out.println("00.##-->"  + format.format(num));

        format.applyPattern("00.0#");
        System.out.println("00.0#-->" + format.format(num));

        format.applyPattern("00.00");
        System.out.println("00" +  format.format(num));


    }
}
