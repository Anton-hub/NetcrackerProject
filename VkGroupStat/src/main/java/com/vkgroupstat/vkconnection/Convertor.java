package com.vkgroupstat.vkconnection;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.vk.api.sdk.objects.groups.GroupFull;
import com.vkgroupstat.model.Group;
import com.vkgroupstat.vkconnection.vkentity.Subscription;

//класс для тестового вывода
public class Convertor {
//	public static String groupParse(Group group) {
//		String out = "";
//		out += ParsingMethodHolder.getGroupInfo(group.getGroupName()).getName() + "\n<br>";
//		out += group.getSavingDate().toString() + "\n<br><br>";
//		out += stringOut(collect(group.getRangeList(), 20));		
//		return out;
//	}
	public static LinkedHashMap<String, Integer> collect(LinkedList<Subscription> handledList){
		
		LinkedList<Subscription> local = new LinkedList<Subscription>(handledList);
		LinkedHashMap<String, Integer> result = new LinkedHashMap<String, Integer>();
		
		int c = 0;
		LinkedList<Integer> list = handledList.stream().collect(LinkedList<Integer>::new
																,(l, item) -> l.add(item.getId())
																,(list1, list2) -> list1.addAll(list2));
		
		LinkedList<GroupFull> groupInfoHolder = new LinkedList<GroupFull>(ParsingMethodHolder.getGroupsInfo(list));
		
		for (GroupFull item : groupInfoHolder) {
			System.out.println(++c + ". " + item.getName());
		}
		
		while (groupInfoHolder.size() > 0) {
			result.put(groupInfoHolder.removeFirst().getName(), local.removeFirst().sizeList());
		}
		return result;
	}
	
	public static String stringOut(LinkedHashMap<String, Integer> handledMap) {
		String response = "";
		int count = 0;
		for (Map.Entry<String, Integer> entry : handledMap.entrySet())
			response += ++count + ".  " + entry.getKey() + " - " + entry.getValue() + "<br>";
		return response;
	}
}
