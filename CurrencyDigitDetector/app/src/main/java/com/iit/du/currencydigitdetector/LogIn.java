package com.iit.du.currencydigitdetector;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LogIn extends AppCompatActivity implements View.OnClickListener {

    Button bLogin;
    EditText etUserId, etPassword;
    TextView tvSignUpLink, textView;
    DataStore userData;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        etUserId = (EditText) findViewById(R.id.etUserId);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bLogin = (Button) findViewById(R.id.bLogin);
        tvSignUpLink = (TextView) findViewById(R.id.tvSignUpLink);
        textView = (TextView)findViewById(R.id.tlogin);

        bLogin.setOnClickListener(this);
        tvSignUpLink.setOnClickListener(this);

        userData = new DataStore(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.bLogin:

                String name = etUserId.getText().toString();
                String pwd = etPassword.getText().toString();
                User user = new User(name, pwd);
                
                Authenticate(user);
                break;

            case R.id.tvSignUpLink:

                Intent intent = new Intent(LogIn.this, FromTextToVoice.class);
                String str;
                String terget;

                str = "Please enter user id, password and email address to resister";
                terget = "Resister";

                intent.putExtra("msg", str);
                intent.putExtra("activity", terget);
                startActivity(intent);
                break;
        }
    }

    private void Authenticate(User user) {
        ServerRequest serverRequest = new ServerRequest(this);
        serverRequest.fetchUserDataInBackground(user, new GetUserCallBack() {
            @Override
            public void done(User returnedUser) {
                if (returnedUser == null){
                    ShowErrorMessage();
                }
                else{
                    logUserIn(returnedUser);
                }
            }
        });
    }

    private void ShowErrorMessage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(LogIn.this);
        builder.setMessage("Inptu miss match");
        builder.setPositiveButton("OK", null);
        builder.show();
    }

    private void logUserIn(User returnedUser) {
        userData.storeUserData(returnedUser);
        userData.setUser(true);

        Intent intent = new Intent(LogIn.this, FromTextToVoice.class);
        String str;
        String terget;

        str = "You have successfully logged in. Choose weather capture money or search history";
        terget = "Home";

        intent.putExtra("msg", str);
        intent.putExtra("activity", terget);
        startActivity(intent);
    }
}
