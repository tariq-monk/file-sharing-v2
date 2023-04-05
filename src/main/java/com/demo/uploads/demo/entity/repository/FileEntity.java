package com.demo.uploads.demo.entity.repository;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "files")
public class FileEntity extends BaseEntity {

    @Column(name = "owner")
    private String owner;

    @ElementCollection
    private Set<String> sharedUsers = new LinkedHashSet<>();
}
