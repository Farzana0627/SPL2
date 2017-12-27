package com.iit.du.currencydigitdetector;

import android.content.Context;
import android.content.Intent;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.iit.du.currencydigitdetector.detection.DigitDetectorActivity;
import com.iit.du.currencydigitdetector.detection.TotalAmount;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SingleMode extends Activity {

    private Uri fileUri;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 1;
    private Button btnCapturePicture, bProcessPicture, bTotalAmount;
    Uri uriSavedImage, image;
    int showamount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_mode);

     //   showamount+=getAmount();

        btnCapturePicture = (Button) findViewById(R.id.bCapturePicture);
        btnCapturePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File imagesFolder = new File(Environment.getExternalStorageDirectory(), "MyImages");
                imagesFolder.mkdirs();
                File image = new File(imagesFolder, "image_001.jpg");
                uriSavedImage = Uri.fromFile(image);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
                startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
            }
        });

        ListenButtonResult();
        bTotalAmount = (Button) findViewById(R.id.bShowTotalAmount);
        bTotalAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SingleMode.this, FromTextToVoice.class);
                String terget = "Sta";
                String total= String.valueOf(showamount);
                String text = "Total amount is"+ total;

                intent.putExtra("msg", text);
                intent.putExtra("activity", terget);


                startActivity(intent);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == RESULT_OK && data != null) {

                image = data.getData();

            }

        }
        /*if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {

            if (resultCode == RESULT_OK && data!=null) {
            //    processImage();
                Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
            }
            else if (resultCode == RESULT_CANCELED) {

                Toast.makeText(getApplicationContext(), "User cancelled image capture", Toast.LENGTH_SHORT).show();
                processImage();
            }
            else {
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }*/
    }

    private void processImage() {

        Intent intent = new Intent(SingleMode.this, FromTextToVoice.class);

        String str = "Captured image is processing";
        String terget = "Detector";

        intent.putExtra("msg", str);
        intent.putExtra("activity", terget);
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

        startActivity(new Intent(SingleMode.this, LogIn.class));
        return true;
    }


    private void ListenButtonResult(){
        final Context context = this;
        bProcessPicture=(Button)findViewById(R.id.bProcessPicture);


        bProcessPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, DigitDetectorActivity.class);
                startActivity(intent);

            }
        });
    }


    public int getAmount() {
        Bundle amountbundle = getIntent().getExtras();
        String message = amountbundle.getString("amount");
        int newamount=Integer.parseInt(message);
        return newamount;
    }
}
