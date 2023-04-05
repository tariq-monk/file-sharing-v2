package com.demo.uploads.demo.repository;

import com.demo.uploads.demo.entity.repository.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    User findByEmail(String email);
    boolean existsByEmail(String email);
}
