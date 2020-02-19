package com.jira.jiracanvas.repository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.jboss.logging.Logger;

public class FileRepositoryImpl implements ImageRepository {

	Logger logger = Logger.getLogger(FileRepositoryImpl.class);
	
	//private static final String UPLOAD_FOLDER = "E:\\work\\workspace\\jira-canvas";
	private static final String UPLOAD_FOLDER = "./upload";

	@Override
	public int saveFile(byte[] content, String fileName) throws RuntimeException {

		Path path = Paths.get(UPLOAD_FOLDER);
		File parentFolder = path.toFile();
		if(!parentFolder.exists()) {
			parentFolder.mkdir();
		}
		path = path.toAbsolutePath().resolve(fileName);

		try (FileOutputStream fos = new FileOutputStream(path.toAbsolutePath().toFile())) {
			fos.write(content);
			return 0;
		} catch (IOException e1) {
			logger.error(e1.getMessage(), e1);
			throw new RuntimeException(e1.getMessage(),e1);
		}
	}
}
