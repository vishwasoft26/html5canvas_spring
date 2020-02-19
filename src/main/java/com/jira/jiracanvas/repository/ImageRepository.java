package com.jira.jiracanvas.repository;

public interface ImageRepository {
	
	/***
	 * Save content to repository location
	 * @param content
	 * @param fileName
	 * @return
	 * @throws RuntimeException
	 */
	
	public int saveFile(byte[] content, String fileName) throws RuntimeException ;
}
