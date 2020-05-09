package com.vkgroupstat.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
	GroupCollector group;
	
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
	
	//https://spring-projects.ru/guides/uploading-files/
	//https://stackoverflow.com/questions/10186662/how-can-i-convert-poi-hssfworkbook-to-bytes
	//https://stackoverflow.com/questions/53428354/unable-to-get-excel-file-in-response-entity-in-spring-boot-rest-controller
	//https://stackoverflow.com/questions/35680932/download-a-file-from-spring-boot-rest-service
	@RequestMapping("/excel/{groupName}")
	public ResponseEntity<Resource> testExcel(@PathVariable String groupName) {
			byte[] bytes;
			try {
				bytes = excel.collect(group.collect(groupName));
			} catch (NoDataAccessException e) {
				e.printStackTrace();
				return null;
			}
			InputStreamResource resource;
			resource = new InputStreamResource(new ByteArrayInputStream(bytes));
			HttpHeaders header = new HttpHeaders();
			header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + "1");
		    return ResponseEntity.ok()
		            .headers(header)
		            .contentType(MediaType.parseMediaType("application/octet-stream"))
		            .body(resource);
	}
}
