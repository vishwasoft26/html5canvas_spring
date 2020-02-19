package com.jira.jiracanvas.repository;

import org.jboss.logging.Logger;

public class DBRepositoryImpl implements ImageRepository {
	Logger logger = Logger.getLogger(DBRepositoryImpl.class);

	@Override
	public int saveFile(byte[] content, String fileName) {
		return 1;
	}
}