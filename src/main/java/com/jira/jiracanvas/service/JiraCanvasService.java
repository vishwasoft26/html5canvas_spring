package com.jira.jiracanvas.service;

public interface JiraCanvasService {
	
	
	/***
	 * This is a service method to store image in persitence media
	 * @param content
	 * @param fileName
	 * @return true for success and false in case failure for unknow reason
	 * @throws RuntimeException
	 */
	public boolean saveImage(byte[] content, String fileName) throws RuntimeException;
}
