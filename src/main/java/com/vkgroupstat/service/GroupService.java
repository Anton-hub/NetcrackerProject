package com.vkgroupstat.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vkgroupstat.model.Group;
import com.vkgroupstat.repository.GroupRepository;
import com.vkgroupstat.vkconnection.GroupCollector;
import com.vkgroupstat.vkconnection.GroupListCollector;
import com.vkgroupstat.vkconnection.VkConnection;
import com.vkgroupstat.vkconnection.СoncurrentParse;

@Service
public class GroupService {


	private final GroupRepository repository;
	public GroupService(GroupRepository repository) {
		this.repository = repository;
	}

	public Group groupRequestHandler(String groupName) {
		Group group = repository.findBygroupName(groupName);
		if (group == null) {
			group = GroupCollector.collector(groupName);
			repository.save(group);
		}
		return group;
	}

	public String testMainFunctional(String groupName) {
		return GroupListCollector.stringOut(
				GroupListCollector.collect(
						new СoncurrentParse(
								VkConnection
										.getGroupVkSdk(groupName))
								.start(), 20));
	}

	//тестовые методы
	public List<Group> findAll(){
		return repository.findAll();
	}
	public String returnSubsnew(String groupName) {
		return new Integer(VkConnection.getGroupVkSdk(groupName).size()).toString();
	}
	public String returnSubscriptionsInt(Integer userId) {
		return VkConnection.getUserSubsVkSdk(userId).toString();
	}
	//конец тестовых
}


//package com.vkgroupstat.service;
//
//import java.util.List;
//
//import com.vkgroupstat.model.Person;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.vkgroupstat.model.Group;
//import com.vkgroupstat.repository.GroupRepository;
//
//@Service
//public class GroupService {
//
//	@Autowired
//	private GroupRepository repository;
//
//	public List<Group> findAll(){
//		return repository.findAll();
//	}
//
//	public Group create(String firstName) {
//		return repository.save(new Group(firstName));
//	}
//
//	public String returnSubscribers(String groupName) {
//		return repository.returnSubscribers(groupName);
//	}
//
//	public String returnSubscriptions(Integer userId) {
//		return repository.returnSubscriptions(userId);
//	}
//
//	public Person returnSubscriptionsVk(Integer userId) {
//		return repository.returnSubscriptionsVk(userId);
//	}
//
//	public String returnSubscriptionsVkWorking(Integer userId) {
//		return repository.returnSubscriptionsVkWorking(userId);
//	}

//	public String returnSubscriptionsVkWorkingAlmost(Integer userId) throws Exception{ return repository.returnSubscriptionsVkWorkingAlmost(userId);
//	}
//}
