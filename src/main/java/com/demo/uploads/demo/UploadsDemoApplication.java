package com.demo.uploads.demo;

import com.demo.uploads.demo.config.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableConfigurationProperties({
	FileStorageProperties.class
})
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableJpaRepositories
public class UploadsDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(UploadsDemoApplication.class, args);
	}

}
