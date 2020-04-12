package com.vkgroupstat.vkconnection;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.app.widgets.ImageItem;
import com.vk.api.sdk.queries.users.UserField;

public class VkMultiConnection {
	
	private static final String token = "b8188fe1b8188fe1b8188fe1ffb868d748bb818b8188fe1e6699966a9d9035f1e14fbda";
	private static final ServiceActor actor_s = new ServiceActor(7362729, token);
	private static final VkApiClient vk_s = new VkApiClient(HttpTransportClient.getInstance());
	
	private static final String user_token = "788ee1a7661917ae996ba0328d0ef942c32965d0c274b25a0cdff606949cc4acc4b6cd488769ebbc9d8d7";
	private static final UserActor actor_u = new UserActor(7362729, user_token);
	private static final VkApiClient vk_u = new VkApiClient(HttpTransportClient.getInstance());
	
	public static String getGroupVkSdk(String groupName) {
		LinkedList<String> subsList = new LinkedList<String>();
		String temp = "";
		int subCount = 0;
		int offset = 0;
		String log = "<br>";
		long b = new Date().getTime();
		try {
			subCount = vk_s.groups()
					.getMembers(actor_s)
					.groupId(groupName)
					.unsafeParam("access_token", actor_s.getAccessToken())
					.execute()
					.getCount();	
		} catch (ClientException | ApiException e) {
			e.printStackTrace();
		}		
		while (offset < subCount) {
			try {
				subsList.addAll(Arrays
						.asList(vk_u.execute()
								.storageFunction(actor_u, "getGroupSubs")
								.unsafeParam("groupName", groupName)
								.unsafeParam("offset", offset)
								.unsafeParam("count", subCount)
								.execute()
								.toString()
								.substring(1)
								.split(",")
								)
						);
				temp = subsList.removeLast();
				subsList.addLast(temp.substring(0, temp.length()-1));
				temp = "";
				offset += 25000;
				if ((offset % 200000) == 0)
					try {Thread.sleep(1000); System.err.println("Wait");} catch (InterruptedException e) {}
			}
			catch (ApiException | ClientException e) {
				System.err.println("NO!");
				try {
					Thread.sleep(250);
				} catch (InterruptedException e1) {}
			}
		}
		long s = new Date().getTime();
		List<Integer> intList = subsList.stream().map(Integer::parseInt).collect(Collectors.toList());
		long e = new Date().getTime();
		return new Integer(intList.size()).toString() + "<br>" + (s - b) + "<br>" + (e - s) + log;
	}
	public static String getGroupUsersInfo(String groupName) {
		LinkedList<Integer> list = new LinkedList<Integer>();
		Integer count = 0;
		try {
			count = vk_s.groups().getMembers(actor_s).groupId(groupName).unsafeParam("access_token", actor_s.getAccessToken()).execute().getCount();
			vk_u.execute()
					.storageFunction(actor_u, "getGroupSubsInfo")
					.unsafeParam("offset", 0)
					.unsafeParam("count", count)
					.unsafeParam("groupName", groupName)
					.execute()
					.getAsJsonObject()
					.get("users")
					.getAsJsonArray()
					.forEach(item -> list.add(item.getAsInt()));
		} catch (ClientException | ApiException e) {
			return "Ошибка<br>" + e.toString();
		}
		return list.toString();
	}
	public static String test() {
		JsonObject json;
		try {
			json = vk_u.execute()
					.storageFunction(actor_u, "getGroupSubsInfo")
					.unsafeParam("offset", 0)
					.unsafeParam("count", 13000)
					.unsafeParam("groupName", "molodoipeterburg")
					.execute().getAsJsonObject();
			return json.toString();
			//поэксперемнтировтаь с json, можно ли вытащить вложенный объект, что выдаст getas.. если такого объекта нет localhost:8080/t
		} catch (ClientException | ApiException e) {
			return null;
		}
	}
}
