package com.tictoc.common;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileService {

	@Value("${upload.path}")
	private String fileUpload;

	public String handleSaveFile(MultipartFile upAvatar, Long id, String type) {

		String fileName = StringUtils.cleanPath(upAvatar.getOriginalFilename());
		try {
			FileCopyUtils.copy(upAvatar.getBytes(), new File(fileUpload + type + "\\" + id + "_" + fileName));

		} catch (IOException e) {
			e.printStackTrace();
		}
		return "http://localhost:8080/" + type + "/" + id + "_" + fileName;
	}

}