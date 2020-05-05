package com.vkgroupstat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vk.api.sdk.client.actors.UserActor;
import com.vkgroupstat.constants.VkSdkObjHolder;
import com.vkgroupstat.controller.WebController;
import com.vkgroupstat.repository.UserRepository;

@Service
public class TokenService implements VkSdkObjHolder{
	private String token;
	private UserActor actor;
	
	private final UserRepository repository;
	
	@Autowired
	public TokenService(UserRepository repository) {
		this.repository = repository;
		tryToInit();
	}
	
	public String takeToken() {
		if (token != "") {
			return token;
		} else {
			if (tryToInit())
				return token;
			return U_TOKEN;
		}
	}
	
	public UserActor takeActor() {
		if (actor != null) {
			return actor;
		} else {
			if (tryToInit())
				return actor;
			return U_ACTOR;
		}
	}
	
	private Boolean tryToInit() {
		if (WebController.USER_ID != null) {
			token = repository.getUserToken(WebController.USER_ID);
			if (token != null) {
				actor = new UserActor(APPID, token);
				return true;
			} else {
				return false;
			} 
		} else {
			return false;
		}
	}
}