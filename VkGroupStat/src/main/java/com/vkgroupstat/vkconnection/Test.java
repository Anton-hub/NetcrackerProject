package com.vkgroupstat.vkconnection;

import static com.vkgroupstat.vkconnection.Convertor.collect;
import static com.vkgroupstat.vkconnection.Convertor.stringOut;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

import com.vkgroupstat.vkconnection.vkentity.Subscriber;
import com.vkgroupstat.vkconnection.vkentity.Subscription;

public class Test {
	public static String test(String groupName) {
		Integer subscriberMapC = 0;
		Integer filtredMapC = 0;
		
		LinkedList<Subscriber> subscriberList = new GroupInfoParser(groupName).parse();		
		LinkedList<Subscription> subscriptionList = new СoncurrentParse(subscriberList).parse();		
		LinkedList<Subscription> slicedSubscriptionList = subscriptionList
				.stream().limit(100).collect(Collectors.toList(LinkedList<Subscription>::new))
		
		
		LinkedHashMap<Integer, Integer> response = new LinkedHashMap<Integer, Integer>();
		int count = 0;
		for (Map.Entry<Integer, Subscription> entry : slicedSubscriptionMap.entrySet()) {
			response.put(entry.getKey(), entry.getValue().sizeList());
			if (count > 100) {
				break;
			}
			count++;
		}			
		System.err.println("Befor filter " + subscriberMapC +
							"\nFiltered " + filtredMapC);
		return stringOut(collect(response, 100));
		
//		Function<Map.Entry<Integer,LinkedList<Integer>>, Integer> getSize = (value) -> value.getValue().size();
//		Function<Map.Entry<Integer,LinkedList<Integer>>, Integer> getKey = (key) -> key.getKey();
//		Supplier<LinkedHashMap<Integer, Integer>> create = () -> new LinkedHashMap<Integer, Integer>();		
//		LinkedHashMap<Integer, Integer> cuteRengeList = 
//				subscriptionMap.entrySet()
//			            .stream()
//			            .limit(20)
//			            .collect(Collectors.toMap(getKey
//			            						, getSize
//			            						, (oldValue, newValue) -> oldValue
//			            						, create
//			            						));
	}
}
