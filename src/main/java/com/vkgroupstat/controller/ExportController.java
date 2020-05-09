package com.vkgroupstat.controller;

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
    
   
	@RequestMapping(value = "/downloadXLS", method = RequestMethod.POST)
	public ResponseEntity<Resource> downloadXLS( @RequestBody SearchCriteria search) {
		Group group = groupService.findGroupByName(search.getGroupName());
		File excelFile = excelCollector.collect(group);
		InputStreamResource resource;
		try {
			resource = new InputStreamResource(new FileInputStream(excelFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			resource = null;
		}
		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + excelFile.getName());
	    return ResponseEntity.ok()
	            .headers(header)
	            .contentLength(excelFile.length())
	            .contentType(MediaType.parseMediaType("application/octet-stream"))
	            .body(resource);
	}
//	@GetMapping("/download")
//	public String download(Model model) {
//		return "";
//	}

}
