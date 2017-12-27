package com.iit.du.currencydigitdetector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SelectMode extends AppCompatActivity implements View.OnClickListener {

    TextView single, multiple;
    Button bSingle, bMulti;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_mode);

        single = (TextView) findViewById(R.id.tvSingleMode);
        multiple = (TextView) findViewById(R.id.tvMultiMode);

        bSingle = (Button) findViewById(R.id.bSingleMode);
        bMulti  = (Button) findViewById(R.id.bMultiMode);

        bSingle.setOnClickListener(this);
        bMulti.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(SelectMode.this, FromTextToVoice.class);
        String str;
        String terget;

        switch (v.getId()){
            case R.id.bSingleMode:

                str = "Well, your deceision is to capture once. Place corner of paper money infront of camera to capture";
                terget = "Single";

                intent.putExtra("msg", str);
                intent.putExtra("activity", terget);
                startActivity(intent);
                break;

            case R.id.bMultiMode:

                str = "Well, your deceision is to capture multiple time. Place corner of paper money infront of camera to capture one by one";
                terget = "Multi";

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
        startActivity(new Intent(this, LogIn.class));
        return true;
    }
}
