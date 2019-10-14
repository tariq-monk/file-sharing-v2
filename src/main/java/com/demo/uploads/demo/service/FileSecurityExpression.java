/*
 * Copyright (C) 2019 wwd, LLC
 * Company Website
 * Email
 * All rights reserved
 *
 */

package com.demo.uploads.demo.service;

import com.demo.uploads.demo.entity.error.NotFoundException;
import com.demo.uploads.demo.entity.repository.FileEntity;
import com.demo.uploads.demo.repository.FileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component("fileSecurity")
public class FileSecurityExpression {

    @Autowired
    FileRepository fileRepository;

    private FileEntity findEntity(String fileId) {
        Optional<FileEntity> file = fileRepository.findById(fileId);

        if (!file.isPresent()) {
            throw new NotFoundException(fileId);
        }

        return file.get();
    }

    public boolean hasAccess(String fileId) {
        return hasAccess(findEntity(fileId));
    }

    public String getCurrentEmail() {
        return ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }

    public boolean hasAccess(FileEntity file) {
        String email = getCurrentEmail();

        boolean sharedAccess = file.getSharedUsers().stream()
            .anyMatch(sharedEmail -> Objects.equals(sharedEmail, email));

        return Objects.equals(file.getOwner(), email) || sharedAccess;
    }

    public boolean canShare(FileEntity file) {
        return Objects.equals(file.getOwner(), getCurrentEmail());
    }
}