package com.tobias.saul.MultiPartFileUpload.service;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.tobias.saul.MultiPartFileUpload.exception.StorageFileNotFoundException;

public interface StorageService {
	
	void init();
	
	void store(MultipartFile file) throws StorageFileNotFoundException;
	
	Stream<Path> loadAll() throws StorageFileNotFoundException;
	
	Path load(String fileName);
	
	Resource loadAsResource(String fileName) throws StorageFileNotFoundException;
	
	void deleteAll();

}
