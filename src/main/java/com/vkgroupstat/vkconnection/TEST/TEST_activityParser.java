package com.vkgroupstat.vkconnection.TEST;

import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
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
		list.forEach(item -> item.initLikersIdList(getLikersList(item.getOwnerId(), item.getPostId(), 0)));
		
		
		
		return list.toString();
	}
	
	public static List<Integer> getLikersList(Integer ownerId, Integer itemId, Integer offset){
		try {
			return VK
					.likes()
					.getList(S_ACTOR, LikesType.POST)
					.itemId(itemId)
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
