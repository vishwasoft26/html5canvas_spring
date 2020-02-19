package com.jira.jiracanvas.rest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class JiraCanvasController {

	private static final String UPLOAD_FOLDER = "E:\\work\\workspace\\jira-canvas";

	@PostMapping(value="/upload/screenshot")
	public ResponseEntity<String> saveImage(@RequestParam MultipartFile image) {
		
		  if(image ==null || image.isEmpty()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Null or empty Image successfully saved in path.");
		  
		  String fileName = "screen.png";
		  if(image.getOriginalFilename() !=null && !image.getOriginalFilename().isEmpty()) {
			  fileName = image.getOriginalFilename();
		  }
		  
		  Path path = Paths.get(".").toAbsolutePath().resolve(fileName);

		  try { 
			  byte[] content = image.getBytes();
			  try(FileOutputStream fos = new FileOutputStream(path.toAbsolutePath().toFile())) {
				  fos.write(content);
			  }
		  } catch (IOException e) {
			  e.printStackTrace(); 
			  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image successfully saved in path " +path.getRoot());
		  }
		  return ResponseEntity.ok().body("Image successfully saved in path " +path.getRoot());

	}
}
