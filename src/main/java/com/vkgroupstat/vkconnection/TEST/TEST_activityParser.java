package com.vkgroupstat.vkconnection.TEST;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import com.vk.api.sdk.objects.wall.WallComment;
import com.vk.api.sdk.queries.likes.LikesType;
import static com.vkgroupstat.vkconnection.ParsingMethodHolder.*;

import com.vkgroupstat.vkconnection.VkSdkObjHolder;
import com.vkgroupstat.vkconnection.parsers.PostParser;
import com.vkgroupstat.vkconnection.vkentity.Post;

public class TEST_activityParser implements VkSdkObjHolder{
	
	private static final Logger LOG = LogManager.getLogger(TEST_activityParser.class);
	
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
		LinkedList<Post> list = new PostParser("pikabu").pasre();
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
		LinkedHashMap<String, Integer> map = keyIntToString(foo(getCommentsJson(-189492672, 2359, 0)));
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
				LOG.info("mark");
				JsonObject temp = item.getAsJsonObject();
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
			likersList.addAll(getLikersList(ownerId, postId, offset));
			offset += 1000;
		}
		return likersList;
	}
	

}