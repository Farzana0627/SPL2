package machineLearning.testing;

import featureCalculator.AbsFeatureCalculator;
import matrixUtils.MatUtils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.ml.CvStatModel;
import utils.FileIoUtils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;

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

    public void test(String imageFolderPath, String imageMoodFilePath) {
        System.out.println("Starting test - ");
        try {
            String s = "";
            List<String> files = FileIoUtils.getAllFileNamesInFolder(
                    imageFolderPath, ".jpg");
            for (String file : files) {
                System.out.println("Testing " + file);
                s += file.substring(0, file.lastIndexOf('.')) + " "
                        + predict(imageFolderPath + "\\" + file) + "\n";
            }
            FileIoUtils.writeToFile(s, imageMoodFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Test ended. Results are written to "
                + imageMoodFilePath);
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
			int type= CvType.CV_8UC3;
			Mat matForImage= new Mat(300,300,type);
			matForImage.put(0, 0, pixels);
        	
        	File inputfile= new File(imagePath);
    		
			BufferedImage inputImage = ImageIO.read(inputfile);
			int size= Math.min(inputImage.getHeight(), inputImage.getWidth());
			BufferedImage resizedImage= new BufferedImage(size,size,inputImage.getType());
		    Graphics2D g2d = resizedImage.createGraphics();
		    g2d.drawImage(inputImage, 0, 0, size, size, null);
		    g2d.dispose();
		
			ImageIO.write(resizedImage, "jpg", new File("test.jpg"));
			
			
			Mat matForImage = MatUtils.getMatForImage("test.jpg");*/
        	Mat matForImage = MatUtils.getMatForImage(imagePath);
            Mat calculateHogWithOpenCv = featureCalculator.calculate(
                    MatUtils.resizeMat(matForImage, utils.Constants.imageSize));
            return calculateHogWithOpenCv;
        } catch (Exception e) {
            System.out.println("Error while reading file: " + imagePath);
            e.printStackTrace();
            throw e;
        }
    }

    public abstract float predict(String filePath) throws Exception;
}
