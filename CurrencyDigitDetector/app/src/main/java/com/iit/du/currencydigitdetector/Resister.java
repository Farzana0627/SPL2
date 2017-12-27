package com.iit.du.currencydigitdetector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RemoteViewsService;
import android.widget.TextView;

public class Resister extends AppCompatActivity implements View.OnClickListener {

    Button bSignup;
    EditText etUserId, etPassword, etEmail;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resister);

        textView = (TextView) findViewById(R.id.rtextview);
        etUserId = (EditText) findViewById(R.id.etUserId);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etEmail = (EditText) findViewById(R.id.etEmail);
        bSignup = (Button) findViewById(R.id.bSignup);

        bSignup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bSignup:
                String name = etUserId.getText().toString();
                String pwd = etPassword.getText().toString();
                String email = etEmail.getText().toString();

                User user = new User(name, pwd, email);
                resisterUser(user);
                break;
        }
    }

    private void resisterUser(User user) {
        ServerRequest serverRequest = new ServerRequest( this);
        serverRequest.storeUserDataInBackground(user, new GetUserCallBack() {
            @Override
            public void done(User returnedUser) {

                Intent intent = new Intent(Resister.this, FromTextToVoice.class);

                String str = "Resistration successfull";
                String terget = "Login";

                intent.putExtra("msg", str);
                intent.putExtra("activity", terget);
                startActivity(intent);
            }
        });
    }
}
