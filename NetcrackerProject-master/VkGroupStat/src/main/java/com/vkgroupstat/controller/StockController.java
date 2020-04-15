package com.vkgroupstat.controller;

import com.vkgroupstat.model.AjaxResponseBody;
import com.vkgroupstat.model.Group;
import com.vkgroupstat.model.SearchCriteria;
import com.vkgroupstat.service.GroupService;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;





@RestController
@RequestMapping("/api")
public class StockController {
	private final GroupService service;

	public StockController(GroupService service) {
		this.service = service;
	}


		LinkedHashMap<String,Integer> myLinkedHashMap =  new LinkedHashMap<String, Integer>();

	@PostMapping("/findgroup")
	public ResponseEntity<?> getSearchResultViaAjax( @RequestBody SearchCriteria search, Errors errors) {

		AjaxResponseBody result = new AjaxResponseBody();

		if (errors.hasErrors()) {

			result.setMsg(errors.getAllErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.joining(",")));
			return ResponseEntity.badRequest().body(result);

		}
		Group group = service.groupRequestHandler(search.getGroupName());
		result.setGroup(group);
		return ResponseEntity.ok(group);

	}
	//тестовые методы


}