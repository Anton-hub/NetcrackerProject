package com.vkgroupstat.service;

import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
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
	public UserService(UserRepository repository) {
		this.userRep = repository;
	}

	public Integer userRequestHandler(String code) {

		UserAuthResponse userInfo = ParsingMethodHolder.getUserAuthInfo(code);

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails)
		{
			String username = ((UserDetails)principal).getUsername();
			User user = userRep.findByEmail(username);
			userRep.refresh(user,userInfo.getUserId(),userInfo.getAccessToken());
			}
//		}
//		else
//		{
//			String username = principal.toString();
//			if (user == null) {
//				user = new User(userInfo.getUserId(), username);
//				userRep.refresh(user,userInfo.getUserId());
//			}
//		}

		return userInfo.getUserId();
		
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
