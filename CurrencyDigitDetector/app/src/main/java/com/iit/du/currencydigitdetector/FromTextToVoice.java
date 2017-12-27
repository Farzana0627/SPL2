package com.iit.du.currencydigitdetector;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.iit.du.currencydigitdetector.detection.DigitDetectorActivity;
import com.iit.du.currencydigitdetector.detection.TotalAmount;

import java.util.Locale;

public class FromTextToVoice extends AppCompatActivity implements TextToSpeech.OnInitListener{

    private TextToSpeech textToSpeech;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_from_text_to_voice);

        textToSpeech = new TextToSpeech(this, this);
        textView = (TextView) findViewById(R.id.tvText);

        Bundle bundle = getIntent().getExtras();
        final String message = bundle.getString("msg");
        textView.setText(message);

        speakOut();

    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {

        if (status == textToSpeech.SUCCESS){

            int language = textToSpeech.setLanguage(Locale.US);
            if (language == textToSpeech.LANG_NOT_SUPPORTED || language == textToSpeech.LANG_MISSING_DATA){
                Log.e("TTS", "Language not supported");
            }
            else {

                speakOut();
                Bundle bundle = getIntent().getExtras();
                String shift = bundle.getString("activity");

                switch (shift) {

                    case "Login":
                        startActivity(new Intent(FromTextToVoice.this, LogIn.class));
                        break;

                    case "Home":
                        startActivity(new Intent(FromTextToVoice.this, Home.class));
                        break;

                    case "Resister":
                        startActivity(new Intent(FromTextToVoice.this, Resister.class));
                        break;

                    case "Mode":
                        startActivity(new Intent(FromTextToVoice.this, SelectMode.class));
                        break;

                    case "Single":
                        startActivity(new Intent(FromTextToVoice.this, SingleMode.class));
                        break;

                    case "Multi":
                        startActivity(new Intent(FromTextToVoice.this, MultiMode.class));
                        break;

                    case "Detector":
                        startActivity(new Intent(FromTextToVoice.this, DigitDetectorActivity.class));
                        break;

                    case "DDA":
                        Intent intent= new Intent(FromTextToVoice.this, SingleMode.class);
                        Bundle amountbundle = getIntent().getExtras();
                        String message = amountbundle.getString("amount");
                        intent.putExtra("amount", message);
                        startActivity(intent);
                        break;

                    case "Search":
                        startActivity(new Intent(FromTextToVoice.this, Search.class));
                        break;

                    case "Save":
                        Intent intent1 = new Intent(FromTextToVoice.this, SaveAmount.class);
                        Bundle saveAmount = getIntent().getExtras();
                        String saveTotalAmount = saveAmount.getString("amount");

                        intent1.putExtra("amount", saveTotalAmount);
                        startActivity(intent1);

                        break;
                    case "sta":
                        Intent intent2=new Intent(FromTextToVoice.this, TotalAmount.class);
                        Bundle totalamountbundle = getIntent().getExtras();
                        final String showamount = totalamountbundle.getString("amount");
                        intent2.putExtra("amount", showamount);
                        startActivity(intent2);
                        break;

                }
            }
        }
        else {
            Log.e("TTS", "Failed to start");
        }

    }

    private void speakOut() {
        String message = textView.getText().toString();
        textToSpeech.speak(message,TextToSpeech.QUEUE_FLUSH, null);
    }
}
