package com.vkgroupstat.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vkgroupstat.vkconnection.GroupCollector;
import com.vkgroupstat.vkconnection.TEST.TEST_StringOut;
import com.vkgroupstat.vkconnection.TEST.TEST_parseStat;

@RestController
@RequestMapping("/test")
public class TestController {
	
	@RequestMapping("/")
	public String main() {
		return "<h1>Hello in test controller</h1>";
	}
	
	@RequestMapping("/parse/{groupName}")
	public String parseWihoutDb(@PathVariable String groupName) {
		return TEST_StringOut.groupToString(GroupCollector.collect(groupName));
	}
	
	@RequestMapping("/stat")
	public String getStat() {
		return TEST_parseStat.statGet("sad");
	}
}
