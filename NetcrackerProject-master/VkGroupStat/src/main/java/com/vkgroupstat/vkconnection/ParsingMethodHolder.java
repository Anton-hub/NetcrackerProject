package com.vkgroupstat.vkconnection;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.groups.GroupFull;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ParsingMethodHolder implements VkSdkObjHolder {
	
	public static List<Integer> getUserSubscriptions(Integer userId) {
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