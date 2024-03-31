package com.git.model;

import java.util.ArrayList;
import java.util.List;

public class File {
	private String content;
	private String name;
	private List<Version> versions;

	public File(String name, String content) {
		this.name = name;
		this.content = content;
		this.versions = new ArrayList<>();
	}

	public String getContent() {
		return content;
	}

	public String getName() {
		return name;
	}

	public List<Version> getVersions() {
		return versions;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setVersions(List<Version> versions) {
		this.versions = versions;
	}

	// Getters and setters
}
