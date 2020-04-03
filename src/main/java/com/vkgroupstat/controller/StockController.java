package com.vkgroupstat.controller;

import com.vkgroupstat.model.Person;
import com.vkgroupstat.model.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import com.vkgroupstat.model.Group;
import com.vkgroupstat.service.GroupService;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class StockController {
	private final GroupService service;
	public StockController(GroupService service) {
		this.service = service;
	}


//	@RequestMapping("/findgroup")
//	public String returnSubscriptions(@RequestParam String groupName) {
//		Group group = service.groupRequestHandler(groupName);
//		return group.getUsers().length + "<br>" + group.getGroupName() + "<br><br>"
//				+ Arrays.asList(group.getUsers()).toString();
//	}
	@PostMapping("/findgroup")
	@ResponseBody
	public  String getSearchResultViaAjax( SearchCriteria search) {




		Group group = service.groupRequestHandler(search.getGroupId());


		return group.toString();

	}

	@GetMapping
	public String index() {

		return "redirect:/index";
	}

	@GetMapping("/index")
	public String getIndex() {

		return "index";
	}
	@GetMapping("/search")
	public String getSearch() {

		return "search";
	}

//@RequestMapping({"/index", "/"})
//public ModelAndView index() {
////	logger.info("Loading view: index");
//	ModelAndView modelAndView = new ModelAndView();
//	modelAndView.setViewName("index");
//	return modelAndView;
//}
//	@RequestMapping("/all")
//	public String all() {
//		return service.findAll().toString();
//	}
//
//	@RequestMapping("/create")
//	public String create(@RequestParam String firstName) {
//		Group group = service.create(firstName);
//		return group.toString();
//	}
//
//	@RequestMapping("/parse")
//	public String returnSubscribers(@RequestParam String groupName) {
//		return service.returnSubscribers(groupName);
//	}
//
//	@RequestMapping("/usersubs")//не работает (выводит первые 200)
//	public String returnSubscriptions(@RequestParam Integer userId) {
//		return service.returnSubscriptions(userId);
//	}
//	@RequestMapping("/usersubsvk")//не работает
//	public String returnSubscriptionsVk(@RequestParam Integer userId, Model model) {
//		person = service.returnSubscriptionsVk(userId);
//		model.addAttribute("person", person);
//		return "usersubsvk";
//	}
//	@RequestMapping("/usersubsworking")//выводит все, но некрасивым json'ом
//	public String returnSubscriptionsVkWorking(@RequestParam Integer userId) {
//		return service.returnSubscriptionsVkWorking(userId);
//	}
//	@RequestMapping("/usersubsworkingalmost")//в работе
//	public String returnSubscriptionsVkWorkingAlmost(@RequestParam Integer userId) throws Exception{
//		return service.returnSubscriptionsVkWorkingAlmost(userId);
//	}
}