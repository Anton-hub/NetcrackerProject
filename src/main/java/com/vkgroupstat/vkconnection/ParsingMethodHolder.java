package com.vkgroupstat.vkconnection;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.groups.GroupFull;

public class ParsingMethodHolder implements VkSdkObjHolder{
	
	private static final Logger LOG = LogManager.getLogger(ParsingMethodHolder.class);
	
	public static List<Integer> getUserSubscriptions(Integer userId) {
		try {
			return VK.users()
					   .getSubscriptions(S_ACTOR)
					   .userId(userId)
					   .execute()
					   .getGroups()
					   .getItems();
		} catch (ApiException | ClientException e) {
			LOG.error(e.getMessage());
			return null;
		}
	}
	
	public static Integer getGroupSubsCount(String groupName) {
		try {
			return VK.groups()
					   .getMembers(S_ACTOR)
					   .groupId(groupName)
					   .unsafeParam("access_token", S_ACTOR.getAccessToken())
					   .execute()
					   .getCount();
		} catch (ApiException | ClientException e) {
			LOG.error(e.getMessage());
			return null;
		}
	}
	
	public static List<GroupFull> getGroupsInfo(LinkedList<Integer> litsId) {
		List<String> stringList = litsId.stream().map(Object::toString).collect(Collectors.toList());
		try {
			return VK.groups()
					   .getById(S_ACTOR)
					   .groupIds(stringList.toArray(new String[0]))
					   .execute();
		} catch (ApiException | ClientException e) {
			LOG.error(e.getMessage());
			return null;
		}
	}
	
	public static GroupFull getGroupInfo(String groupSreenName) {
		try {
			return VK.groups()
					   .getById(S_ACTOR)
					   .groupId(groupSreenName)
					   .execute()
					   .get(0);
		} catch (ApiException | ClientException e) {
			LOG.error(e.getMessage());
			return null;
		}
	}
}