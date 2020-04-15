package com.vkgroupstat.vkconnection.TEST;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vk.api.sdk.exceptions.ClientException;
import com.vkgroupstat.vkconnection.VkSdkObjHolder;

public class TEST_parseStat implements VkSdkObjHolder{
	public static String statGet(String groupId) {
		try {
			@SuppressWarnings(value = "deprecation")
			JsonObject json = new JsonParser()
									.parse(VK_U
											.stats()
											.get(U_ACTOR)
											.appId(APPID)
											.groupId(68471405)
											.unsafeParam("intervals_count", 1)
											.unsafeParam("v", "5.103")
											.unsafeParam("stats_groups", "reach")
											.unsafeParam("filters", "age", "city")
											.executeAsString()
											).getAsJsonObject();
			json = json.get("response").getAsJsonArray().get(0).getAsJsonObject();
			return json.toString();		
		} catch (ClientException e) {
			System.err.println(e);
			return e.getMessage();
		}
	}
}
