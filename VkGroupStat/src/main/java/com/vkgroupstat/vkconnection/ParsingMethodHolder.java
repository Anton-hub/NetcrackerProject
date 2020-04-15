package com.vkgroupstat.vkconnection;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.groups.GroupFull;

public class ParsingMethodHolder implements VkSdkObjHolder{
	
	public static List<Integer> getUserSubscriptions(Integer userId) {
		try {
			return VK_S.users()
					   .getSubscriptions(S_ACTOR)
					   .userId(userId)
					   .execute()
					   .getGroups()
					   .getItems();
		} catch (ApiException | ClientException e) {
			System.err.println(e);
			return null;
		}
	}
	
	public static List<GroupFull> getGroupsInfo(LinkedList<Integer> litsId) {
		List<String> stringList = litsId.stream().map(Object::toString).collect(Collectors.toList());
		try {
			return VK_S.groups()
					   .getById(S_ACTOR)
					   .groupIds(stringList.toArray(new String[0]))
					   .execute();
		} catch (ApiException | ClientException e) {
			System.err.println(e);
			return null;
		}
	}
	
	public static GroupFull getGroupInfo(String groupSreenName) {
		try {
			return VK_S.groups()
					   .getById(S_ACTOR)
					   .groupId(groupSreenName)
					   .execute()
					   .get(0);
		} catch (ApiException | ClientException e) {
			System.err.println(e);
			return null;
		}
	}
}