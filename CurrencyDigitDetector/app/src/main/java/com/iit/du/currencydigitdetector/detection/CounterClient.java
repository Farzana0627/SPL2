package com.iit.du.currencydigitdetector.detection;

import java.io.IOException;

/**
 * Created by naush on 03-May-16.
 */
public class CounterClient {
    private Counter soldier ;

    /**
     * this state is maintained by the client
     */
    private int currentLocationX = 0;

    /**
     * this state is maintained by the client
     */
    private int currentLocationY=0;

    public CounterClient() throws IOException {
        soldier= CounterFactory.getSoldier();
    }
}
