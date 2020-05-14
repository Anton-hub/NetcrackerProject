package com.vkgroupstat.service;

import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails)
		{
			String username = ((UserDetails)principal).getUsername();
			User user = userRep.findByEmail(username);
			userRep.refresh(user,userInfo.getUserId(),userInfo.getAccessToken());
		}

		return userInfo.getUserId();
		
	}

	public void addGroup(String groupId) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails)
		{
			String username = ((UserDetails)principal).getUsername();
			User user = userRep.findByEmail(username);
//			User user = userRep.findByuserId(WebController.USER_ID);
			user.addGroupId(groupId);
			userRep.save(user);
		}
	}

	public User getUser(String username){
		User user = userRep.findByEmail(username);
		return user;
	}

}
