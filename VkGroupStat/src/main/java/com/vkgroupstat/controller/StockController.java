package com.vkgroupstat.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vkgroupstat.model.Group;
import com.vkgroupstat.service.GroupService;
import com.vkgroupstat.vkconnection.Convertor;

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
	@RequestMapping("/")
	public String getHello() {
		return "Hello";
	}	
	@RequestMapping("/usersubsint")
	public String returnSubscriptionsInt(@RequestParam Integer userId) {
		return service.returnSubscriptionsInt(userId);
	}
	@RequestMapping("/test")
	public String test(@RequestParam String groupName) {
		return service.testMainFunctional(groupName);
	}
	@RequestMapping("/testnew")
	public String testss(@RequestParam String groupName) {
		return service.returnSubsnew(groupName);
	}
	//конец тестовых
}