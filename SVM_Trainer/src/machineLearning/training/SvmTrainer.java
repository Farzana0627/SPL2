package machineLearning.training;

import featureCalculator.AbsFeatureCalculator;
import machineLearning.TrainingParams;
import matrixUtils.MatUtils;
import org.opencv.core.Mat;
import org.opencv.core.TermCriteria;
import org.opencv.ml.CvSVM;
import org.opencv.ml.CvSVMParams;
import utils.FileIoUtils;

import java.util.Map;

public class SvmTrainer extends AbsTrainer<CvSVM> {

	private CvSVMParams svmParams;

	public SvmTrainer(AbsFeatureCalculator featureCalculator) {
        super(featureCalculator);
		trainingStateModel = new CvSVM();
		createSvmParams();
	}

	private void createSvmParams() {
		svmParams = new CvSVMParams();
        Map<String, Object> trainingParams = featureCalculator.getFeatureSpecificTrainingParams();
        svmParams.set_svm_type(CvSVM.C_SVC);
		svmParams.set_kernel_type(CvSVM.RBF);
		svmParams.set_gamma((Double) trainingParams.get("g"));
		svmParams.set_C((Integer) trainingParams.get("c"));
		svmParams.set_term_crit(new TermCriteria(TermCriteria.EPS,
                (Integer) trainingParams.get("c"), (Double) trainingParams.get("g")));
	}

	@Override
	public CvSVM train(TrainingParams params) {
		Map<String, Float> moodFacePairMap = FileIoUtils
				.getMoodFacePairMap(params.getImageMoodFilePath());
		Mat[] trainningMats = generateTrainningMats(moodFacePairMap,
				params.getImageFolderPath());
        MatUtils.writeToFileForBsvm(params.getKnowledgeFilePath()+ "bsvm.txt", trainningMats[0], trainningMats[1]);
        trainingStateModel.train(trainningMats[0], trainningMats[1], new Mat(),
                new Mat(), svmParams);
        return trainingStateModel;
	}
}
