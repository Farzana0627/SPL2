package machineLearning.training;

import featureCalculator.AbsFeatureCalculator;
import machineLearning.TrainingParams;
import matrixUtils.MatUtils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.ml.CvStatModel;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.imageio.ImageIO;

public abstract class AbsTrainer<Trainer extends CvStatModel> {
    protected String defaultImageExtension = ".jpg";
    protected AbsFeatureCalculator featureCalculator;
    protected Trainer trainingStateModel;

    public AbsTrainer(AbsFeatureCalculator featureCalculator) {
        this.featureCalculator = featureCalculator;
    }

    protected Mat[] generateTrainningMats(Map<String, Float> moodFacePairMap, String imageFolderPath) {
        Set<Entry<String, Float>> moodFacePairSet = moodFacePairMap.entrySet();
        int trainSetLength = moodFacePairSet.size();
        System.out.println(trainSetLength);

        Mat trainDataResult = new Mat(trainSetLength, 1, CvType.CV_32FC1);

        int count = 0;
        ArrayList<Mat> featureMats = new ArrayList<Mat>();
        for (Entry<String, Float> element : moodFacePairSet) {
            try {
             //   System.out.println(element.getKey()+" "+element.getValue());
                featureMats.add(getTrainingMatForImage(imageFolderPath + "//" + element.getKey()));
                trainDataResult.put(count, 0, element.getValue());
                count++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Mat trainData = new Mat();
        Core.vconcat(featureMats, trainData);

        return new Mat[]{trainData, trainDataResult};
    }

    protected Mat getTrainingMatForImage(String imagePath) throws Exception {
        try {
        /*	File file= new File(imagePath);
			BufferedImage bi= ImageIO.read(file);
			int imagesize= Math.min(bi.getHeight(), bi.getWidth());
			BufferedImage resizedImage= new BufferedImage(imagesize,imagesize,bi.getType());
			Graphics2D g2d = resizedImage.createGraphics();
		   g2d.drawImage(bi, 0, 0, imagesize, imagesize, null);
		   g2d.dispose();
		    
			byte[] pixels = ((DataBufferByte) resizedImage.getRaster().getDataBuffer()).getData();
			//byte[] pixels = ((DataBufferByte) bi.getRaster().getDataBuffer()).getData();
		
			Mat matForImage= new Mat(imagesize,imagesize,CvType.CV_8UC3);
			matForImage.put(0, 0, pixels);
			*/
        	Mat matForImage = MatUtils.getMatForImage(imagePath);
        	
        	//Imgproc.cvtColor(matForImage, matForImage, Imgproc.COLOR_RGB2GRAY);
            return featureCalculator.calculate(MatUtils.resizeMat(matForImage, utils.Constants.imageSize));
        	} 	catch (Exception e) {
            System.out.println("Error while reading file: " + imagePath);
            e.printStackTrace();
            throw e;
        }
    }

    public void saveTrainingData(String knowledgeFilePath) {
        trainingStateModel.save(knowledgeFilePath);
        System.out.println("Training data is written to " + knowledgeFilePath);
    }

    public abstract Trainer train(TrainingParams params);
}
