package com.demo.uploads.demo.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "com.demo.uploads")
@Data
public class FileStorageProperties {
    String directory;
}
