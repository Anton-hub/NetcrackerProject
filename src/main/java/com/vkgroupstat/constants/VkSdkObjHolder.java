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

	/**
	 * здесь должен быть стоковый токен с какого нить фейка,
	 * для работы приложения без подтверждения вк, 
	 * но пока тут мой токен)) 
	 * P.S. скорей всего когда ты это читаешь он уже просрочен
	 */
	String U_TOKEN = "57a835da175cc46e5ce4da247150dd8c0f80ce4e516dac7a20a6270083eda8f0b6c70890147c2faab8d58";
	UserActor U_ACTOR = new UserActor(APPID, U_TOKEN);

	VkApiClient VK = new VkApiClient(HttpTransportClient.getInstance());
}