package com.vkgroupstat.vkconnection;

import java.util.LinkedHashMap;
import java.util.LinkedList;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;

import com.vkgroupstat.vkconnection.Sub;

public class GroupInfoParser {
	
	private final String token = "b8188fe1b8188fe1b8188fe1ffb868d748bb818b8188fe1e6699966a9d9035f1e14fbda";
	private final ServiceActor actor_s = new ServiceActor(7362729, token);
	private final VkApiClient vk_s = new VkApiClient(HttpTransportClient.getInstance());
	
	private final String user_token = "7d6581b7a6ba55ee55c580d6ba0908b4b04a218b22309dc2907902261b8a2f71876e0034b89f65335dc20";
	private final UserActor actor_u = new UserActor(7362729, user_token);
	private final VkApiClient vk_u = new VkApiClient(HttpTransportClient.getInstance());
	
	private String groupName;	
	private Integer count;
	
	public GroupInfoParser() {
		this.groupName = "molodoipeterburg";
		this.count = 1400;
	}
	
	public GroupInfoParser(String groupName) {
		this.groupName = groupName;
		try {
			count = vk_s
					.groups()
					.getMembers(actor_s)
					.groupId(groupName)
					.unsafeParam("access_token", actor_s.getAccessToken())
					.execute()
					.getCount();
		} catch (ApiException | ClientException e) {
			System.err.println(e.getMessage());
			count = 0;
		}
	}
	
	public String getGroupUsersInfo(Integer offset) {
		LinkedList<Integer> usersList = new LinkedList<Integer>();
		LinkedHashMap<Integer, Sub> userInfoMap = new LinkedHashMap<Integer, Sub>();
		JsonObject response = null;
		try {
			response = vk_u.execute()
					.storageFunction(actor_u, "getGroupSubsInfo")
					.unsafeParam("offset", offset)
					.unsafeParam("count", count)
					.unsafeParam("groupName", groupName)
					.execute()
					.getAsJsonObject();
		} catch (ClientException | ApiException e) {
			System.err.println(e.getMessage());
		}
		response
			.get("info")
			.getAsJsonArray()
			.forEach(item -> { 
							JsonObject json = item.getAsJsonObject();
							userInfoMap.put(json.get("id").getAsInt(),new Sub(json));								
										 }
								);
		return userInfoMap.toString();
	}
}