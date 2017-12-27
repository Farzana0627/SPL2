package com.iit.du.currencydigitdetector.SVM;

/**
 * Created by UserCP on 5/2/2016.
 */



import com.iit.du.currencydigitdetector.MatrixUtilities.MatUtils;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.ml.CvStatModel;

public abstract class AbsTester<Trainer extends CvStatModel> {

    protected Trainer trainer;
    protected AbsFeatureCalculator featureCalculator;
    protected Mat convolutionMat;

    protected AbsTester(Trainer trainer, AbsFeatureCalculator featureCalculator) {
        this.trainer = trainer;
        this.featureCalculator = featureCalculator;
        createConvolutionMat();
    }

    private void createConvolutionMat() {
        convolutionMat = Mat.zeros(1, 5, CvType.CV_32F);
        convolutionMat.put(0, 0, -1);
        convolutionMat.put(0, 4, 1);
    }

    protected void loadKnowledgeFile(String knowledgeFilePath) {
        trainer.load(knowledgeFilePath);
    }



    protected Mat getTrainingMatForImage(String imagePath) throws Exception {
        try {

            Mat matForImage = MatUtils.getMatForImage(imagePath);
            Mat calculateHogWithOpenCv = featureCalculator.calculate(
                    MatUtils.resizeMat(matForImage, Constants.imageSize));
            return calculateHogWithOpenCv;
        } catch (Exception e) {
            System.out.println("Error while reading file: " + imagePath);
            e.printStackTrace();
            throw e;
        }
    }

    public abstract float predict(String filePath) throws Exception;
}

