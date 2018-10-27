package main;

import featureCalculator.HogCalculator;
import featureCalculator.LbpCalculator;
import featureCalculator.WeightedLbpCalculator;
import machineLearning.TrainingParams;
import machineLearning.testing.AbsTester;
import machineLearning.testing.SvmTester;
import machineLearning.training.AbsTrainer;
import machineLearning.training.SvmTrainer;

import java.util.HashMap;
import java.util.Map;

import org.opencv.core.Core;

public class Main {
	public static void main(String[] args) throws Exception {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		trainAll();
	}

	private static void trainAll() throws Exception {
		//train
		String imageFolderPath = "H://Workspace//Mood Detector Trainer//Taka";
		String imageMoodFilePath = "pairedfile.txt";
		String outputPath = "H://Workspace//Mood Detector Trainer//";
		TrainingParams hogTrainingParams = new TrainingParams(imageFolderPath,
				imageMoodFilePath, outputPath + "//knowledge.xml");
	
	//	AbsTrainer<?> trainer = new SvmTrainer(new HogCalculator());
	//	trainer.train(hogTrainingParams);
	//	trainer.saveTrainingData(hogTrainingParams.getKnowledgeFilePath());
		
		//test
		Map<Float, Integer> classmap= new HashMap<>();
		       // Float[] array= {0.0,1.0,2.0,3.0,4.0,5.0,6.0,7.0,8.0};
		        classmap.put((float)0.0,10);
		        classmap.put((float)1.0,100);
		        classmap.put((float)2.0,1000);
		        classmap.put((float)3.0,2);
		        classmap.put((float)4.0,20);
		        classmap.put((float)5.0,5);
		        classmap.put((float)6.0,50);
		        classmap.put((float)7.0,500);
		        classmap.put((float)8.0,1);

	
		String testfilepath="H://Workspace//Mood Detector Trainer//Test//fivetwo.jpg";
		AbsTester<?> tester= new SvmTester(outputPath + "//knowledge.xml", new HogCalculator());
		
		float f = tester.predict(testfilepath);
		
		System.out.println(classmap.get(f));
	}
}
