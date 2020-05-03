package com.vkgroupstat.service;

import java.util.Date;
import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vkgroupstat.exception.NoDataAccessException;
import com.vkgroupstat.model.Group;
import com.vkgroupstat.repository.GroupRepository;

@Service
public class GroupService {
	
	private static final Logger LOG = LogManager.getLogger(GroupService.class);
	
	private final UserService service;
	private final GroupRepository repository;
	
	@Autowired
	public GroupService(UserService service, GroupRepository repository) {
		this.repository = repository;
		this.service = service;
	}

	public Group groupRequestHandler(String groupName) throws NoDataAccessException{		
		Group group = repository.findOrParse(groupName);		
		service.addGroup(group.getId());		
		if (new Date().getTime() - group.getCreateDate().getTime() > 2678400000l)
			repository.refresh(group);
		return group;
	}
	
	public LinkedList<Group> findListById(LinkedList<String> listId){
		LinkedList<Group> responseList = new LinkedList<Group>();
		for (String item : listId) {
			responseList.add(repository.findById(item));
		}		
		return responseList;
	}
}
