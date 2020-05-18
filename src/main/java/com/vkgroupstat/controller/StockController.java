package com.vkgroupstat.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.vkgroupstat.vkconnection.vkentity.Subscription;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import com.vkgroupstat.exception.NoDataAccessException;
import com.vkgroupstat.export.excel.ExcelCollector;
import com.vkgroupstat.model.Group;
import com.vkgroupstat.model.SearchCriteria;
import com.vkgroupstat.model.User;
import com.vkgroupstat.service.FeedbackService;
import com.vkgroupstat.service.GroupService;
import com.vkgroupstat.service.QueueManager;
import com.vkgroupstat.service.UserService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

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
	
	private final QueueManager manager;

	public StockController(GroupService service, UserService uService, QueueManager manager) {
		this.service = service;
		this.uService = uService;
		this.manager = manager;
	}


	LinkedHashMap<String,Integer> myLinkedHashMap =  new LinkedHashMap<String, Integer>();

	@PostMapping("/findgroup")
	public ResponseEntity<?> getSearchResultViaAjax( @RequestBody SearchCriteria search, Errors errors, Model model) {

		Group group;
		try {
//			group = service.groupRequestHandler(search.getGroupName());
			CompletableFuture<Group> fut = manager.getGroup(search.getGroupName());
			LOG.info("fut1 " + search.getGroupName());
			group = fut.get();
			LOG.info("fut2 " + search.getGroupName());
		} catch (NoDataAccessException | InterruptedException | ExecutionException e) {
			group = null; //добавить сюда обработку ошибки
		}
		model.addAttribute(group);
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