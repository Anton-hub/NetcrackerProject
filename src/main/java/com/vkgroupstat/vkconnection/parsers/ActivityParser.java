package com.vkgroupstat.vkconnection.parsers;

import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vkgroupstat.vkconnection.ParsingMethodHolder;
import com.vkgroupstat.vkconnection.vkentity.Post;

@Component
public class ActivityParser {
	
	private final ParsingMethodHolder pmh;
	
	@Autowired
	public ActivityParser(ParsingMethodHolder pmh) {
		this.pmh = pmh;
	}
	
	/**
	 * a function that initializes a field (in Post) that stores information about like
	 * @param post
	 */
	public void initLikersList(Post post) {
		LinkedList<Integer> likersList = new LinkedList<Integer>();
		Integer offset = 0;
		while (post.getLikesCount() > offset) {
			likersList.addAll(pmh.getLikersList(post.getOwnerId(), post.getPostId(), offset));
			offset += 1000;
		}
		post.initLikersIdList(likersList);
	}
	
	/**
	 * a function that initializes a field (in Post) that stores information about comments
	 * @param post
	 */
	public void initCommentsMap(Post post) {
		post.initCommentsMap(getCommentsMap(post));		
	}
	
	/**
	 * function that parses the json response with post comments
	 * and puts this response on the map
	 */
	private LinkedHashMap<Integer, Integer> getCommentsMap(Post post) {
		LinkedHashMap<Integer, Integer> commentCountMap = 
				new LinkedHashMap<Integer, Integer>();
		Integer offset = 0;
		Boolean flag = true;
		while (flag) {
			String stringJson = pmh.getCommentsJson(post.getOwnerId(), post.getPostId(), offset);
			JsonObject json = new JsonParser().parse(stringJson).getAsJsonObject().get("response").getAsJsonObject();
			JsonArray listJsonComments = null;
			
			if (json.has("items")) {
				listJsonComments = json.get("items").getAsJsonArray();
				if (listJsonComments.size() < 100)
					flag = false;
			
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
				offset += 100;
			} else {
				return null;
			} 
		}
		return commentCountMap;
	}	
}
