package com.vkgroupstat.vkconnection;

import java.util.LinkedList;

import com.vkgroupstat.model.Group;

public class GroupCollector {
	public static Group collector(String groupName) {
		LinkedList<Integer> listUsers = VkConnection.getGroupVkSdk(groupName);
		Integer[] arrayUsers = new Integer[listUsers.size()];
		arrayUsers = listUsers.toArray(arrayUsers);
		return new Group(groupName, arrayUsers);
	}
}
