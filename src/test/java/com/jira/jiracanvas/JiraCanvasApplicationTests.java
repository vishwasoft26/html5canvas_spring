package com.jira.jiracanvas;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest

class JiraCanvasApplicationTests {

	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		this.mockMvc = builder.build();
	}

	@Test
	public void testFileUpload() throws IOException, Exception {
		byte b[] = null;
		try (FileInputStream fis = new FileInputStream("screen.png")){
			b = new byte[fis.available()];
			fis.read(b);
		} catch (Exception e) {
			throw e;
		}
		MockMultipartFile imageFile = new MockMultipartFile("image", "screen.png",
				"multipart/form-data; boundary= + ohaiimaboundary", b);
		imageFile.getOriginalFilename();
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.multipart("/upload/screenshot")
				.file("image", imageFile.getBytes()).characterEncoding("UTF-8")).andExpect(status().isOk());

		assertThat(response.andReturn().getResponse().getContentAsString())
				.contains("Image successfully saved screen.png");

	}

	@Test
	public void testFileUploadException() {
		Assertions.assertThrows(FileNotFoundException.class, () -> {
			byte b[] = null;
			try (FileInputStream fis = new FileInputStream("screen1.png")){
				b = new byte[fis.available()];
				fis.read(b);
			}
			MockMultipartFile imageFile = new MockMultipartFile("image", "screen.png",
					"multipart/form-data; boundary= + ohaiimaboundary", b);
			imageFile.getOriginalFilename();
			ResultActions response = mockMvc.perform(MockMvcRequestBuilders.multipart("/upload/screenshot")
					.file("image", imageFile.getBytes()).characterEncoding("UTF-8")).andExpect(status().isOk());
		});
	}

	@Test
	public void testFileUpload_BoundryException() throws IOException, Exception {
		byte b[] = null;
		try (FileInputStream fis = new FileInputStream("screen.png")){
			b = new byte[fis.available()];
			fis.read(b);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		MockMultipartFile imageFile = new MockMultipartFile("image", "screen.png", "multipart/form-data;", b);
		imageFile.getOriginalFilename();
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.multipart("/upload/screenshot")
				.file("image1", imageFile.getBytes()).characterEncoding("UTF-8"))
				.andExpect(status().is4xxClientError());
	}

	@Test
	public void testFileUpload_MissingServletRequestPartException() throws Exception {
		byte b[] = null;
		try (FileInputStream fis = new FileInputStream("screen.png")){
			b = new byte[fis.available()];
			fis.read(b);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		MockMultipartFile imageFile = new MockMultipartFile("image", "screen.png",
				"multipart/form-data; boundary= + ohaiimaboundary", b);
		imageFile.getOriginalFilename();
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.multipart("/upload/screenshot")
				.file("image", imageFile.getBytes()).characterEncoding("UTF-8")).andExpect(status().isOk());
	}

}
