package com.vkgroupstat.repository;

import com.vkgroupstat.model.Group;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends MongoRepository<Group, String>{
	public Group findBygroupName(String groupName);
}