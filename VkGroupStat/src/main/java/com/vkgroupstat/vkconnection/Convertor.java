package com.vkgroupstat.vkconnection;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import com.vk.api.sdk.objects.groups.GroupFull;
import com.vkgroupstat.model.Group;

//класс для тестового вывода
public class Convertor {
	public static String groupParse(Group group) {
		String out = "";
		out += VkConnection.getGroupInfo(group.getGroupName()).getName() + "\n<br>";
		out += group.getSavingDate().toString() + "\n<br><br>";
		out += stringOut(collect(group.getRangeList(), 20));		
		return out;
	}
	public static LinkedHashMap<String, Integer> collect(LinkedHashMap<Integer, Integer> handledMap, Integer size){
		
		LinkedList<Integer> keyHolder = new LinkedList<Integer>();
		LinkedList<Integer> valueHolder = new LinkedList<Integer>();
		
		LinkedHashMap<String, Integer> result = new LinkedHashMap<String, Integer>();
		
		int count = 0;
		
		for (Map.Entry<Integer, Integer> entry : handledMap.entrySet()) {
			++count;
			keyHolder.addLast(entry.getKey());
			valueHolder.addLast(entry.getValue());
			if (count > size) {
				break;
			}				
		}
		LinkedList<GroupFull> groupInfoHolder = new LinkedList<GroupFull>(VkConnection.getGroupsInfo(keyHolder, "name"));
		while ((keyHolder.size() > 0) && (valueHolder.size() > 0)) {
			result.put(groupInfoHolder.removeFirst().getName(), valueHolder.removeFirst());
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
