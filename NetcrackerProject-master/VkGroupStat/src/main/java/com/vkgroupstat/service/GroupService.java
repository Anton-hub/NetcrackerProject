package com.vkgroupstat.service;

import java.util.List;

import com.vkgroupstat.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vkgroupstat.model.Group;
import com.vkgroupstat.repository.GroupRepository;

@Service
public class GroupService {
	
	@Autowired
	private GroupRepository repository;
	
	public List<Group> findAll(){
		return repository.findAll();
	}
	
	public Group create(String firstName) {		
		return repository.save(new Group(firstName));
	}
	
	public String returnSubscribers(String groupName) {
		return repository.returnSubscribers(groupName);
	}
	
	public String returnSubscriptions(Integer userId) {
		return repository.returnSubscriptions(userId);
	}

	public Person returnSubscriptionsVk(Integer userId) {
		return repository.returnSubscriptionsVk(userId);
	}

	public String returnSubscriptionsVkWorking(Integer userId) {
		return repository.returnSubscriptionsVkWorking(userId);
	}
}
