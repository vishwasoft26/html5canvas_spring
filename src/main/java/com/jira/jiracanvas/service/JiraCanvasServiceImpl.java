package com.jira.jiracanvas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jira.jiracanvas.repository.ImageRepository;

@Service
public class JiraCanvasServiceImpl implements JiraCanvasService {

	@Autowired ImageRepository repository;
	
	@Override
	public boolean saveImage(byte[] content, String fileName) throws RuntimeException {
		
		return repository.saveFile(content, fileName) == 0;
	}

}
