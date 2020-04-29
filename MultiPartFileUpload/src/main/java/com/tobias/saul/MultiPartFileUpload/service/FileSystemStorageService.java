package com.tobias.saul.MultiPartFileUpload.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.tobias.saul.MultiPartFileUpload.exception.StorageFileNotFoundException;
import com.tobias.saul.MultiPartFileUpload.properties.StorageProperties;

@Service
public class FileSystemStorageService implements StorageService {

	private final Path rootLocation;

	@Autowired
	public FileSystemStorageService(StorageProperties prop) {
		this.rootLocation = Paths.get(prop.getLocation());
	}

	@Override
	public void init() {
		try {
			Files.createDirectories(rootLocation);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void store(MultipartFile file) throws StorageFileNotFoundException {
		String filename = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			if (file.isEmpty()) {
				throw new StorageFileNotFoundException("Failed to store empty file " + file.getName());
			}

			if (filename.contains("..")) {
				// This is a security check
				throw new StorageFileNotFoundException(
						"Cannot store file with relative path outside current directory " + filename);
			}

			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, this.rootLocation.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public Stream<Path> loadAll() throws StorageFileNotFoundException {
		try {
			return Files.walk(this.rootLocation, 1)
			.filter(path -> !path.equals(this.rootLocation))
			.map(this.rootLocation::relativize);
		} catch(IOException e) {
			throw new StorageFileNotFoundException("Failed to load stored files", e);
		}
		
	}

	@Override
	public Path load(String fileName) {
		return rootLocation.resolve(fileName);
	}

	@Override
	public Resource loadAsResource(String filename) throws StorageFileNotFoundException {
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			}
			else {
				throw new StorageFileNotFoundException(
						"Could not read file: " + filename);

			}
		}
		catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + filename, e);
		}
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());

	}

}
