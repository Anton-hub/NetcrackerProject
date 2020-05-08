package com.vkgroupstat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vkgroupstat.TEST.TEST_ParseGroupWithoutDB;
import com.vkgroupstat.TEST.TEST_activityParser;
import com.vkgroupstat.TEST.TEST_parseStat;
import com.vkgroupstat.TEST.exprort.TryToExport;
import com.vkgroupstat.exception.NoDataAccessException;
import com.vkgroupstat.export.excel.ExcelCollector;
import com.vkgroupstat.model.Group;
import com.vkgroupstat.repository.GroupRepository;
import com.vkgroupstat.vkconnection.GroupCollector;

@RestController
@RequestMapping("/test")
public class TestController {
	
	@Autowired
	GroupRepository repository;
	@Autowired
	ExcelCollector excel;
	@Autowired
	GroupCollector collect;
	
	@RequestMapping("/")
	public String main() {
		return "<h1>Hello in test controller</h1>";
	}
	
	@RequestMapping("/parse/{groupName}")
	public String parseWihoutDb(@PathVariable String groupName) {
		try {
			return TEST_ParseGroupWithoutDB.test(groupName);
		} catch (NoDataAccessException e) {
			return e.toString();
		}
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
	@RequestMapping("/excel/{groupName}")
	public void testExcel(@PathVariable String groupName) {
		try {
			excel.collect(collect.collect(groupName));
		} catch (NoDataAccessException e) {
			System.err.println(e);
		}
	}
}
