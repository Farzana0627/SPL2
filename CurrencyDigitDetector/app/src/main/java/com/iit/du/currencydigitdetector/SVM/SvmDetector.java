package com.iit.du.currencydigitdetector.SVM;

/**
 * Created by UserCP on 5/1/2016.
 */
import android.util.Log;

import org.opencv.core.Mat;
import org.opencv.ml.CvSVM;

public class SvmDetector extends AbsSvmDetector<CvSVM> {
    public SvmDetector(String knowledgeFilePath, AbsFeatureCalculator featureCalculator) {
        super(new CvSVM(), knowledgeFilePath, featureCalculator);
    }

    public float detectInternal(Mat faceMatProcessed) {
        Mat calculate = featureCalculator.calculate(faceMatProcessed);
        Log.i("feature", "calculated");
        return learner.predict(calculate);
    }
}
