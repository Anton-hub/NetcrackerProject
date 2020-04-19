package com.vkgroupstat.repository;

import com.vkgroupstat.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findByuserId(String groupName);
    User findByEmail(String email);
}