package com.vkgroupstat.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.vkgroupstat.controller.WebController;
import com.vkgroupstat.exception.NoDataAccessException;
import com.vkgroupstat.model.Group;
import com.vkgroupstat.model.User;
import com.vkgroupstat.vkconnection.GroupCollector;

@Repository
public class GroupRepository {

	private final MongoTemplate mongoTemplate;
	private final GroupCollector collector;
	
	@Autowired
	public GroupRepository(MongoTemplate mongoTemplate, GroupCollector collcetor) {
		this.mongoTemplate = mongoTemplate;
		this.collector = collcetor;
	}
	
	public Group save(Group group) {
		return mongoTemplate.save(group, "group");
	}
	
	public void delete(Group group) {
		mongoTemplate.remove(group, "group");
	}
	
	public Group refresh(Group group) throws NoDataAccessException{
		String id = group.getId();
		group = collector.collect(group.getUrlName());
		group.setId(id);
		save(group);
		return group;
	}
	
	public Group findOrParse(String groupName) throws NoDataAccessException{
		Group group = findByurlName(groupName);
		if (group == null) {
			group = collector.collect(groupName);
			save(group);
		}
		return group;
	}
	
	public Group findById(String groupId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("Id").is(groupId));		
		return mongoTemplate.findOne(query, Group.class);
	}
	
	public Group findByurlName(String groupName) {
		Query query = new Query();
		query.addCriteria(Criteria.where("urlName").is(groupName));
		return mongoTemplate.findOne(query, Group.class);		 
	}
}