package com.git.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.git.model.File;

@Service
public class VersionControlService {

	private Map<String, File> fileSystem = new HashMap<>();

	public String createOrUpdateFile(String fileName, String content) {
		File file = fileSystem.getOrDefault(fileName, new File(fileName, ""));
		file.setContent(content);
		fileSystem.put(fileName, file);
		return "File created/updated successfully";
	}

	public String deleteFile(String fileName) {
		if (fileSystem.containsKey(fileName)) {
			fileSystem.remove(fileName);
			return "File deleted successfully";
		} else {
			return "File not found";
		}
	}

	public String getDiff(String file1, String file2) {
		String content1 = getFileContent(file1);
		String content2 = getFileContent(file2);
		// Perform diffing logic here
		return "Diff between " + file1 + " and " + file2 + ":\n" + performDiff(content1, content2);
	}

	public String getFileContent(String fileName) {
		if (fileSystem.containsKey(fileName)) {
			return fileSystem.get(fileName).getContent();
		} else {
			return "File not found";
		}
	}

	public String mergeFiles(String base, String modified, String remote) {
		String baseContent = getFileContent(base);
		String modifiedContent = getFileContent(modified);
		String remoteContent = getFileContent(remote);
		// Perform merging logic here
		String mergedContent = performMerge(baseContent, modifiedContent, remoteContent);
		createOrUpdateFile(base, mergedContent); // Update base file with merged content
		return "Merge completed successfully";
	}

	private String performDiff(String content1, String content2) {
		int[][] dp = new int[content1.length() + 1][content2.length() + 1];

		// Build the DP table
		for (int i = 0; i <= content1.length(); i++) {
			for (int j = 0; j <= content2.length(); j++) {
				if (i == 0 || j == 0) {
					dp[i][j] = 0;
				} else if (content1.charAt(i - 1) == content2.charAt(j - 1)) {
					dp[i][j] = dp[i - 1][j - 1] + 1;
				} else {
					dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
				}
			}
		}

		// Traceback to find the LCS
		StringBuilder diffOutput = new StringBuilder();
		int i = content1.length(), j = content2.length();
		while (i > 0 && j > 0) {
			if (content1.charAt(i - 1) == content2.charAt(j - 1)) {
				// Characters are the same, move diagonally up-left
				i--;
				j--;
			} else if (dp[i - 1][j] >= dp[i][j - 1]) {
				// Move up if the value above is greater or equal
				diffOutput.insert(0, "Delete '" + content1.charAt(i - 1) + "' from " + i + "\n");
				i--;
			} else {
				// Move left if the value to the left is greater
				diffOutput.insert(0, "Insert '" + content2.charAt(j - 1) + "' at " + i + "\n");
				j--;
			}
		}

		// If there are remaining characters in content1 or content2, add them as
		// deletes/inserts
		while (i > 0) {
			diffOutput.insert(0, "Delete '" + content1.charAt(i - 1) + "' from " + i + "\n");
			i--;
		}
		while (j > 0) {
			diffOutput.insert(0, "Insert '" + content2.charAt(j - 1) + "' at " + i + "\n");
			j--;
		}

		return diffOutput.toString();
	}

	private String performMerge(String baseContent, String modifiedContent, String remoteContent) {
		// Split content into lines
		List<String> baseLines = Arrays.asList(baseContent.split("\\r?\\n"));
		List<String> modifiedLines = Arrays.asList(modifiedContent.split("\\r?\\n"));
		List<String> remoteLines = Arrays.asList(remoteContent.split("\\r?\\n"));

		StringBuilder mergedContent = new StringBuilder();
		int idxBase = 0, idxModified = 0, idxRemote = 0;

		// Perform merging logic
		while (idxBase < baseLines.size() || idxModified < modifiedLines.size() || idxRemote < remoteLines.size()) {
			if (idxModified < modifiedLines.size() && idxRemote < remoteLines.size()) {
				// Conflict detected, need manual intervention or some strategy to resolve
				// conflict
				if (!Objects.equals(modifiedLines.get(idxModified), remoteLines.get(idxRemote))) {
					// Here, you can implement your conflict resolution strategy.
					// For simplicity, let's just add conflict markers.
					mergedContent.append("<<<<<<< Modified\n").append(modifiedLines.get(idxModified)).append("\n")
							.append("=======\n").append(remoteLines.get(idxRemote)).append("\n")
							.append(">>>>>>> Remote\n");
					idxModified++;
					idxRemote++;
				} else {
					// No conflict, use modified version
					mergedContent.append(modifiedLines.get(idxModified)).append("\n");
					idxModified++;
					idxRemote++;
				}
			} else if (idxModified < modifiedLines.size()) {
				// No conflict, use modified version
				mergedContent.append(modifiedLines.get(idxModified)).append("\n");
				idxModified++;
			} else if (idxRemote < remoteLines.size()) {
				// No conflict, use remote version
				mergedContent.append(remoteLines.get(idxRemote)).append("\n");
				idxRemote++;
			} else if (idxBase < baseLines.size()) {
				// No conflict, use base version
				mergedContent.append(baseLines.get(idxBase)).append("\n");
				idxBase++;
			}
		}

		return mergedContent.toString();
	}
}