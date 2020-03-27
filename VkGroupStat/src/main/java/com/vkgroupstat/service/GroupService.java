package com.vkgroupstat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vkgroupstat.model.Group;
import com.vkgroupstat.repository.GroupRepository;
import com.vkgroupstat.vkconnection.*;

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
	
	
	
	//тестовые методы
	public List<Group> findAll(){
		return repository.findAll();
	}	
	public String returnSubscriptions(Integer userId) {
		return VkConnection.getUserSubsVkSdk(userId);
	}
	public String testConc(String groupName) {
		return TestConcurrent.test(groupName);
	}
	//конец тестовых
}
