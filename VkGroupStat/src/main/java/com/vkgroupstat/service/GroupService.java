package com.vkgroupstat.service;

import java.util.List;

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
}
