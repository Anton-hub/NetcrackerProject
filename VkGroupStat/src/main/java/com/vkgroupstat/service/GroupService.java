package com.vkgroupstat.service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vkgroupstat.model.Group;
import com.vkgroupstat.repository.GroupRepository;
import com.vkgroupstat.vkconnection.*;

@Service
public class GroupService {
	
	
	private final GroupRepository repository;	
	public GroupService(GroupRepository repository) {
		this.repository = repository;
	}

	public Group groupRequestHandler(String groupName) {
		Group group = repository.findBygroupName(groupName);
		if (group == null) {
			group = GroupCollector.collector(groupName);
			repository.save(group);
		}
		return group;
	}
	
	public String testMainFunctional(String groupName) {
		
		СoncurrentParse parse = new СoncurrentParse(VkConnection.getGroupVkSdk(groupName));
		LinkedHashMap<String, Integer> map = parse.start();
		
		String response = "";
		int count = 0;
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			count++;
			response += entry.getKey() + " - " + entry.getValue() + "<br>";
			if (count > 20) {
				response += "...";
				break;
			}
				
		}
			
		return response;
	}
	
	//тестовые методы
	public List<Group> findAll(){
		return repository.findAll();
	}
	public String returnSubscriptionsInt(Integer userId) {
		return VkConnection.getUserSubsVkSdk(userId).toString();
	}
	//конец тестовых
}
