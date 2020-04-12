package com.vkgroupstat.vkconnection;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

import com.vkgroupstat.model.Group;
import com.vkgroupstat.vkconnection.vkentity.Subscriber;
import com.vkgroupstat.vkconnection.vkentity.Subscription;

public class GroupCollector {
	public static Group collect(String groupName) {
		long startTime = new Date().getTime();
		
		LinkedList<Subscriber> subscriberList = new GroupInfoParser(groupName).parse();		
		LinkedList<Subscription> subscriptionList = new СoncurrentParse(subscriberList).parse();		
		LinkedList<Subscription> slicedSubscriptionList = subscriptionList
				.stream().limit(100).collect(Collectors.toList(LinkedList<Subscription>::new))
				
//			subscriptionMap
//				.entrySet()
//				.stream()
//				.limit(100)
//				.collect(Collectors.toMap(Map.Entry::getKey
//										 ,Map.Entry::getValue
//										 ,(oldValue, newValue) -> oldValue
//										 ,LinkedHashMap::new
//										 ));
		
		
		
		
		
		
		
		
		
		LinkedList<Integer> listUsers = ParsingMethodHolder.getGroupVkSdk(groupName);
		Integer[] arrayUsers = new Integer[listUsers.size()];
		arrayUsers = listUsers.toArray(new Integer[0]);

		LinkedHashMap<Integer, Integer>	rengeList = new СoncurrentParse(ParsingMethodHolder.getGroupVkSdk(groupName)).start();
		rengeList.remove(ParsingMethodHolder.getGroupInfo(groupName).getId());//удаляет целевую группу из списка
		if (rengeList.size() > 1000) {
			mapSize = 1000;
		} else {
			mapSize = 20;
		}
		LinkedHashMap<Integer, Integer> cuteRengeList = 
				rengeList.entrySet()
				.stream()
				.limit(mapSize)
				.collect(Collectors.toMap(Map.Entry::getKey
						, Map.Entry::getValue
						, (oldValue, newValue) -> oldValue
						, LinkedHashMap::new
						)
						);
		System.out.println("Download and collect group data completed in " + (new Date().getTime() - startTime) + " miliseconds! " + listUsers.size());
		return new Group(groupName, arrayUsers, cuteRengeList);
	}
}
