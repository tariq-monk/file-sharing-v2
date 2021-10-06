package com.demo.uploads.demo.entity.repository;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileEntity extends BaseEntity {

    private String owner;

    @ElementCollection
    private Set<String> sharedUsers = new LinkedHashSet<>();
}
