package com.vkgroupstat.controller;

import com.vkgroupstat.message.Response;
import com.vkgroupstat.model.Customer;
import com.vkgroupstat.model.Group;
import com.vkgroupstat.service.GroupService;
import com.vkgroupstat.vkconnection.Convertor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class StockController {

	List<Customer> cust = new ArrayList<Customer>();

	@GetMapping(value = "/all")
	public Response getResource() {
		Response response = new Response("Done", cust);
		return response;
	}

	@PostMapping(value = "/save")

	public Response postCustomer(@RequestBody Customer customer) {

		cust.add(customer);


		// Create Response Object
		Response response = new Response("Done", customer);
		return response;
	}
}

//	private final GroupService service;
//	public StockController(GroupService service) {
//		this.service = service;
//	}
//
//
//	@RequestMapping("/findgroup")
//	public String returnSubscriptions(@RequestParam String groupName) {
//		Group group = service.groupRequestHandler(groupName);
//		return Convertor.groupParse(group);
//
//	}


	//тестовые методы
//	@RequestMapping("/")
//	public String getHello() {
//		return "Hello";
//	}
//	@RequestMapping("/usersubsint")
//	public String returnSubscriptionsInt(@RequestParam Integer userId) {
//		return service.returnSubscriptionsInt(userId);
//	}
//	@RequestMapping("/test")
//	public String test(@RequestParam String groupName) {
//		return service.testMainFunctional(groupName);
//	}
//	@RequestMapping("/testnew")
//	public String testss(@RequestParam String groupName) {
//		return service.returnSubsnew(groupName);
//	}
	//конец тестовых
