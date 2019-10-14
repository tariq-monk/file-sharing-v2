package com.demo.uploads.demo.service;

import com.demo.uploads.demo.config.FileStorageProperties;
import com.demo.uploads.demo.entity.dto.FileEntityDto;
import com.demo.uploads.demo.entity.dto.FileEntityListDto;
import com.demo.uploads.demo.entity.dto.FileShareRequestDto;
import com.demo.uploads.demo.entity.error.ForbiddenException;
import com.demo.uploads.demo.entity.error.NotFoundException;
import com.demo.uploads.demo.entity.repository.FileEntity;
import com.demo.uploads.demo.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FileService {

    @Autowired
    FileRepository fileRepository;

    @Autowired
    FileSecurityExpression fileSecurity;

    @Autowired
    FileStorageProperties fileStorageProperties;

    private Path fileStorageLocation;

    @PostConstruct
    protected void init() {
        fileStorageLocation = Paths.get(fileStorageProperties.getDirectory()).toAbsolutePath().normalize();

        try {
            Files.createDirectories(fileStorageLocation);
        } catch (Exception ex) {
            throw new IllegalStateException(String.format("Folder structure cannot be created at path [%s]", fileStorageLocation), ex);
        }
    }

    public FileEntityListDto findAll() {
        String currentEmail = fileSecurity.getCurrentEmail();

        List<FileEntityDto> owned = convertToDto(fileRepository.findAllByOwner(currentEmail));

        List<FileEntityDto> shared = convertToDto(fileRepository.findAllShared(currentEmail));

        return new FileEntityListDto(owned, shared);
    }

    private List<FileEntityDto> convertToDto(List<FileEntity> list) {
        return list.stream()
            .map(f -> new FileEntityDto(f.getId()))
            .collect(Collectors.toList());
    }

    public void share(FileShareRequestDto requestDto) {
        FileEntity file = fileRepository.findById(requestDto.getFileId())
            .orElseThrow(() -> new NotFoundException(requestDto.getFileId()));

        if (!fileSecurity.canShare(file)) {
            throw new ForbiddenException(file.getId());
        }

        file.getSharedUsers().add(requestDto.getEmail());
        fileRepository.save(file);
    }

    public FileEntity storeFile(MultipartFile file) throws IOException {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        String newId = UUID.randomUUID().toString();

        Path targetLocation = this.fileStorageLocation.resolve(newId);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        FileEntity fileEntity = new FileEntity();
        fileEntity.setId(newId);
        fileEntity.setOwner(fileSecurity.getCurrentEmail());

        return fileRepository.save(fileEntity);
    }

    public Resource loadResource(String id) {
        try {
            Path filePath = fileStorageLocation.resolve(id).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new NotFoundException(id);
            }
        } catch (MalformedURLException ex) {
            throw new NotFoundException(id, ex);
        }
    }
}
