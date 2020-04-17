package com.vkgroupstat.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.vkgroupstat.model.Group;
import com.vkgroupstat.repository.GroupRepository;
import com.vkgroupstat.vkconnection.GroupCollector;

@Service
public class GroupService {
	
	
	private final GroupRepository repository;	
	public GroupService(GroupRepository repository) {
		this.repository = repository;
	}

	public Group groupRequestHandler(String groupName) {
		Group group = repository.findByurlName(groupName);
		if (group == null) {
			group = GroupCollector.collect(groupName);
			repository.save(group);
		} else {
			if (new Date().getTime() - group.getCreateDate().getTime() > 2678400000l) {
				repository.delete(group);
				group = GroupCollector.collect(groupName);
				repository.save(group);
			}
		}
		return group;
	}
}
