package com.iit.du.currencydigitdetector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MultiMode extends AppCompatActivity implements View.OnClickListener {

    private TextView intro;
    private Button bConnectSingleModeToCapture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_mode);

        intro = (TextView) findViewById(R.id.tvIntro);
        bConnectSingleModeToCapture = (Button) findViewById(R.id.bConnectSingleModeToCapture);

        bConnectSingleModeToCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MultiMode.this, Capture.class));
            }
        });

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, Capture.class);
        String str = "yes";
        intent.putExtra("Multi",str);
        startActivity(intent);
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
        startActivity(new Intent(MultiMode.this, LogIn.class));
        return true;
    }
}
