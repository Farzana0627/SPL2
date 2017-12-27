package com.iit.du.currencydigitdetector;

import java.util.Date;

/**
 * Created by naush on 02-May-16.
 */
public class Data {

    String logName;
    String date;
    String amount;

    public Data(String name, String date1, String amount){
        this.logName = name;
        this.date = date1;
        this.amount = amount;
    }

    public Data(String name){
        this.logName = name;
    }
}
