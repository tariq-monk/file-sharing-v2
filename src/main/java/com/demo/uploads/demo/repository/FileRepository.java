package com.demo.uploads.demo.repository;

import com.demo.uploads.demo.entity.repository.FileEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends CrudRepository<FileEntity, String>{

    List<FileEntity> findAllByOwner(String owner);

    @Query("select o from FileEntity o where :email in elements(o.sharedUsers)")
    List<FileEntity> findAllShared(String email);
}
