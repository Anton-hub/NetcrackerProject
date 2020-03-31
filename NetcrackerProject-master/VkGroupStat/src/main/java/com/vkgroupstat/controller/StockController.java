package com.vkgroupstat.controller;

import com.vkgroupstat.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.vkgroupstat.model.Group;
import com.vkgroupstat.service.GroupService;
import org.springframework.web.servlet.ModelAndView;


@RestController
public class StockController {
	private static Person person = new Person();
	@Autowired
	GroupService service;

	@GetMapping
	public String index() {

		return "redirect:/index";
	}

	@GetMapping("/index")
	public String getIndex() {

		return "index.html";
	}

//@RequestMapping({"/index", "/"})
//public ModelAndView index() {
////	logger.info("Loading view: index");
//	ModelAndView modelAndView = new ModelAndView();
//	modelAndView.setViewName("index");
//	return modelAndView;
//}
	@RequestMapping("/all")
	public String all() {
		return service.findAll().toString();
	}
	
	@RequestMapping("/create")
	public String create(@RequestParam String firstName) {
		Group group = service.create(firstName);
		return group.toString();
	}
	
	@RequestMapping("/parse")  
	public String returnSubscribers(@RequestParam String groupName) {
		return service.returnSubscribers(groupName);
	}
	
	@RequestMapping("/usersubs")//не работает (выводит первые 200)
	public String returnSubscriptions(@RequestParam Integer userId) {
		return service.returnSubscriptions(userId);
	}
	@RequestMapping("/usersubsvk")//не работает
	public String returnSubscriptionsVk(@RequestParam Integer userId, Model model) {
		person = service.returnSubscriptionsVk(userId);
		model.addAttribute("person", person);
		return "usersubsvk";
	}
	@RequestMapping("/usersubsworking")//выводит все, но некрасивым json'ом
	public String returnSubscriptionsVkWorking(@RequestParam Integer userId) {
		return service.returnSubscriptionsVkWorking(userId);
	}
//	@RequestMapping("/usersubsworkingalmost")//в работе
//	public String returnSubscriptionsVkWorkingAlmost(@RequestParam Integer userId) throws Exception{
//		return service.returnSubscriptionsVkWorkingAlmost(userId);
//	}
}