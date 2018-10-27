package machineLearning.testing;

import featureCalculator.AbsFeatureCalculator;
import org.opencv.core.Mat;
import org.opencv.ml.CvSVM;

public class SvmTester extends AbsTester<CvSVM> {

    public SvmTester(String knowledgeFilePath, AbsFeatureCalculator featureCalculator) {
        super(new CvSVM(), featureCalculator);
        loadKnowledgeFile(knowledgeFilePath);
    }

    public float predict(String filePath) throws Exception {
        Mat hogMat = getTrainingMatForImage(filePath);
        return trainer.predict(hogMat);
    }

}
