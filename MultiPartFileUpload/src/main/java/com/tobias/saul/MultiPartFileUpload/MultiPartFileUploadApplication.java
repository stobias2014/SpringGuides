package com.tobias.saul.MultiPartFileUpload;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.tobias.saul.MultiPartFileUpload.service.StorageService;

@SpringBootApplication
@EnableConfigurationProperties
public class MultiPartFileUploadApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultiPartFileUploadApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}

}
