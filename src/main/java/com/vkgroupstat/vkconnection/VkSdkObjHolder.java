package com.vkgroupstat.vkconnection;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;

public interface VkSdkObjHolder {
	Integer APPID = 7362729;
	String PROTECTEDKEY = "pqnxEFjvEdh3GHY0iOf3";
	
	String S_TOKEN = "b8188fe1b8188fe1b8188fe1ffb868d748bb818b8188fe1e6699966a9d9035f1e14fbda";
	ServiceActor S_ACTOR = new ServiceActor(APPID, S_TOKEN);	

	String U_TOKEN = "c94ceaf8c143cd2a0b30d1fd1e36344e011b847a2205b1bd1bf7ec1d88e4bb1be7a0f0f050b970bb36c21";
	UserActor U_ACTOR = new UserActor(APPID, U_TOKEN);	

	VkApiClient VK = new VkApiClient(HttpTransportClient.getInstance());
}