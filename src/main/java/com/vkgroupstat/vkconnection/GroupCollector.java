package com.vkgroupstat.vkconnection;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.vk.api.sdk.objects.groups.GroupFull;
import com.vkgroupstat.model.Group;
import com.vkgroupstat.vkconnection.vkentity.GroupStat;
import com.vkgroupstat.vkconnection.vkentity.Subscriber;
import com.vkgroupstat.vkconnection.vkentity.Subscription;
import com.vkgroupstat.vkconnection.vkentity.SubscriptionStat;

@Service
public class GroupCollector {
	
	private static final Logger LOG = LogManager.getLogger(GroupCollector.class);
	
	public Group collect(String groupName) {
		long startTime = new Date().getTime();		

		LinkedList<Subscriber> subscriberList = new SubscriberParser(groupName).parse();		
		LinkedList<Subscription> subscriptionList = new SubscriptionParser(subscriberList, groupName).parse();	
//													new TEST_SimpleSubscriptionParser(subscriberList, groupName).parse();
		
		LinkedList<Subscription> slicedSubscriptionList = new LinkedList<Subscription>(subscriptionList
				.stream().limit(20).collect(Collectors.toList()));

		slicedSubscriptionList.stream().forEach(item -> item.countUp());
		
		fillNameField(slicedSubscriptionList);	
		fillSubsCount(slicedSubscriptionList);
		
		GroupFull baseGrInf = ParsingMethodHolder.getGroupInfo(groupName);
		GroupStat groupStat = new GroupStat(subscriberList
											, baseGrInf.getMembersCount()
											, getBannedCount(subscriberList));		
		
		LOG.info("Download and collect group data completed in " + (new Date().getTime() - startTime) + " miliseconds!");

		return new Group(baseGrInf.getId()
						, groupName
						, baseGrInf.getName()
						, groupStat
						, slicedSubscriptionList);
	}

	private void fillNameField(LinkedList<Subscription> handledList) {
		LinkedList<Integer> listId = handledList.stream().collect(LinkedList<Integer>::new,
				(l, item) -> l.add(item.getId()), (list1, list2) -> list1.addAll(list2));
		LinkedList<GroupFull> groupInfoHolder = new LinkedList<GroupFull>(ParsingMethodHolder.getGroupsInfo(listId));
		Iterator<GroupFull> iterator = groupInfoHolder.iterator();
		for (Subscription item : handledList) {
			GroupFull itemGF = iterator.next();
			item.setStringName(itemGF.getName());
			item.setUrlName(itemGF.getScreenName());
			item.setPhotoUrl(itemGF.getPhoto50());
		}
	}
	
	private Integer getBannedCount(LinkedList<Subscriber> list) {
		Integer count = 0;
		for (Subscriber item : list) {
			if (item.getIsBanned())
				count++;
		}			
		return count;
	}
	
	private void fillSubsCount(LinkedList<Subscription> handledList) {
		for (Subscription item : handledList)
			item.setThisGroupSubsCount(ParsingMethodHolder.getGroupSubsCount(item.getUrlName()));
	}
}