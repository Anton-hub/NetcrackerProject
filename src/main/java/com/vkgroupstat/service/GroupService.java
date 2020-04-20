package com.vkgroupstat.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.vkgroupstat.model.Group;
import com.vkgroupstat.repository.GroupRepository;
import com.vkgroupstat.vkconnection.GroupCollector;

@Service
public class GroupService {
	
	private final GroupCollector collcetor;
	private final GroupRepository repository;	
	public GroupService(GroupRepository repository, GroupCollector collector) {
		this.repository = repository;
		this.collcetor = collector;
	}

	public Group groupRequestHandler(String groupName) {
		Group group = repository.findByurlName(groupName);
		if (group == null) {
			group = collcetor.collect(groupName);
			repository.save(group);
		} else {
			if (new Date().getTime() - group.getCreateDate().getTime() > 2678400000l) {
				repository.delete(group);
				group = collcetor.collect(groupName);
				repository.save(group);
			}
		}
		return group;
	}
}
