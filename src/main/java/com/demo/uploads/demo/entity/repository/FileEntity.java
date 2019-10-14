package com.demo.uploads.demo.entity.repository;

import lombok.Data;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Data
public class FileEntity {
    @Id
    private String id;

    private String owner;

    @ElementCollection
    private Set<String> sharedUsers = new LinkedHashSet<>();
}
