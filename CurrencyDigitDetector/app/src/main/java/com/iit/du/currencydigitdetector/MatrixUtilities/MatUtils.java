package com.iit.du.currencydigitdetector.MatrixUtilities;

/**
 * Created by UserCP on 4/30/2016.
 */

import org.opencv.core.*;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public final class MatUtils {
    public static Mat straightenMatrix(Mat mat) {
        int origWidth = mat.width();
        int origHeight = mat.height();
        MatOfFloat straightened = new MatOfFloat(new Mat(1, origHeight
                * origWidth, CvType.CV_32FC1, new Scalar(0)));
        for (int r = 0; r < origHeight; r++) {
            for (int c = 0; c < origWidth; c++) {
                straightened.put(0, r * origWidth + c, mat.get(r, c));
            }
        }
        return straightened;
    }

    public static Mat getMatForImage(String imagePath) throws Exception {
        try {
            Mat faceImg = Highgui.imread(imagePath, Highgui.IMREAD_GRAYSCALE);
            return faceImg;
        } catch (Exception e) {
            throw e;
        }
    }

    public static Mat resizeMat(Mat src, Size size) {
        Mat dst = new Mat(size, src.type());
        Imgproc.resize(src, dst, dst.size());
        return dst;
    }

    public static Mat createMatrixInitializedWith(int rowCount,
                                                  int columnCount, double val) {
        return new Mat(rowCount, columnCount, CvType.CV_32FC1, new Scalar(val));
    }

    public static Mat createMatrixWithZero(int rowCount, int columnCount) {
        return createMatrixInitializedWith(rowCount, columnCount, 0);
    }

    public static Mat transpose(Mat matrix) {
        return matrix.t();
    }

    public static Mat transpose(Mat matrix, Boolean invertSign) {
        Mat transpose = transpose(matrix);
        return invertSign ? transpose.mul(createMatrixInitializedWith(
                transpose.height(), transpose.width(), -1)) : transpose;
    }

    public static List<Float> matFC1D1ToList(Mat mat) {
        List<Float> fs = new Vector<Float>();

        fs.clear();
        int count = mat.cols();
        float[] buff = new float[count];
        mat.get(0, 0, buff);
        for (int i = 0; i < count; i++) {
            fs.add(buff[i]);
        }
        return fs;
    }

    public static Mat calculateHistogram(Mat mat, int binCount, float rangeStart, float rangeEnd) {
        MatOfInt histSize = new MatOfInt(binCount);
        MatOfFloat ranges = new MatOfFloat(rangeStart, rangeEnd);
        MatOfInt channels = new MatOfInt(0);
        Mat mask = new Mat();
        ArrayList<Mat> mats = new ArrayList<Mat>();
        mats.add(mat);
        Mat hist = new Mat();
        Imgproc.calcHist(mats, channels, mask, hist, histSize, ranges);
        return MatUtils.straightenMatrix(hist);
    }

    public static Mat getPaddedImage(Mat src, int topPad, int bottomPad, int leftPad, int rightPad, double value) {
        Mat dst = new Mat(src.rows() + topPad + bottomPad, src.cols() + leftPad + rightPad, src.type());
        Imgproc.copyMakeBorder(src, dst, topPad, bottomPad, leftPad, rightPad, Imgproc.BORDER_CONSTANT, new Scalar(value));
        return dst;
    }

    public static Mat getPaddedImage(Mat src, int pad) {
        return getPaddedImage(src, pad, pad, pad, pad, 0);
    }

    public static Mat getPaddedImage(Mat src, int pad, double value) {
        return getPaddedImage(src, pad, pad, pad, pad, value);
    }

    public static void writeToFileForBsvm(String filePath, Mat feature, Mat classes) {
        FileWriter writer;
        try {
            writer = new FileWriter(filePath);
            int rows = feature.rows();

            for (int r = 0; r < rows; r++) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(classes.get(r, 0)[0] + " ");
                Mat currentRow = feature.row(r);
                int cols = currentRow.cols();

                for (int c = 0; c < cols; c++) {
                    stringBuilder.append((c + 1) + ":" + feature.get(r, c)[0] + " ");
                }

                stringBuilder.append("\n");
                writer.append(stringBuilder.toString());
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
