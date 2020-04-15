package com.vkgroupstat.vkconnection.TEST;

import com.vk.api.sdk.objects.groups.GroupFull;
import com.vkgroupstat.vkconnection.ParsingMethodHolder;
import com.vkgroupstat.vkconnection.SubscriberParser;
import com.vkgroupstat.vkconnection.SubscriptionParser;
import com.vkgroupstat.vkconnection.vkentity.Subscriber;
import com.vkgroupstat.vkconnection.vkentity.Subscription;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class TEST_ParseGroupWithoutDB {
	public static String test(String groupName) {
		
		LinkedList<Subscriber> subscriberList = new SubscriberParser(groupName).parse();
		LinkedList<Subscription> subscriptionList = new SubscriptionParser(subscriberList, groupName).parse();
		
		LinkedList<Subscription> slicedSubscriptionList = new LinkedList<Subscription>(subscriptionList
				.stream().limit(100).collect(Collectors.toList()));
		
		slicedSubscriptionList.stream().forEach(item -> item.countUp());		
		fillNameField(slicedSubscriptionList);		

		return TEST_StringOut.subsInfo_statistic_StringOut(slicedSubscriptionList);
	}
	
	public static void fillNameField(LinkedList<Subscription> handledList) {
		LinkedList<Integer> listId = handledList.stream().collect(LinkedList<Integer>::new
																,(l, item) -> l.add(item.getId())
																,(list1, list2) -> list1.addAll(list2));
		LinkedList<GroupFull> groupInfoHolder =
				new LinkedList<GroupFull>(ParsingMethodHolder.getGroupsInfo(listId));
		Iterator<GroupFull> iterator = groupInfoHolder.iterator();
		for (Subscription item : handledList) {
			GroupFull itemGF = iterator.next();
			item.setStringName(itemGF.getName());
			item.setUrlName(itemGF.getScreenName());
		}
	}	
}