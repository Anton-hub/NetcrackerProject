package com.vkgroupstat.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vkgroupstat.model.Group;
import com.vkgroupstat.service.GroupService;
import com.vkgroupstat.vkconnection.TEST.TEST_StringOut;

@RestController
public class StockController {

	private final GroupService service;		
	public StockController(GroupService service) {
		this.service = service;
	}

	@RequestMapping("/findgroup")
	public String returnSubscriptions(@RequestParam String groupName) {
		Group group = service.groupRequestHandler(groupName);
		return TEST_StringOut.groupToString(group);
	}
	@RequestMapping("/")
	public String returnSubscriptions() {
		return "<h1>Hello from VkGroupStat<h1>";
	}
	
}