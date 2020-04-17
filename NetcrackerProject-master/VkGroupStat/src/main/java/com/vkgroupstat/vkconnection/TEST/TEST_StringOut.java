package com.vkgroupstat.vkconnection.TEST;

import com.vkgroupstat.model.Group;
import com.vkgroupstat.vkconnection.vkentity.Subscription;

import java.util.LinkedHashMap;
import java.util.LinkedList;

//класс для тестового вывода
public class TEST_StringOut {
	
	public static String groupToString(Group group) {
		String response = "";
		response += "<h1>" + group.getStringName()+"</h1>";
		response += "<h2>" + group.getUrlName() + "</h2>";
		response += "<h3>" + group.getCreateDate() + "</h3><br>";
		response += "<h4>Статистика группы:<br><br>" + group.getBaseStat() + "</h4><br>";
		response += subsInfo_statistic_StringOut(group.getRangeList());
		return response;
	}
	
	public static LinkedHashMap<String, Integer> rangeList_StringOut(LinkedList<Subscription> handledList){
		LinkedHashMap<String, Integer> result = new LinkedHashMap<String, Integer>();
		int count = 0;
		for (Subscription item : handledList)
			result.put(++count + ". " + item.getStringName(), item.sizeList());
		return result;
	}
	
	public static String subsInfo_statistic_StringOut(LinkedList<Subscription> list) {
		String response = "";
		int count = 0;
		for (Subscription item : list)
			response += ++count + ". " + item + "<br>";
		return response;
	}
}
