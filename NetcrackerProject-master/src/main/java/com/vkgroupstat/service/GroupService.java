package com.vkgroupstat.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.vkgroupstat.model.Group;
import com.vkgroupstat.repository.GroupRepository;
import com.vkgroupstat.vkconnection.GroupCollector;

@Service
public class GroupService {

	private final UserService service;
	private final GroupRepository repository;

	public GroupService(UserService service, GroupRepository repository) {
		this.service = service;
		this.repository = repository;
	}

	public Group groupRequestHandler(String groupName) {

		Group group = repository.findByurlName(groupName);
		if (group == null) {
			group = GroupCollector.collect(groupName);
			repository.save(group);
			service.addGroup();
		} else {
			if (new Date().getTime() - group.getCreateDate().getTime() > 2678400000l) {
				repository.delete(group);
				group = GroupCollector.collect(groupName);
				repository.save(group);
				service.addGroup();
			}
		}
		return group;
	}
}
