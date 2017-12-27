package com.iit.du.currencydigitdetector.SVM;

/**
 * Created by UserCP on 5/1/2016.
 */


import android.graphics.Bitmap;

import com.iit.du.currencydigitdetector.MatrixUtilities.MatUtils;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.ml.CvStatModel;

public abstract class AbsSvmDetector<Learner extends CvStatModel> {
    protected Learner learner;
    protected AbsFeatureCalculator featureCalculator;

    protected AbsSvmDetector(Learner learner, String knowledgeFilePath, AbsFeatureCalculator featureCalculator) {
        this.learner = learner;
        this.featureCalculator = featureCalculator;
        learner.load(knowledgeFilePath);
    }

    public float detect(Bitmap faceImg) {

        //int MAX_IMAGE_SIZE= Math.min(faceImg.getHeight(), faceImg.getWidth());
    //    Bitmap scaledBitmap = scaleDown(faceImg, MAX_IMAGE_SIZE, true);
      //  Mat faceMat = new Mat();


        Mat faceMat = new Mat();
        Utils.bitmapToMat(faceImg, faceMat);
        return detect(faceMat);

    }

    public float detect(Mat faceMat) {
        faceMat = MatUtils.resizeMat(faceMat, Constants.imageSize);
      //  Imgproc.cvtColor(faceMat, faceMat, Imgproc.COLOR_RGB2GRAY);
        return detectInternal(faceMat);
    }

    public Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                   boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }

    protected abstract float detectInternal(Mat faceMatProcessed);
}
