package com.iit.du.currencydigitdetector.detection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.iit.du.currencydigitdetector.FromTextToVoice;
import com.iit.du.currencydigitdetector.R;

public class TotalAmount extends AppCompatActivity {

    TextView t;
    Button saveButton;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_amount);
        t= (TextView) findViewById(R.id.textView);
        Bundle amountbundle = getIntent().getExtras();
        message = amountbundle.getString("amount");
        t.setText(message);

        saveButton = (Button) findViewById(R.id.bsave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TotalAmount.this, FromTextToVoice.class);
                String str = "Do you want to save it ?";
                String terget = "Save";

                String amount = message;

                intent.putExtra("msg", str);
                intent.putExtra("activity", terget);
                intent.putExtra("amount", str);

                startActivity(intent);
            }
        });
    }
}
