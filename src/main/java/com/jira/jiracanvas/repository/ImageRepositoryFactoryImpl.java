package com.jira.jiracanvas.repository;

public class ImageRepositoryFactoryImpl {

	public static ImageRepository getImageRepositoryFactory(RepositoryType type) {
		
		switch(type) {
			case FILE:
				return new FileRepositoryImpl();
			case DATABASE:
				return new DBRepositoryImpl();
			default:
				return new FileRepositoryImpl();
		}
	}
}
