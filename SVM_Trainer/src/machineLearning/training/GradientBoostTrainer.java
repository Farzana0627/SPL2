package machineLearning.training;

import featureCalculator.AbsFeatureCalculator;
import machineLearning.TrainingParams;
import org.opencv.core.Mat;
import org.opencv.ml.CvGBTrees;
import org.opencv.ml.CvGBTreesParams;
import utils.FileIoUtils;

import java.util.Map;

public class GradientBoostTrainer extends AbsTrainer<CvGBTrees> {
	private CvGBTreesParams gbTreesParams;

	public GradientBoostTrainer(AbsFeatureCalculator featureCalculator) {
        super(featureCalculator);
        trainingStateModel = new CvGBTrees();
		createGbTreeParams();
	}

	private void createGbTreeParams() {
		gbTreesParams = new CvGBTreesParams();
		gbTreesParams.set_weak_count(300);
		gbTreesParams.set_max_depth(3);
		gbTreesParams.set_max_categories(5);
		gbTreesParams.set_loss_function_type(CvGBTrees.DEVIANCE_LOSS);
	}

	@Override
	public CvGBTrees train(TrainingParams params) {
		System.out.println("Training started.");
		Map<String, Float> moodFacePairMap = FileIoUtils
				.getMoodFacePairMap(params.getImageMoodFilePath());
		Mat mat[] = generateTrainningMats(moodFacePairMap,
				params.getImageFolderPath());

		trainingStateModel.train(mat[0], 1, mat[1], new Mat(), new Mat(),
				new Mat(), new Mat(), gbTreesParams, false);

		System.out.println("Training successful.");
		return trainingStateModel;
	}
}
