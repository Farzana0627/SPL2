package com.iit.du.currencydigitdetector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Home extends AppCompatActivity implements View.OnClickListener {

    TextView textView;
    Button bCapture, bSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        textView = (TextView) findViewById(R.id.tvGreetings);
        bCapture = (Button) findViewById(R.id.bCapture);
        bSearch = (Button) findViewById(R.id.bSearch);

        bCapture.setOnClickListener(this);
        bSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Home.this, FromTextToVoice.class);
        String str;
        String terget;

        switch (v.getId()){
            case R.id.bCapture:
                str = "Choose weather capture once or more time";
                terget = "Mode";

                intent.putExtra("msg", str);
                intent.putExtra("activity", terget);
                startActivity(intent);
                break;

            case R.id.bSearch:

                str = "Enter search key";
                terget = "Search";

                intent.putExtra("msg", str);
                intent.putExtra("activity", terget);
                startActivity(intent);
                break;
        }
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
        startActivity(new Intent(Home.this, LogIn.class));

        return true;
    }
}
