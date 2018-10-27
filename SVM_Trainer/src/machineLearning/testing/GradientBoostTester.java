package machineLearning.testing;

import featureCalculator.AbsFeatureCalculator;
import org.opencv.core.Mat;
import org.opencv.ml.CvGBTrees;

public class GradientBoostTester extends AbsTester<CvGBTrees> {

    public GradientBoostTester(String knowledgeFilePath, AbsFeatureCalculator featureCalculator) {
        super(new CvGBTrees(), featureCalculator);
        loadKnowledgeFile(knowledgeFilePath);
    }

    @Override
    public float predict(String filePath) throws Exception {
        Mat hogMat = getTrainingMatForImage(filePath);
        return trainer.predict(hogMat);
    }

}
