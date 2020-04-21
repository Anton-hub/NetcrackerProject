package com.vkgroupstat.controller;

import com.vkgroupstat.model.AjaxResponseBody;
import com.vkgroupstat.model.Group;
import com.vkgroupstat.model.SearchCriteria;
import com.vkgroupstat.model.User;
import com.vkgroupstat.service.FeedbackService;
import com.vkgroupstat.service.GroupService;
import com.vkgroupstat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;





@RestController
@RequestMapping("/api")
public class StockController {
	@Autowired
	private MailSender mailSender;

//
//	@Value("checkins.tracker@gmail.com")
//	String email="checkins.tracker@gmail.com";
	private final GroupService service;
	private final UserService uService;

	public StockController(GroupService service, UserService uService) {
		this.service = service;
		this.uService = uService;
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
	@PostMapping("/showhistory")
	public ResponseEntity<?> getHistory() {


		User user = uService.getUser(WebController.USER_ID);
		System.out.println(user.getUserId());

		return ResponseEntity.ok(user);

	}

	@RequestMapping(value = "/sendEmail", method = RequestMethod.POST)
	public	@ResponseBody	String sendEmail(@RequestParam("email") String emailUser,
					 @RequestParam("subject") String subject,
					 @RequestParam("message") String message) {

		FeedbackService feedbackService = new FeedbackService(mailSender);

		if (!feedbackService.validateEmail(emailUser)) {
			return "Your email address is invalid. Please enter a valid address.";
		}

		if (message.trim().isEmpty()) {
			return "Please enter the message.";
		}

		feedbackService.sendMsg(emailUser, subject, message);

		return "The message has been sent!";
	}
	//тестовые методы


}