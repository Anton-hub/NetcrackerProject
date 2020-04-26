package com.vkgroupstat.vkconnection.TEST;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.wall.WallComment;
import com.vk.api.sdk.queries.likes.LikesType;
import com.vkgroupstat.vkconnection.ParsingMethodHolder;
import com.vkgroupstat.vkconnection.VkSdkObjHolder;
import com.vkgroupstat.vkconnection.vkentity.Post;

public class TEST_activityParser implements VkSdkObjHolder{
	
	private static final Logger LOG = LogManager.getLogger(TEST_activityParser.class);
	
	public static String wallGet() {
		LinkedList<Post> list = new LinkedList<Post>();
		ParsingMethodHolder.getWallPosts("molodoipeterburg", 0)
			.forEach(item -> list.add(new Post(item)));
		list.forEach(item -> item.initLikersIdList(getLikersList(item.getOwnerId(), item.getPostId(), item.getLikesCount())));
		
		LOG.info("end!");
		
		return test();//list.toString();
	}
	
	public static String test() {
		return getPartComments(-189492672, 2180, 0).toString()  + "<br><br>" + getPartCommentsString(-189492672, 2266, 0);
	}
	
	public static HashMap<Integer, Integer> getUsersComment(Integer ownerId, Integer postId){
		HashMap<Integer, Integer> map = new HashMap<Integer,Integer>();
		LinkedList<WallComment> listComments = new LinkedList<WallComment>();
		List<WallComment> temp;
		Integer offset = 0;
		do{
			temp = getPartComments(ownerId, postId, offset);
			if (temp != null)
				listComments.addAll(temp);
			offset += 100;
		} while (temp.size() > 0);
		for (WallComment item : listComments) {
			map.merge(item.getFromId(), 1, (oldV, newV) -> oldV + newV);
		}
		return null;
	}
	
	public static LinkedList<Integer> getLikersList(Integer ownerId, Integer postId, Integer count){
		LinkedList<Integer> likersList = new LinkedList<Integer>();
		Integer offset = 0;
		while (count > offset) {
			likersList.addAll(getPartLikersList(ownerId, postId, offset));
			offset += 1000;
		}
		return likersList;
	}
	
	public static List<WallComment> getPartComments(Integer ownerId,Integer postId, Integer offset){
		try {
			return VK
					.wall()
					.getComments(S_ACTOR, postId)
					.ownerId(ownerId)
					.count(2)
					.offset(offset)
					.previewLength(1)
					.unsafeParam("thread_items_count", 10)
					.unsafeParam("v", "5.103")
					.execute()
					.getItems();
		} catch (ApiException | ClientException e) {
			LOG.error(e);
			return null;
		}
	}
	
	public static String getPartCommentsString(Integer ownerId,Integer postId, Integer offset){
		try {
			return VK
					.wall()
					.getComments(S_ACTOR, postId)
					.ownerId(ownerId)
					.count(2)
					.offset(offset)
					.previewLength(1)
					.unsafeParam("thread_items_count", 10)
					.unsafeParam("v", "5.103")
					.executeAsString();
		} catch (ClientException e) {
			LOG.error(e);
			return null;
		}
	}
	
	public static List<Integer> getPartLikersList(Integer ownerId, Integer postId, Integer offset){
		try {
			return VK
					.likes()
					.getList(S_ACTOR, LikesType.POST)
					.itemId(postId)
					.ownerId(ownerId)
					.offset(offset)
					.count(1000)
					.execute()
					.getItems();
		} catch (ApiException | ClientException e) {
			LOG.error(e);
			return null;
		}
	}
}
