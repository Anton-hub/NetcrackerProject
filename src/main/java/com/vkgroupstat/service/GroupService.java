package com.vkgroupstat.service;

import java.util.Date;
import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.vkgroupstat.model.Group;
import com.vkgroupstat.repository.GroupRepository;
import com.vkgroupstat.vkconnection.GroupCollector;

@Service
public class GroupService {
	
	private static final Logger LOG = LogManager.getLogger(GroupService.class);
	
	private final UserService service;
	private final GroupCollector collcetor;
	private final GroupRepository repository;	
	public GroupService(UserService service, GroupRepository repository, GroupCollector collector) {
		this.repository = repository;
		this.collcetor = collector;
		this.service = service;
	}

	public Group groupRequestHandler(String groupName) {

		Group group = repository.findByurlName(groupName);
		if (group == null) {
			group = collcetor.collect(groupName);
			String groupId = repository.save(group).getId();
			service.addGroup(groupId);
		} else {
			if (new Date().getTime() - group.getCreateDate().getTime() > 2678400000l) {
				repository.delete(group);
				group = collcetor.collect(groupName);
				String groupId = repository.save(group).getId();
				service.addGroup(groupId);
			} else {
				service.addGroup(group.getId());
			}
		}
		return group;
	}
	
	public LinkedList<Group> findListById(LinkedList<String> listId){
		LinkedList<Group> responseList = new LinkedList<Group>();
		for (String item : listId) {
			responseList.add(repository.findById(item).get());
		}		
		return responseList;
	}
}
