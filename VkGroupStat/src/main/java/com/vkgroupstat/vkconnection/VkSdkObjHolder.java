package com.vkgroupstat.vkconnection;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;

public interface VkSdkObjHolder {
	String token = "b8188fe1b8188fe1b8188fe1ffb868d748bb818b8188fe1e6699966a9d9035f1e14fbda";
	ServiceActor actor_s = new ServiceActor(7362729, token);
	VkApiClient vk_s = new VkApiClient(HttpTransportClient.getInstance());
	
	String user_token = "056f69275886262af25495718963700b53589e816d7cb4b72e4ba65d9df894998df05f3c801ae2c20d602";
	UserActor actor_u = new UserActor(7362729, user_token);
	VkApiClient vk_u = new VkApiClient(HttpTransportClient.getInstance());
}