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
	String U_TOKEN = "006ad7559bfe9e5a58883f3eac896d6e0d509635d0b10fde4a375dfaf9d66d8086bd2005728c954cab252";
	UserActor U_ACTOR = new UserActor(APPID, U_TOKEN);

	VkApiClient VK = new VkApiClient(HttpTransportClient.getInstance());
}