package com.vkgroupstat.controller;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vkgroupstat.exception.NoDataAccessException;
import com.vkgroupstat.model.Group;
import com.vkgroupstat.model.SearchCriteria;
import com.vkgroupstat.model.User;
import com.vkgroupstat.service.FeedbackService;
import com.vkgroupstat.service.GroupService;
import com.vkgroupstat.service.UserService;

@Controller
@RequestMapping("/api")
public class StockController {

	private static final Logger LOG = LogManager.getLogger(StockController.class);

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


		Group group;
		try {
			group = service.groupRequestHandler(search.getGroupName());
		} catch (NoDataAccessException e) {
			group = null; //добавить сюда обработку ошибки
		}
		return ResponseEntity.ok(group);

	}
	@PostMapping("/showhistory")
	public ResponseEntity<?> getHistory() {
		User user = uService.getUser(WebController.USER_ID);
		LinkedList<Group> list = service.findListById(user.getListGroupsId());
		return ResponseEntity.ok(list);
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