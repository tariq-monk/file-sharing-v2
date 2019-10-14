package com.demo.uploads.demo.controller;

import com.demo.uploads.demo.entity.dto.FileEntityListDto;
import com.demo.uploads.demo.entity.dto.FileShareRequestDto;
import com.demo.uploads.demo.entity.dto.FileUploadResponseDto;
import com.demo.uploads.demo.entity.repository.FileEntity;
import com.demo.uploads.demo.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping("/file")
    public FileEntityListDto getAll() {
        return fileService.findAll();
    }

    @GetMapping("/file/{id}")
    @PreAuthorize("@fileSecurity.hasAccess(#id)")
    public ResponseEntity<Resource> get(@PathVariable String id) {
        return ResponseEntity.ok(fileService.loadResource(id));
    }

    @PostMapping("/file")
    public ResponseEntity<FileUploadResponseDto> handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        FileEntity fileEntity = fileService.storeFile(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(FileUploadResponseDto.builder().id(fileEntity.getId()).build());
    }

    @PostMapping("/share")
    public ResponseEntity<String> share(@Valid @RequestBody FileShareRequestDto requestDto) {
        fileService.share(requestDto);

        return ResponseEntity.ok().build();
    }
}
