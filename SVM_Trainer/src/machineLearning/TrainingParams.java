package machineLearning;

public class TrainingParams {

	private String imageMoodFilePath;
	private String imageFolderPath;
	private String knowledgeFilePath;

	public TrainingParams(String imageFolderPath, String imageMoodFilePath,
			String knowledgeFilePath) {
		this.imageFolderPath = imageFolderPath;
		this.imageMoodFilePath = imageMoodFilePath;
		this.knowledgeFilePath = knowledgeFilePath;
	}

	public String getKnowledgeFilePath() {
		return knowledgeFilePath;
	}

	public String getImageFolderPath() {
		return imageFolderPath;
	}

	public String getImageMoodFilePath() {
		return imageMoodFilePath;
	}
}
