package com.vkgroupstat.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

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

import com.vkgroupstat.model.Group;
import com.vkgroupstat.model.SearchCriteria;
import com.vkgroupstat.model.User;
import com.vkgroupstat.service.FeedbackService;
import com.vkgroupstat.service.GroupService;
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

	public StockController(GroupService service, UserService uService) {
		this.service = service;
		this.uService = uService;
	}


	LinkedHashMap<String,Integer> myLinkedHashMap =  new LinkedHashMap<String, Integer>();

	@PostMapping("/findgroup")
	public ResponseEntity<?> getSearchResultViaAjax( @RequestBody SearchCriteria search, Errors errors, Model model) {

		Group group;
		try {
			group = service.groupRequestHandler(search.getGroupName());
		} catch (NoDataAccessException e) {
			group = null; //добавить сюда обработку ошибки
		}
//		model.addAttribute(group);
		return ResponseEntity.ok(group);

	}
//	@RequestMapping("/downloadXLS")
//	public void downloadXLS( @RequestBody SearchCriteria search) {
//		HttpServletResponse response = new HttpServletResponse() {
//
//		};
//		Group group;
//		try {
//			group = service.groupRequestHandler(search.getGroupName());
//		} catch (NoDataAccessException e) {
//			group = null; //добавить сюда обработку ошибки
//		}
//		LinkedList <Subscription> groupsList = group.getRangeList();
//		try {
////			response.setContentType("text/html; charset=UTF-8");
////			response.setCharacterEncoding("UTF-8");
//			response.setContentType("application/vnd.ms-excel");
//			String reportName =  "GroupList.xls";
//			response.setHeader("Content-disposition",  "attachment; filename=" +  reportName);
//			ArrayList< String>  rows =  new ArrayList< String> ( );
//			rows.add("Vk id");
//			rows.add("\t");
//			rows.add( "Group Name");
//			rows.add("\t");
//			rows.add( "Target Subs Count");
//			rows.add("\t");
//			rows.add( "All Subs Count");
//			rows.add("\t");
//			rows.add( "Women");
//			rows.add("\t");
//			rows.add( "Men");
//			rows.add("\t");
//			rows.add( "> 1 like");
//			rows.add("\t");
//			rows.add( "> 1 like and comment");
//			rows.add("\n");
//
//			for(Subscription current : groupsList){
//				rows.add(current.getId().toString());
//				rows.add("\t");
//				rows.add(current.getUrlName());
//				rows.add("\t");
//				rows.add(current.getTargetSubsCount().toString());
//				rows.add("\t");
//				rows.add(current.getThisGroupSubsCount().toString());
//				rows.add("\t");
//				rows.add(current.getStatistics().getSexStat().get(0).getCount().toString());
//				rows.add("\t");
//				rows.add(current.getStatistics().getSexStat().get(1).getCount().toString());
//				rows.add("\t");
//				rows.add(current.getStatistics().getActivityStat().get(0).getCount().toString());
//				rows.add("\t");
//				rows.add(current.getStatistics().getActivityStat().get(1).getCount().toString());
//				rows.add("\n");
//			}
//			Iterator <String> iter = rows.iterator();
//			while (iter.hasNext()) {
//				String outputString = (String)iter.next();
//				response.getOutputStream().print(outputString);
//			}
//
//			response.getOutputStream().flush();

//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//	}
//	@GetMapping("/download")
//	public String download(Model model) {
//		return "";
//	}
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