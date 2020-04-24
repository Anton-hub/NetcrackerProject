package com.vkgroupstat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vkgroupstat.model.Group;
import com.vkgroupstat.repository.GroupRepository;
import com.vkgroupstat.vkconnection.GroupCollector;
import com.vkgroupstat.vkconnection.TEST.TEST_StringOut;
import com.vkgroupstat.vkconnection.TEST.TEST_activityParser;
import com.vkgroupstat.vkconnection.TEST.TEST_parseStat;

@RestController
@RequestMapping("/test")
public class TestController {
	
	@Autowired
	GroupCollector collector;
	@Autowired
	GroupRepository repository;
	
	@RequestMapping("/")
	public String main() {
		return "<h1>Hello in test controller</h1>";
	}
	
	@RequestMapping("/parse/{groupName}")
	public String parseWihoutDb(@PathVariable String groupName) {
		return TEST_StringOut.groupToString(collector.collect(groupName));
	}
	
	@RequestMapping("/stat")
	public String getStat() {
		return TEST_parseStat.statGet("sad");
	}
	
	@RequestMapping("/wall")
	public String getPosts() {
		return TEST_activityParser.wallGet();
	}
	
	@RequestMapping("/deleteGroup/{groupName}")
	public String testMTrep(@PathVariable String groupName) {
		Group group = repository.findByurlName(groupName);
		repository.delete(group);
		return "1";
	}
}
