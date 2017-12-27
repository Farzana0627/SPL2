package com.iit.du.currencydigitdetector.detection;

/**
 * Created by naush on 03-May-16.
 */
public class Counter {

    private static Counter counterobject;
    private int totalamount=0;

    public void addCountedValue(String amount){

        totalamount+= Integer.parseInt(amount);
    }

}
