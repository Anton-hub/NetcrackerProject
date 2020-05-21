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
	String U_TOKEN = "8417495f64370f891321811d7571d1485c4e342de39ee7b1c0faa1734cb21c711c2756f56c8d78250d74e";
	UserActor U_ACTOR = new UserActor(APPID, U_TOKEN);

	VkApiClient VK = new VkApiClient(HttpTransportClient.getInstance());
}