package com.demo.uploads.demo.events;

import com.demo.uploads.demo.entity.repository.FileEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileChangesEvent extends BaseEvent{

    private FileEntity fileEntity;
    private String fileId;
    private String message;

}