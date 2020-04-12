package com.vkgroupstat.service;

import com.vkgroupstat.model.Group;
import com.vkgroupstat.repository.GroupRepository;
import com.vkgroupstat.vkconnection.Convertor;
import com.vkgroupstat.vkconnection.GroupCollector;
import com.vkgroupstat.vkconnection.VkConnection;
import com.vkgroupstat.vkconnection.СoncurrentParse;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class GroupService {
	
	
	private final GroupRepository repository;	
	public GroupService(GroupRepository repository) {
		this.repository = repository;
	}

	public Group groupRequestHandler(String groupName) {
		Group group = repository.findBygroupName(groupName);
		if (group == null) {
			group = GroupCollector.collect(groupName);
			repository.save(group);
		}
		return group;
	}

	

	
	//тестовые методы
	public List<Group> findAll(){
		return repository.findAll();
	}
	public String returnSubsnew(String groupName) {
		return new Integer(VkConnection.getGroupVkSdk(groupName).size()).toString();
	}
	public String returnSubscriptionsInt(Integer userId) {
		return VkConnection.getUserSubsVkSdk(userId).toString();
	}
	//загрузка с игнорированием базы
	public String testMainFunctional(String groupName) {
		Date start = new Date();
		return Convertor.stringOut(
					Convertor.collect(
							new СoncurrentParse(
										VkConnection
										.getGroupVkSdk(groupName))
										.start(), 20)) + 
				"<br>" + (new Date().getTime() - start.getTime());
	}
	//конец тестовых
}
