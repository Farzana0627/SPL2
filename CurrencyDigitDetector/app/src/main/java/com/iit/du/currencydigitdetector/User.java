package com.iit.du.currencydigitdetector;

/**
 * Created by naush on 25-Apr-16.
 */
public class User {

    String userId, password, email;

    public User( String uid, String pwd, String mail){
        this.userId = uid;
        this.password  = pwd;
        this.email = mail;
    }

    public User( String uid, String pwd){
        this.userId = uid;
        this.password = pwd;
    }

    public User(String uid){
        this.userId = uid;
    }
}
