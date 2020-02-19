package com.jira.jiracanvas.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jira.jiracanvas.service.JiraCanvasService;

@RestController
public class JiraCanvasController {

	@Autowired JiraCanvasService service;
	
	@PostMapping(value="/upload/screenshot")
	public ResponseEntity<String> saveImage(@RequestParam MultipartFile image) {
		
		  if(image ==null || image.isEmpty()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Null or empty Image successfully saved in path.");
		  
		  String fileName = "screen.png";
		  if(image.getOriginalFilename() !=null && !image.getOriginalFilename().isEmpty()) {
			  fileName = image.getOriginalFilename();
		  }
		  
		  try { 
			  boolean status = service.saveImage(image.getBytes(), fileName);
			  if(status) {
				  return ResponseEntity.ok().body("Image successfully saved " +fileName);
			  } else {
				  return ResponseEntity.ok().body("There is some issue with saving file " +fileName);
			  }
		  } catch (Exception e) {
			  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image successfully saved in path " +e.getMessage());
		  }
		  

	}
}
