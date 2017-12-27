package com.iit.du.currencydigitdetector.SVM;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ImageResize {
  private Context mContext;
  private int mWidth;
  private int mHeight;
  private Uri mImageUri;
  private BitmapFactory.Options mBitMapOptions;
  private Bitmap mBitMap;
  private Bitmap tempBitMap;
  
  public ImageResize(Context context, int width, int height, Uri imgUri){
    this.mContext = context;
    this.mWidth = width;
    this.mHeight = height;
    this.mImageUri = imgUri;
  }
  
  public Bitmap getResizeImage(){
    ContentResolver resolver = mContext.getContentResolver();
    mBitMapOptions = new BitmapFactory.Options();
    
    if(mImageUri != null){
      ParcelFileDescriptor fd = null;
      try {
        fd = resolver.openFileDescriptor(mImageUri, "r");
        int sampleSize = 1;
        
        mBitMapOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fd.getFileDescriptor(), null, mBitMapOptions);
        
        int nextWidth = mBitMapOptions.outWidth >> 1;
        int nextHeight = mBitMapOptions.outHeight >> 1;
        
        while(nextWidth > mWidth && nextHeight > mHeight){
          sampleSize <<= 1;
          nextWidth >>= 1;
          nextHeight >>= 1;
        }
        
        mBitMapOptions.inSampleSize = sampleSize;
        mBitMapOptions.inJustDecodeBounds = false;
        
        mBitMap = BitmapFactory.decodeFileDescriptor(fd.getFileDescriptor(), null, mBitMapOptions);
        Log.d("Result","Image use Size : " +mWidth+"," +mHeight);
        Log.d("Result","Image Size : " +mBitMap.getWidth()+"," + mBitMap.getHeight());
        Log.d("Result","aa : " +(mWidth*mBitMap.getHeight())/mBitMap.getWidth());
        if(mBitMap!=null){
          if(mBitMapOptions.outWidth != mWidth || mBitMapOptions.outHeight != mHeight){ 
            //??????? :  ???? ????????  =  (???? ???????? * ?????????) / ????????? 
            tempBitMap = Bitmap.createScaledBitmap(mBitMap, mWidth, (mWidth*mBitMap.getHeight())/mBitMap.getWidth(), true);
            mBitMap.recycle();
            mBitMap = tempBitMap;
          }
        }
        
        return mBitMap;
        
      } catch (FileNotFoundException e) {
        Log.e(getClass().getSimpleName(), e.getMessage(), e);
      } finally {
          try { if(fd != null) fd.close(); } catch (IOException e) { Log.e(getClass().getSimpleName(), e.getMessage(), e);}
          if(mBitMap != null) mBitMap = null;
          if(tempBitMap != null) tempBitMap = null;
      }
    }
    return null;
  }
}