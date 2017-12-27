package com.iit.du.currencydigitdetector.detection;

/**
 * Created by UserCP on 5/1/2016.
 */


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.iit.du.currencydigitdetector.FromTextToVoice;
import com.iit.du.currencydigitdetector.R;
import com.iit.du.currencydigitdetector.SVM.AbsTester;
import com.iit.du.currencydigitdetector.SVM.HogCalculator;
import com.iit.du.currencydigitdetector.SVM.SvmTester;
import com.iit.du.currencydigitdetector.SingleMode;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class DigitDetectorActivity extends AppCompatActivity {


    public static int totalamount=0;
    private Map<Float, Integer> classmap= new HashMap<>();

    String knowledgefilename="/knowledge.xml";
    ProgressBar progressBar;
    //AbsSvmDetector detector;
    String knowledgefilepath=null;
    String testimagepath=null;
    Float result=null;
    AbsTester tester;
    String testfilename = "image_001.jpg";

    TextView t;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detector_activity);
        t=(TextView) findViewById(R.id.textView);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);
     //   progressBar.setVisibility(ProgressBar.VISIBLE);


    }

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            if (status == LoaderCallbackInterface.SUCCESS ) {
                String knowledgefilepath = Environment
                        .getExternalStorageDirectory().getPath()+knowledgefilename;
                testimagepath = Environment
                        .getExternalStorageDirectory().getPath() +"/MyImages/"+ testfilename;

                tester = new SvmTester(knowledgefilepath , new HogCalculator());


                ImageAsync();
            } else {
                super.onManagerConnected(status);
            }
        }
    };

    @Override
    public void onResume() {;
        super.onResume();
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_9,this, mLoaderCallback);
        // you may be tempted, to do something here, but it's *async*, and may take some time,
        // so any opencv call here will lead to unresolved native errors.
    }
    private void setClass(){
       // Float[] array= {0.0,1.0,2.0,3.0,4.0,5.0,6.0,7.0,8.0};
        classmap.put((float)0.0,10);
        classmap.put((float)1.0,100);
        classmap.put((float)2.0,1000);
        classmap.put((float)3.0,2);
        classmap.put((float)4.0,20);
        classmap.put((float)5.0,5);
        classmap.put((float)6.0,50);
        classmap.put((float)7.0,500);
        classmap.put((float)8.0,1);

    }

    private Integer getClass(Float classvalue){
        return classmap.get((float)classvalue);
    }

    private void ImageAsync()  {
        setClass();
        try {
            result=tester.predict(testimagepath);

        } catch (Exception e) {
            e.printStackTrace();
        }

        t.setText(String.valueOf(getClass(result)));
      //  int showamount= calculateandsave(String.valueOf(getClass(result)));
        String resultstring=String.valueOf(getClass(result));
        String text="Captured image is a " + resultstring + " taka";
        progressBar.setVisibility(ProgressBar.INVISIBLE);


        Intent intent = new Intent(DigitDetectorActivity.this, FromTextToVoice.class);
        String terget = "DDA";

        intent.putExtra("msg", text);
        intent.putExtra("activity", terget);
        intent.putExtra("amount",resultstring );

        startActivity(intent);
    }


    private int calculateandsave(String amountstr){
        int newamount= Integer.parseInt(amountstr);
        totalamount+=newamount;
        return totalamount;
    }
/*
    private void ImageAsync() {
        // Now we can execute the long-running task at any time.
        setClass();
     //   Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tenone);
   /*     Uri ImageUri=getUri();
        InputStream image_stream = null;
        try {
            image_stream = getContentResolver().openInputStream(ImageUri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Bitmap bitmap= BitmapFactory.decodeStream(image_stream );*/

      //  String filename = "tw1.jpg";
     //   String path = Environment
     //           .getExternalStorageDirectory().getPath() +"/MyImages/"+ filename;
     //   BitmapFactory.Options options = new BitmapFactory.Options();
     //   options.inPreferredConfig = Bitmap.Config.ARGB_8888;
      //  Bitmap bitmap = BitmapFactory.decodeFile(path, options);

    /*    String filename = "tw3.jpg";
        String path = Environment
                .getExternalStorageDirectory().getPath() +"/MyImages/"+ filename;

        try {
            Mat m= MatUtils.getMatForImage(path);
            result=detector.detect(m);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String text=String.valueOf(getClass(result));
        progressBar.setVisibility(ProgressBar.INVISIBLE);
        t.setText(text);
    }
*/
  /*  private int getMinSize(Uri uri){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(new File(uri.getPath()).getAbsolutePath(), options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;

        return Math.min(imageHeight,imageWidth);

    }

    private Bitmap getBitmap(){
        String filename = "image_104.jpg";
        String path = Environment
                .getExternalStorageDirectory().getPath() +"/MyImages/"+ filename;
        File f = new File(path);
        Uri imageUri = Uri.fromFile(f);
        int imagesize= getMinSize(imageUri);
        ImageResize obj= new ImageResize(this, imagesize,imagesize, imageUri );
        return obj.getResizeImage();
    }
*/

    private Uri getUri(){
        String filename = "tenthree.jpg";
        String path = Environment
                .getExternalStorageDirectory().getPath() +"/MyImages/"+ filename;
        File f = new File(path);


        return Uri.fromFile(f);
    }

}
