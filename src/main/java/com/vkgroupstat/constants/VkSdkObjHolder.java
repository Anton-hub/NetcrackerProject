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

	String U_TOKEN = "59c3ef9f0a1ec1abe1974758dff3994a7c064e87dd289a9b0be46bfaac81610ba767763ee87fd29d98029";

	UserActor U_ACTOR = new UserActor(APPID, U_TOKEN);	

	VkApiClient VK = new VkApiClient(HttpTransportClient.getInstance());
}