package com.vkgroupstat.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vkgroupstat.export.excel.ExcelCollector;
import com.vkgroupstat.model.Group;
import com.vkgroupstat.model.SearchCriteria;
import com.vkgroupstat.service.GroupService;

@Controller
@RequestMapping("/export")
public class ExportController {

  
    private ExcelCollector excelCollector;
    private GroupService groupService;
    
    public ExportController(ExcelCollector excelCollector, GroupService groupService) {
    	this.excelCollector = excelCollector;
    	this.groupService = groupService;
	}
    
   
	@RequestMapping(value = "/downloadXLS", method = RequestMethod.GET)
	public ResponseEntity<Resource> downloadXLS( @RequestBody String groupName) {
		Group group = groupService.findGroupByName(groupName);
		byte[] bytes = excelCollector.collect(group);
		InputStreamResource resource;
		resource = new InputStreamResource(new ByteArrayInputStream(bytes));
		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + groupName + "_report.xlsx");
	    return ResponseEntity.ok()
	            .headers(header)
	            .contentType(MediaType.parseMediaType("application/octet-stream"))
	            .body(resource);
	}
//	@GetMapping("/download")
//	public String download(Model model) {
//		return "";
//	}

}
