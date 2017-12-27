package com.iit.du.currencydigitdetector.detection;

import java.io.IOException;

/**
 * Created by naush on 03-May-16.
 */
public class CounterFactory {

    private static Counter SOLDIER;

    /**
     * getFlyweight
     * @return
     * @throws IOException
     */
    public static Counter getSoldier() throws IOException {

        // this is a singleton
        // if there is no soldier
        if(SOLDIER==null){

            // create the soldier
            SOLDIER = new Counter();
        }

        // return the only soldier reference
        return SOLDIER;
        //return new SoldierImp();
    }
}
