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
	String U_TOKEN = "873c5368a715c397b8365814f0bad1b621b5eafd29d1e79a681f545d9e1f1950be6a501eef880318b42da";
	UserActor U_ACTOR = new UserActor(APPID, U_TOKEN);
	VkApiClient VK_U = new VkApiClient(HttpTransportClient.getInstance());
}