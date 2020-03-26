package com.vkgroupstat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vkgroupstat.model.Group;
import com.vkgroupstat.repository.GroupRepository;
import com.vkgroupstat.vkconnection.GroupCollector;
import com.vkgroupstat.vkconnection.VkConnection;

@Service
public class GroupService {
	
	@Autowired
	private GroupRepository repository;	

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
	//конец тестовых
}
