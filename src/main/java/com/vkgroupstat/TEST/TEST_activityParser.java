package com.vkgroupstat.TEST;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import com.vkgroupstat.Context;
import com.vkgroupstat.constants.VkSdkObjHolder;
import com.vkgroupstat.vkconnection.ParsingMethodHolder;
import com.vkgroupstat.vkconnection.parsers.PostParser;
import com.vkgroupstat.vkconnection.vkentity.Post;

public class TEST_activityParser implements VkSdkObjHolder{
	
	private static final Logger LOG = LogManager.getLogger(TEST_activityParser.class);
	private static final ParsingMethodHolder pmh = Context.getBean(ParsingMethodHolder.class);
	
	public static String wallGet() {
//		LinkedList<Post> list = new LinkedList<Post>();
//		getWallPosts("molodoipeterburg", 0)
//			.forEach(item -> list.add(new Post(item)));
//		list.forEach(item -> item.initLikersIdList(getLikersList(item.getOwnerId(), item.getPostId(), item.getLikesCount())));
//		
//		LOG.info("end!");
//		//return test();
//		return list.toString();
		//return getPartCommentsString(-189492672, 2266, 0);
		LinkedList<Post> list = new PostParser("molodoipeterburg").pasre();
		return list.toString() + "<br>" + list.size();
	}
	
	public static LinkedHashMap<String, Integer> keyIntToString(LinkedHashMap<Integer, Integer> inMap){
		LinkedHashMap<String, Integer> outMap = new LinkedHashMap<String, Integer>();
		for (Map.Entry<Integer, Integer> item : inMap.entrySet()) {
			outMap.put("<br>" + getName(item.getKey()), item.getValue());
		}
		return outMap;
	}
	
	public static String getName(Integer id) {
		try {
			UserXtrCounters temp = VK.users().get(S_ACTOR).userIds(id.toString()).execute().get(0);
			String s = temp.getFirstName() + " " + temp.getLastName();
			return s;
		}catch (Exception e) {
			return null;
		}
	}
	
	public static String test() {
		LinkedHashMap<String, Integer> map = keyIntToString(foo(pmh.getCommentsJson(-189492672, 2359, 0)));
		return map.toString();
		//return foo(getPartCommentsString(-189492672, 2266, 0)).toString();
	}
	
	public static LinkedHashMap<Integer, Integer> foo(String stringJson) {
		LinkedHashMap<Integer, Integer> commentCountMap = 
				new LinkedHashMap<Integer, Integer>();
		
		JsonObject json = new JsonParser().parse(stringJson).getAsJsonObject().get("response").getAsJsonObject();
		JsonArray listJsonComments = null;
		if (json.has("items")) {
			listJsonComments = json.get("items").getAsJsonArray();
			for (JsonElement item : listJsonComments) {
				JsonObject temp = item.getAsJsonObject();
				if (temp.has("from_id"))
					commentCountMap.merge(temp.get("from_id").getAsInt(), 1, (o, n) -> o + n);
				JsonArray answerList = temp.get("thread").getAsJsonObject().get("items").getAsJsonArray();
				if (answerList.size() > 0) {
					for (JsonElement answerItem : answerList) {
						commentCountMap.merge(answerItem.getAsJsonObject().get("from_id").getAsInt(), 1,
								(o, n) -> o + n);
					}
				}
			}
		} else {
			return null;
		}
		return commentCountMap;
	}
	
	
	
	
	public static LinkedList<Integer> getFullLikersList(Integer ownerId, Integer postId, Integer count){
		LinkedList<Integer> likersList = new LinkedList<Integer>();
		Integer offset = 0;
		while (count > offset) {
			likersList.addAll(pmh.getLikersList(ownerId, postId, offset));
			offset += 1000;
		}
		return likersList;
	}
	

}