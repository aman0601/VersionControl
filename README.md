# Version Control File System

This project implements a simple version control file system using Spring Boot. Unlike Git, which can be used offline, this version control system operates online and focuses on maintaining strong concurrency and read capability. The project provides APIs to perform file operations, diffing, and merging.

## Features

- Create, read, update, and delete files in the version control system.
- Perform file diffing to compare the content of two files.
- Merge changes from multiple files into a single file.

## API Endpoints

### Get File Content

GET /files/{fileName}

Retrieve the content of a file with the given file name.

### Create or Update File

POST /files/{fileName}

Create a new file with the given file name and content. If the file already exists, update its content.

### Delete File

DELETE /files/{fileName}

Delete the file with the given file name.

### Get Diff

GET /files/diff/{file1}/{file2}

Get the difference between the content of two files.

### Merge Files

GET /files/merge/{base}/{modified}/{remote}

Merge changes from the modified and remote files into the base file.

## Usage

1. Clone the repository:

git clone https://github.com/yourusername/version-control-file-system.git

2. Build the project:

cd version-control-file-system
mvn clean install

3. Run the application:

java -jar target/version-control-file-system.jar

4. Access the APIs using your preferred HTTP client (e.g., cURL, Postman).
