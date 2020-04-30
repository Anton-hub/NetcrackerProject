package com.vkgroupstat.vkconnection.TEST;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

import com.vk.api.sdk.objects.groups.GroupFull;
import com.vkgroupstat.exception.NoDataAccessException;
import com.vkgroupstat.vkconnection.ParsingMethodHolder;
import com.vkgroupstat.vkconnection.TEST.TEST_StringOut;
import com.vkgroupstat.vkconnection.parsers.PostParser;
import com.vkgroupstat.vkconnection.parsers.SubscriberParser;
import com.vkgroupstat.vkconnection.parsers.SubscriptionParser;
import com.vkgroupstat.vkconnection.vkentity.Post;
import com.vkgroupstat.vkconnection.vkentity.Subscriber;
import com.vkgroupstat.vkconnection.vkentity.Subscription;

public class TEST_ParseGroupWithoutDB {
	public static String test(String groupName) throws NoDataAccessException{
		
		LinkedList<Subscriber> subscriberList = new SubscriberParser(groupName).parse();		
		LinkedList<Subscription> subscriptionList = new SubscriptionParser(subscriberList, groupName).parse();	
//													new TEST_SimpleSubscriptionParser(subscriberList, groupName).parse();
		
		fillActivityField(subscriberList, new PostParser(groupName).pasre());		
		
		LinkedList<Subscription> slicedSubscriptionList = new LinkedList<Subscription>(subscriptionList
				.stream().limit(20).collect(Collectors.toList()));

		slicedSubscriptionList.stream().forEach(item -> item.countUp());		
		fillInfoField(slicedSubscriptionList);		

		return TEST_StringOut.subsInfo_statistic_StringOut(slicedSubscriptionList);
	}
	
	private static void fillActivityField(LinkedList<Subscriber> subscribers, LinkedList<Post> activity) {
		HashMap<Integer, Integer> baseLikeCount = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> baseCommentCount = new HashMap<Integer, Integer>();
		
		for (Post post : activity) {
			for (Integer likerId : post.getLikersIdList()) {
				baseLikeCount.merge(likerId, 1, (o, n) -> o + n);
			}
			for (Map.Entry<Integer, Integer> comment : post.getCommentsMap().entrySet()) {
				baseCommentCount.merge(comment.getKey(), comment.getValue(), (o, n) -> o + n);
			}
		}
		
		for (Subscriber sub : subscribers) {
			if (baseLikeCount.containsKey(sub.getId()))
				sub.setLikeCount(baseLikeCount.get(sub.getId()));
			if (baseCommentCount.containsKey(sub.getId()))
				sub.setCommentCount(baseCommentCount.get(sub.getId()));
		}
	}

	private static void fillInfoField(LinkedList<Subscription> handledList) {
		LinkedList<Integer> listId = handledList
									.stream()
									.collect(LinkedList<Integer>::new
											,(l, item) -> l.add(item.getId())
											,(list1, list2) -> list1.addAll(list2));
		LinkedList<GroupFull> groupInfoHolder = new LinkedList<GroupFull>(ParsingMethodHolder.getGroupsInfo(listId));
		
		Iterator<GroupFull> iterator = groupInfoHolder.iterator();
		for (Subscription item : handledList) {
			GroupFull itemGF = iterator.next();
			item.setStringName(itemGF.getName());
			item.setUrlName(itemGF.getScreenName());
			item.setPhotoUrl(itemGF.getPhoto50());
			item.setThisGroupSubsCount(itemGF.getMembersCount());
			item.setDescription(itemGF.getDescription());
		}
	}	
	
	private static Integer getBannedCount(LinkedList<Subscriber> list) {
		Integer count = 0;
		for (Subscriber item : list) {
			if (item.getIsBanned())
				count++;
		}			
		return count;
	}
}