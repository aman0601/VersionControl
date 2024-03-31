package com.git.model;

import java.util.ArrayList;
import java.util.List;

public class Folder {
	private List<File> files;
	private String name;

	public Folder(String name) {
		this.name = name;
		this.files = new ArrayList<>();
	}

	public List<File> getFiles() {
		return files;
	}

	public String getName() {
		return name;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}

	public void setName(String name) {
		this.name = name;
	}

	// Getters and setters
}