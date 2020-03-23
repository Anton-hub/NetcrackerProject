package com.vkgroupstat.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.vkgroupstat.model.Group;
import com.vkgroupstat.vkconnection.VkConnection;

@Repository
public class GroupRepository {
	
	@Autowired
	private MongoTemplate mongoTemplate;

	public List<Group> findAll() {
		return mongoTemplate.findAll(Group.class);
	}

	public Group save(Group group) {
		return mongoTemplate.save(group);
	}	
		
	public String returnSubs(String groupName) {
		return VkConnection.getGroup(groupName);
	}
}
