package com.vkgroupstat.controller;

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
	
	@RequestMapping("/")
	public String getHello() {
		return "Hello";
	}
	
	@RequestMapping("/all")
	public String all() {
		return service.findAll().toString();
	}
	
	@RequestMapping("/create")
	public String create(@RequestParam String firstName) {
		Group group = service.create(firstName);
		return group.toString();
	}

}