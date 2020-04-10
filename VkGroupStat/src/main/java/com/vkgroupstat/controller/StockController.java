package com.vkgroupstat.controller;

import java.util.LinkedList;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vkgroupstat.model.Group;
import com.vkgroupstat.service.GroupService;
import com.vkgroupstat.vkconnection.Convertor;
import com.vkgroupstat.vkconnection.GroupInfoParser;
import com.vkgroupstat.vkconnection.VkConnection;
import com.vkgroupstat.vkconnection.VkMultiConnection;

@RestController
public class StockController {

	private final GroupService service;		
	public StockController(GroupService service) {
		this.service = service;
	}


	@RequestMapping("/findgroup")
	public String returnSubscriptions(@RequestParam String groupName) {
		Group group = service.groupRequestHandler(groupName);
		return Convertor.groupParse(group);
	}
	
	
	
	//тестовые методы
	@RequestMapping("/testex/{groupName}")
	public String testsss(@PathVariable String groupName) {
		return VkMultiConnection.getGroupVkSdk(groupName);
	}
	@RequestMapping("/t")
	public String testsss() {
		return new GroupInfoParser().getGroupUsersInfo(0);
	}
	@RequestMapping("/")
	public String getHello() {
		return "Hello";
	}	
	@RequestMapping("/usersubsint")
	public String returnSubscriptionsInt(@RequestParam Integer userId) {
		return service.returnSubscriptionsInt(userId);
	}
	@RequestMapping("/test/{groupName}")
	public String test(@PathVariable String groupName) {
		return service.testMainFunctional(groupName);
	}
	@RequestMapping("/testnew")
	public String testss(@RequestParam String groupName) {
		return service.returnSubsnew(groupName);
	}
	//конец тестовых
}