package com.iit.du.currencydigitdetector;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Search extends AppCompatActivity {

    EditText editText;
    Button search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        editText = (EditText) findViewById(R.id.stext);
        search = (Button) findViewById(R.id.sbutton);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchHistory();
            }
        });
    }

    private void searchHistory() {

        final String name = new DataStore(this).getUserName();
        User user = new User(name);
        Data data = new Data(editText.getText().toString());
        final ServerRequest request = new ServerRequest(this);
        request.fetchLogDataInBackground(data, user, new GetDataCallBack() {
            @Override
            public void done(Data returnedData) {
                if (returnedData == null) {
                    ShowErrorMessage();
                } else {

                    Intent intent = new Intent(Search.this, FromTextToVoice.class);
                    String str = returnedData.amount + " taka is assigned against search name " + returnedData.logName;
                    String terget = "Home";

                    intent.putExtra("msg", str);
                    intent.putExtra("activity", terget);
                    startActivity(intent);


                }
            }
        });

    }
    private void ShowErrorMessage(){

        AlertDialog.Builder builder = new AlertDialog.Builder(Search.this);
        builder.setMessage("Inptu miss match");
        builder.setPositiveButton("OK", null);
        builder.show();
    }


}
