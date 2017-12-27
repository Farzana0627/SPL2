package com.iit.du.currencydigitdetector;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by naush on 26-Apr-16.
 */
public class DataStore {

    public  static final String sp_name = "userDetails";
    SharedPreferences userLocationDatabase;

    public DataStore(Context context){
        userLocationDatabase = context.getSharedPreferences(sp_name, 0);
    }

    public void storeUserData(User user){
        SharedPreferences.Editor spEditor = userLocationDatabase.edit();
        spEditor.putString("name", user.userId);
        spEditor.putString("password", user.password);
        spEditor.putString("email", user.email);

        spEditor.commit();
    }

    public User geUser(){
        String user_name = userLocationDatabase.getString("name", "");
        String pwd = userLocationDatabase.getString("password", "");
        String email = userLocationDatabase.getString("email", "");

        User loggedUser = new User(user_name, pwd, email);
        return loggedUser;
    }

    public void setUser(boolean loggedIn){
        SharedPreferences.Editor spEditor =  userLocationDatabase.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.commit();
    }

    public boolean getUser() {
        if (userLocationDatabase.getBoolean("loggedIn", false)== true)
            return true;
        else return false;
    }

    public String getUserName(){
        String user_Name = userLocationDatabase.getString("name", "");
        return user_Name;
    }

    public void clearData(){
        SharedPreferences.Editor spEditor = userLocationDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }

}
