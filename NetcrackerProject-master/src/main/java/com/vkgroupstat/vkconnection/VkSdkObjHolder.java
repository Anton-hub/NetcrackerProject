package com.vkgroupstat.vkconnection;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;

public interface VkSdkObjHolder {
	Integer APPID = 7362729;
	String S_TOKEN = "b8188fe1b8188fe1b8188fe1ffb868d748bb818b8188fe1e6699966a9d9035f1e14fbda";
	ServiceActor S_ACTOR = new ServiceActor(APPID, S_TOKEN);
	VkApiClient VK_S = new VkApiClient(HttpTransportClient.getInstance());

	
	//with stats
	String U_TOKEN = "8990b076a67306d0063f8e028a88d06c20856b1edce4c8af8d07b92a36eaeb192b86d27eaab1a0a76698f";
	UserActor U_ACTOR = new UserActor(APPID, U_TOKEN);
	VkApiClient VK_U = new VkApiClient(HttpTransportClient.getInstance());
}