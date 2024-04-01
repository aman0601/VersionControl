package com.git;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.git.controller.VersionControlController;
import com.git.service.VersionControlService;

@SpringBootTest
class GitServiceApplicationTests {

	@InjectMocks
	private VersionControlController versionControlController;

	@Mock
	private VersionControlService versionControlService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testCreateOrUpdateFile_Success() {
		ResponseEntity<String> response = versionControlController.createOrUpdateFile("file1.txt", "Updated content");
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals("File created/updated successfully", response.getBody());
	}

	@Test
	public void testDeleteFile_FileNotFound() {
		when(versionControlService.deleteFile(anyString())).thenReturn("File not found");
		ResponseEntity<String> response = versionControlController.deleteFile("nonexistent.txt");
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals("File not found", response.getBody());
	}

	@Test
	public void testDeleteFile_Success() {
		when(versionControlService.deleteFile("file1.txt")).thenReturn("File deleted successfully");
		ResponseEntity<String> response = versionControlController.deleteFile("file1.txt");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("File deleted successfully", response.getBody());
	}

	@Test
	public void testGetDiff_FilesExist_ReturnsDiff() {
		when(versionControlService.getDiff("file1.txt", "file2.txt")).thenReturn("Dummy diff output");
		ResponseEntity<String> response = versionControlController.getDiff("file1.txt", "file2.txt");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Dummy diff output", response.getBody());
	}

	@Test
	public void testGetFile_FileNotFound() {
		when(versionControlService.getFileContent(anyString())).thenReturn("File not found");
		ResponseEntity<String> response = versionControlController.getFile("nonexistent.txt");
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals("File not found", response.getBody());
	}

	@Test
	public void testGetFile_Success() {
		when(versionControlService.getFileContent("file1.txt")).thenReturn("Content of file1");
		ResponseEntity<String> response = versionControlController.getFile("file1.txt");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Content of file1", response.getBody());
	}

	@Test
	public void testMergeFiles_FilesExist_ReturnsMergedResult() {
		when(versionControlService.mergeFiles("base.txt", "modified.txt", "remote.txt"))
				.thenReturn("Dummy merge output");
		ResponseEntity<String> response = versionControlController.mergeFiles("base.txt", "modified.txt", "remote.txt");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Dummy merge output", response.getBody());
	}
}
