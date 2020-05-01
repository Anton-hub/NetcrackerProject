package com.vkgroupstat.constants;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;

public interface VkSdkObjHolder {
	Integer APPID = 7362729;
	String PROTECTEDKEY = "pqnxEFjvEdh3GHY0iOf3";
	
	String S_TOKEN = "b8188fe1b8188fe1b8188fe1ffb868d748bb818b8188fe1e6699966a9d9035f1e14fbda";
	ServiceActor S_ACTOR = new ServiceActor(APPID, S_TOKEN);	

	String U_TOKEN = "51366290ac5ef5c581bccf5c76ac2654646312d0fa96c298dd44165bf96a50adfc1a59e767d22990af14b";
	UserActor U_ACTOR = new UserActor(APPID, U_TOKEN);	

	VkApiClient VK = new VkApiClient(HttpTransportClient.getInstance());
}