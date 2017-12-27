package com.iit.du.currencydigitdetector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity /*implements View.OnClickListener*/ {
    Button bLogout;
    EditText etUserId, etEmail;
    DataStore loggedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUserId = (EditText)findViewById(R.id.etUserId);
        etEmail = (EditText)findViewById(R.id.etEmail);
        bLogout = (Button)findViewById(R.id.bLogout);

       // bLogout.setOnClickListener(this);
        loggedUser = new DataStore(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (auth()){

            Intent intent = new Intent(this, FromTextToVoice.class);
            String str = "Wellcome to currency digit detector. Please select weather capture image or search history";
            String terget = "Home";

            intent.putExtra("msg", str);
            intent.putExtra("activity", terget);
            startActivity(intent);

        }
        else{
            TextView textView = (TextView)findViewById(R.id.tvlogIn);

            Intent intent = new Intent(this, FromTextToVoice.class);
            String str = "Enter username and pasword to log in. Not resistered? Sign up bellow";
            String terget = "Login";

            intent.putExtra("msg", str);
            intent.putExtra("activity", terget);
            startActivity(intent);
        }
    }

    private boolean auth(){
        return loggedUser.getUser();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        DataStore loggedUser = new DataStore(this);
        loggedUser.clearData();
        loggedUser.setUser(false);
        return true;
    }

}
