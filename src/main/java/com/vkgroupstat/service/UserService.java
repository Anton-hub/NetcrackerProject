package com.vkgroupstat.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vk.api.sdk.objects.UserAuthResponse;
import com.vkgroupstat.controller.WebController;
import com.vkgroupstat.model.User;
import com.vkgroupstat.repository.UserRepository;
import com.vkgroupstat.vkconnection.ParsingMethodHolder;

@Service
public class UserService {

	
	private static final Logger LOG = LogManager.getLogger(UserService.class);
	
	private final UserRepository userRep;
	private final ParsingMethodHolder pmh;
	
	@Autowired
	public UserService(UserRepository repository, ParsingMethodHolder pmh) {
		this.userRep = repository;
		this.pmh = pmh;
	}

	public Integer userRequestHandler(String code) {

		UserAuthResponse userInfo = pmh.getUserAuthInfo(code);
		User user = userRep.findByuserId(userInfo.getUserId());
		if (user == null) {
			user = new User(userInfo.getUserId());
			userRep.save(user);
		}
		return user.getUserId();
		
	}

	public void addGroup(String groupId) {
		User user = userRep.findByuserId(WebController.USER_ID);
		user.addGroupId(groupId);
		userRep.save(user);
	}

	public User getUser(Integer userId){
		User user = userRep.findByuserId(userId);
		return user;
	}

}
