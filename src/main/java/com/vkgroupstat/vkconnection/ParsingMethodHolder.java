package com.vkgroupstat.vkconnection;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.vk.api.sdk.objects.groups.GroupFull;
import com.vk.api.sdk.objects.wall.WallPostFull;
import com.vk.api.sdk.queries.groups.GroupField;
import com.vk.api.sdk.queries.wall.WallGetFilter;

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
					   .fields(GroupField.MEMBERS_COUNT, GroupField.DESCRIPTION)
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
					   .fields(GroupField.MEMBERS_COUNT, GroupField.DESCRIPTION)
					   .execute()
					   .get(0);
		} catch (ApiException | ClientException e) {
			LOG.error(e.getMessage());
			return null;
		}
	}
	
	public static List<WallPostFull> getWallPosts(String groupName, Integer offset){
		try {
			return VK.wall()
						.get(S_ACTOR)
						.domain(groupName)
						.offset(offset)
						.count(100)
						.filter(WallGetFilter.ALL)
						.unsafeParam("extended", 0)
						.execute()
						.getItems();
		} catch (ApiException | ClientException e) {
			LOG.error(e.getMessage());
			return null;
		}
	}
	
	public static UserAuthResponse getUserAuthInfo(String code) {
		try {
			return VK.oauth()
						.userAuthorizationCodeFlow(APPID, PROTECTEDKEY, "http://localhost:8080/search", code)
						.execute();
		} catch (ClientException | ApiException e) {
			LOG.error(e);
			return null;
		}
	}
}