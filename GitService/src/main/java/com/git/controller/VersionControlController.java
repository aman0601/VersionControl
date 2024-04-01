package com.git.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.git.service.VersionControlService;

@RestController
@RequestMapping("/files")
public class VersionControlController {

	@Autowired
	private VersionControlService versionControlService;

	@PostMapping("/{fileName}")
	public ResponseEntity<String> createOrUpdateFile(@PathVariable String fileName, @RequestBody String content) {
		versionControlService.createOrUpdateFile(fileName, content);
		return ResponseEntity.status(HttpStatus.CREATED).body("File created/updated successfully");
	}

	@DeleteMapping("/{fileName}")
	public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
		String result = versionControlService.deleteFile(fileName);
		if (result.equals("File not found")) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
		} else {
			return ResponseEntity.ok(result);
		}
	}

	@GetMapping("/diff/{file1}/{file2}")
	public ResponseEntity<String> getDiff(@PathVariable String file1, @PathVariable String file2) {
		String diff = versionControlService.getDiff(file1, file2);
		return ResponseEntity.ok(diff);
	}

	@GetMapping("/{fileName}")
	public ResponseEntity<String> getFile(@PathVariable String fileName) {
		String content = versionControlService.getFileContent(fileName);
		if (content.equals("File not found")) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(content);
		} else {
			return ResponseEntity.ok(content);
		}
	}

	@GetMapping("/merge/{base}/{modified}/{remote}")
	public ResponseEntity<String> mergeFiles(@PathVariable String base, @PathVariable String modified,
			@PathVariable String remote) {
		String result = versionControlService.mergeFiles(base, modified, remote);
		return ResponseEntity.ok(result);
	}
}
