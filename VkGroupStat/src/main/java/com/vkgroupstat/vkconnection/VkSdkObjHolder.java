package com.vkgroupstat.vkconnection;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;

public interface VkSdkObjHolder {
	String token = "b8188fe1b8188fe1b8188fe1ffb868d748bb818b8188fe1e6699966a9d9035f1e14fbda";
	ServiceActor actor_s = new ServiceActor(7362729, token);
	VkApiClient vk_s = new VkApiClient(HttpTransportClient.getInstance());
	
	String user_token = "947f03521054dbde88de067238c574f87518167cf37ab59d3cbd3fd97190aee03912739a2d526e9771e42";
	UserActor actor_u = new UserActor(7362729, user_token);
	VkApiClient vk_u = new VkApiClient(HttpTransportClient.getInstance());
}
