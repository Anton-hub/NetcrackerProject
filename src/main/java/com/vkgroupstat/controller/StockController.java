package com.vkgroupstat.controller;
import com.vkgroupstat.model.AjaxResponseBody;
import com.vkgroupstat.model.Group;
import com.vkgroupstat.model.SearchCriteria;
import com.vkgroupstat.service.GroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import static com.vkgroupstat.vkconnection.Convertor.collect;




@RestController
@RequestMapping("/api")
public class StockController {
	private final GroupService service;

	public StockController(GroupService service) {
		this.service = service;
	}



//    @RequestMapping(value = "/all",
//            produces = "application/json",
//            method = {RequestMethod.GET, RequestMethod.POST})
//        public Response getResource() {
//        Response response = new Response("Done", cust);
//        return response;
//    }

//    @RequestMapping(value = "/save",
//            produces = "application/json",
//            method = {RequestMethod.GET, RequestMethod.POST})
//
//    public Response postCustomer(@RequestBody Customer customer) {
//
//        cust.add(customer);
//
//
//        // Create Response Object
//        Response response = new Response("Done", customer);
//        return response;
//    }
//}
		LinkedHashMap<String,Integer> myLinkedHashMap =  new LinkedHashMap<String, Integer>();

	@PostMapping("/findgroup")
	public ResponseEntity<?> getSearchResultViaAjax( @RequestBody SearchCriteria search, Errors errors) {

		AjaxResponseBody result = new AjaxResponseBody();

		if (errors.hasErrors()) {

			result.setMsg(errors.getAllErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.joining(",")));
			return ResponseEntity.badRequest().body(result);

		}
		Group group = service.groupRequestHandler(search.getGroupName());
		myLinkedHashMap = collect(group.getRangeList(), 20);
		if (myLinkedHashMap.isEmpty()) {
			result.setMsg("no groups found!");
		} else {
			result.setMsg("success");
		}
		result.setResult(myLinkedHashMap);

		return ResponseEntity.ok(result);

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