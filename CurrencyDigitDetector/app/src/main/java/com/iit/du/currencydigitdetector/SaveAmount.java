package com.iit.du.currencydigitdetector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SaveAmount extends AppCompatActivity {

    TextView textView ;
    EditText editText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_amount);

        textView = (TextView) findViewById(R.id.satextView);
        editText = (EditText) findViewById(R.id.satext);
        button = (Button) findViewById(R.id.svbutton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mmm-yyyy");
                String date = dateFormat.format(calendar.getTime());

                Bundle bundle = getIntent().getExtras();
                String amount = bundle.getString("amount");

                String name = new DataStore(SaveAmount.this).getUserName();
                storInDatabase( name, date, amount);
            }
        });
    }

    private void storInDatabase(String name, String date, String amount) {

        User loggedUser = new User(name);
        Data data = new Data(editText.getText().toString(), date, amount);

        ServerRequest request = new ServerRequest(this);
        request.storeLogDataAsyncTask(data, loggedUser, new GetDataCallBack() {
            @Override
            public void done(Data returnedData) {

                Intent intent = new Intent(SaveAmount.this, FromTextToVoice.class);
                String str = "Amount has been stored in database";
                String terget = "Home";

                intent.putExtra("msg", str);
                intent.putExtra("activity", terget);
                startActivity(intent);

            }
        });
    }


}
