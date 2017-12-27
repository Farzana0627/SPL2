package com.iit.du.currencydigitdetector.SVM;

import com.iit.du.currencydigitdetector.MatrixUtilities.MatUtils;

import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.Size;
import org.opencv.objdetect.HOGDescriptor;

/**
 * Created by UserCP on 4/30/2016.
 */

public class HogCalculator extends AbsFeatureCalculator {

    private int binCount;

    public HogCalculator(int binCount) {
        this.binCount = binCount;
        horizontalBlocks = 20;
        verticalBlocks = 20;
        setTrainingParams();
    }

    public HogCalculator() {
        this(36);
    }

    private void setTrainingParams() {
        featureSpecificTrainingParams.put("g", .0001);
        featureSpecificTrainingParams.put("c", 100);
    }

    @Override
    public Mat calculate(Mat src) {
        Size blockSize = new Size(15, 15);
        return this.calculateHogWithOpenCv(src, blockSize, this.binCount);
    }

    private Mat calculateHogWithOpenCv(Mat img, Size blockSize, int binCount) {
        HOGDescriptor hogDescriptor = new HOGDescriptor(img.size(), blockSize,
                blockSize, blockSize, binCount);
        MatOfFloat descriptors = new MatOfFloat();
        hogDescriptor.compute(img,descriptors);
        return MatUtils.straightenMatrix(descriptors);
    }
}
