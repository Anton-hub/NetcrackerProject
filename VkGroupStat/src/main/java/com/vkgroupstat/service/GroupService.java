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
