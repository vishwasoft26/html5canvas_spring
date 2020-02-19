package com.jira.jiracanvas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.jira.jiracanvas.repository.ImageRepository;
import com.jira.jiracanvas.repository.ImageRepositoryFactoryImpl;
import com.jira.jiracanvas.repository.RepositoryType;

@SpringBootApplication
public class JiraCanvasApplication {

	public static void main(String[] args) {
		SpringApplication.run(JiraCanvasApplication.class, args);
	}

	/***
	 * This is factory method to create ImageRepository based on type As of now
	 * support File based image repository We can also store file into DB as blob
	 * content
	 * 
	 * @return ImageRepository
	 */
	@Bean
	public ImageRepository createImageRepository() {
		return ImageRepositoryFactoryImpl.getImageRepositoryFactory(RepositoryType.FILE);

	}

}
