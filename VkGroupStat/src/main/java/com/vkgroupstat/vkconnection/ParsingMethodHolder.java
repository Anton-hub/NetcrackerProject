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

public class ParsingMethodHolder{
	
	private static final String token = "b8188fe1b8188fe1b8188fe1ffb868d748bb818b8188fe1e6699966a9d9035f1e14fbda";
	private static final ServiceActor actor_s = new ServiceActor(7362729, token);
	private static final VkApiClient vk_s = new VkApiClient(HttpTransportClient.getInstance());
	
	public static LinkedList <Integer> getGroupVkSdk(String groupName) {
		LinkedList <Integer> subIdArray = new LinkedList<Integer>();
		Integer offset = 0;
		Integer subCount = 0;
		try {
			subCount = vk_s.groups()
							.getMembers(actor_s)
							.groupId(groupName)
							.unsafeParam("access_token", actor_s.getAccessToken())
							.execute()
							.getCount();
			while (offset < subCount) {	
				subIdArray.addAll(vk_s.groups()
							.getMembers(actor_s)
							.groupId(groupName)
							.unsafeParam("access_token", actor_s.getAccessToken())
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
			subsIdArray = vk_s.users()
						.getSubscriptions(actor_s)
						.userId(userId)
						.execute()
						.getGroups()
						.getItems();
		} catch (ApiException | ClientException e) {
			System.err.println(e);
			return null;
		}
		return subsIdArray;
	}
	
	public static List<GroupFull> getGroupsInfo(Collection<Integer> keySet) {
		Collection<String> stringList = keySet.stream().map(Object::toString).collect(Collectors.toList());
		List<GroupFull> response;
		try {
			response = vk_s.groups()
					.getById(actor_s)
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
			response = vk_s.groups()
					.getById(actor_s)
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