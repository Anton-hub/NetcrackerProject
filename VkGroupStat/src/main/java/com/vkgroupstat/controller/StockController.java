package com.vkgroupstat.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vkgroupstat.model.Group;
import com.vkgroupstat.service.GroupService;

@RestController
public class StockController {

	@Autowired
	GroupService service;	
	
	@RequestMapping("/findgroup")
	public String returnSubscriptions(@RequestParam String groupName) {
		Group group = service.groupRequestHandler(groupName);
		return group.getUsers().length + "<br>" + group.getGroupName() + "<br><br>" 
				+ Arrays.asList(group.getUsers()).toString();
	}
	
	
	
	//тестовые методы
	@RequestMapping("/")
	public String getHello() {
		return "Hello";
	}	
	@RequestMapping("/all")
	public String all() {
		return service.findAll().toString();
	}	
	@RequestMapping("/usersubs")
	public String returnSubscriptions(@RequestParam Integer userId) {
		return service.returnSubscriptions(userId);
	}
	@RequestMapping("/conc")
	public String testConc(@RequestParam String groupName) {
		return service.testConc(groupName);
	}
	//конец тестовых
}