package utils;

import org.opencv.core.Mat;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.*;

public final class FileIoUtils {

/*
    public static void writeHogAndMoodToFile(String moodFacePairFilePath,
                                             String imageFolderPath, String filePath) {
        Map<String, Float> moodFacePairMap = FileIoUtils
                .getMoodFacePairMap(moodFacePairFilePath);
        Set<Entry<String, Float>> moodFacePairSet = moodFacePairMap.entrySet();
        int trainSetLength = moodFacePairSet.size();
        Mat trainData = new Mat(trainSetLength, utils.Constants.dataWidth,
                CvType.CV_32FC1);
        Mat trainDataResult = new Mat(trainSetLength, 1, CvType.CV_32FC1);
        HogCalculator featureCalculator = new HogCalculator();
        int count = 0;
        for (Entry<String, Float> element : moodFacePairSet) {
            Mat hogMat;
            try {
                hogMat = featureCalculator.calculate(MatUtils
                        .resizeMat(
                                MatUtils.getMatForImage(imageFolderPath + "\\"
                                        + element.getKey() + ".jpg"),
                                utils.Constants.imageSize), utils.Constants.blockSize);
                hogMat.copyTo(trainData.row(count));
                System.out.println(trainData.row(count).dump());
                trainDataResult.put(count, 0, element.getValue());
                count++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        FileIoUtils.writeHogAndMoodToFile(trainData, trainDataResult, filePath);
    }
*/

    public static void writeHogAndMoodToFile(Mat hogMat, Mat moodMat,
                                             String filePath) {
        if (hogMat.rows() == moodMat.rows()) {
            writeToFile("", filePath);
            int height = moodMat.rows();
            for (int i = 0; i < height; i++) {
                String s = "";
                s += moodMat.get(i, 0)[0] + " ";
                for (int j = 1; j <= hogMat.row(i).cols(); j++) {
                    s += j + ":" + hogMat.get(i, j - 1)[0] + " ";
                }
                s += "\n";
                appendToFile(s, filePath);
            }
        }
    }

    public static void writeToFile(String data, String filePath) {
        FileWriter writer;
        try {
            writer = new FileWriter(filePath);
            writer.write(data);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void appendToFile(String data, String filePath) {
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(
                    new FileWriter(filePath, true)));
            out.print(data);
            out.close();
        } catch (IOException e) {
        }
    }

    public static List<String> getAllFileNamesInFolder(String folderPath,
                                                       final String fileExtension) {
        File folder = new File(folderPath);
        String[] files = folder.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(fileExtension);
            }
        });

        return Arrays.asList(files);
    }

    public static Map<String, Float> getMoodFacePairMap(
            String moodFacePairFilePath) {
        Map<String, Float> moodFacePairMap = new HashMap<String, Float>();
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(
                    moodFacePairFilePath));
            String line = "";
            while ((line = fileReader.readLine()) != null) {
                String[] splited = line.split("\\s+");
                moodFacePairMap.put(splited[0], Float.parseFloat(splited[1]));
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println(moodFacePairFilePath + " is not found.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error reading file: " + moodFacePairFilePath
                    + ". Make sure no other application is not using the file");
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error while reading file: "
                    + moodFacePairFilePath
                    + ". Make sure it is in correct format.");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Un-expected error occured while reading file: "
                    + moodFacePairFilePath);
        }

        return moodFacePairMap;
    }

    public static ArrayList<Integer> getColumnValuesFromAdaboostOutput(String filePath) {
        ArrayList<Integer> columnNumbers = new ArrayList<Integer>();
        try {
            File file = new File(filePath);
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            if (doc.hasChildNodes()) {
                columnNumbers.addAll(getColumnValuesFromAdaboostOutput(doc.getChildNodes()));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return columnNumbers;
    }


    public static ArrayList<Integer> getColumnValuesFromAdaboostOutput(NodeList nodeList) {
        ArrayList<Integer> columnNumbers = new ArrayList<Integer>();
        for (int count = 0; count < nodeList.getLength(); count++) {

            Node tempNode = nodeList.item(count);

            if (tempNode.getNodeType() == Node.ELEMENT_NODE && tempNode.getNodeName() == "column") {
                columnNumbers.add(Integer.parseInt(tempNode.getTextContent()));
            }
            if (tempNode.hasChildNodes()) {
                columnNumbers.addAll(getColumnValuesFromAdaboostOutput(tempNode.getChildNodes()));
            }
        }
        return columnNumbers;
    }

}
