package com.vkgroupstat.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.vkgroupstat.exception.NoDataAccessException;
import com.vkgroupstat.model.Group;

@Service
public class QueueManager {
	
	@Autowired
	GroupService service;

	@Async
	public CompletableFuture<Group> getGroup(String groupName) throws NoDataAccessException {
		Group group = service.groupRequestHandler(groupName);
		return CompletableFuture.completedFuture(group);
	}
}
