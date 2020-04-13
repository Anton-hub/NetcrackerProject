package com.vkgroupstat.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.vkgroupstat.model.Group;

@Repository
public interface GroupRepository extends MongoRepository<Group, String>{
	public Group findByurlName(String groupName);
}