package com.vkgroupstat.repository;

import java.util.List;

import com.vkgroupstat.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.vkgroupstat.model.Group;
import com.vkgroupstat.vkconnection.VkConnection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.vkgroupstat.model.Group;

@Repository
public interface GroupRepository extends MongoRepository<Group, String>{
	public Group findBygroupName(String groupName);
}
//package com.vkgroupstat.repository;
//
//import java.util.List;
//
//import com.vkgroupstat.model.Person;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.stereotype.Repository;
//
//import com.vkgroupstat.model.Group;
//import com.vkgroupstat.vkconnection.VkConnection;
//
//@Repository
//public class GroupRepository {
//
//	@Autowired
//	private MongoTemplate mongoTemplate;
//
//	public List<Group> findAll() {
//		return mongoTemplate.findAll(Group.class);
//	}
//
//	public Group save(Group group) {
//		return mongoTemplate.save(group);
//	}
//
//	public String returnSubscribers(String groupName) {
//		return VkConnection.getGroupVkSdk(groupName);
//	}
//
//	public String returnSubscriptions(Integer userId) {
//		return VkConnection.getUserSubs(userId);
//	}
//
//	public Person returnSubscriptionsVk(Integer userId) {
//		return VkConnection.getUserSubsVkSdk(userId);
//	}
//
//	public String returnSubscriptionsVkWorking(Integer userId) {
//		return VkConnection.getUserSubsVkSdkWorking(userId);
//	}
//
////	public String returnSubscriptionsVkWorkingAlmost(Integer userId) throws Exception{ return VkConnection.getUserSubsVkSdkWorkingAlmost(userId);
////	}
//}
