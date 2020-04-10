package com.vkgroupstat.vkconnection;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.groups.GroupFull;

public class VkConnection {

	private static String token = "b8188fe1b8188fe1b8188fe1ffb868d748bb818b8188fe1e6699966a9d9035f1e14fbda";
	private static ServiceActor actor = new ServiceActor(7362729, token);
	private static VkApiClient vk = new VkApiClient(HttpTransportClient.getInstance());
	
	public static LinkedList <Integer> getGroupVkSdk(String groupName) {
		LinkedList <Integer> subIdArray = new LinkedList<Integer>();
		Integer offset = 0;
		Integer subCount = 0;
		try {
			subCount = vk.groups()
							.getMembers(actor)
							.groupId(groupName)
							.unsafeParam("access_token", actor.getAccessToken())
							.execute()
							.getCount();
			while (offset < subCount) {	
				subIdArray.addAll(vk.groups()
							.getMembers(actor)
							.groupId(groupName)
							.unsafeParam("access_token", actor.getAccessToken())
							.offset(offset)
							.execute()
							.getItems());
				offset += 1000;
			}
		} catch (ClientException | ApiException e) {
			e.printStackTrace();
			return null;
		}
		return subIdArray;
	}
	
	public static List<Integer> getUserSubsVkSdk(Integer userId) {
		List<Integer> subsIdArray = null;
		try {
			subsIdArray = vk.users()
						.getSubscriptions(actor)
						.userId(userId)
						.execute()
						.getGroups()
						.getItems();
		} catch (ApiException | ClientException e) {
			//e.printStackTrace();
			System.err.println(e);
			return null;
		}
		return subsIdArray;
		//photo_id, verified, sex, bdate, city, country, home_town, has_photo, domain, has_mobile, contacts, site, education, universities, schools, last_seen, followers_count, common_count, occupation, nickname, relatives, relation, personal, connections, exports, activities, interests, music, movies, tv, books, games, about, quotes, timezone, screen_name, maiden_name, career, militarycan_be_invited_group
	}
	
	public static List<GroupFull> getGroupsInfo(Collection<Integer> keySet, String parameter) {
		Collection<String> stringList = keySet.stream().map(Object::toString).collect(Collectors.toList());
		List<GroupFull> response;
		try {
			response = vk.groups()
					.getById(actor)
					.groupIds(stringList.toArray(new String[0]))
					//.fields(GroupField.valueOf(parameter))
					.execute();
		} catch (ApiException | ClientException e) {
			e.printStackTrace();
			return null;
		}
		return response;
	}
	
	public static GroupFull getGroupInfo(String groupSreenName) {
		GroupFull response;
		try {
			response = vk.groups()
					.getById(actor)
					.groupId(groupSreenName)
					.execute()
					.get(0);
		} catch (ApiException | ClientException e) {
			e.printStackTrace();
			return null;
		}
		return response;
	}
}