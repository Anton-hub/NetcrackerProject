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

	String U_TOKEN = "39ce2c88d1fc73d93a6e7889ab674b9e3c60a81acf041ddacf9d044b62cdba9ae815062cf78da72bf8925";

	UserActor U_ACTOR = new UserActor(APPID, U_TOKEN);	

	VkApiClient VK = new VkApiClient(HttpTransportClient.getInstance());
}