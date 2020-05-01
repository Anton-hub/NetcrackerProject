package com.vkgroupstat.vkconnection.fillers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.vk.api.sdk.objects.groups.GroupFull;
import com.vkgroupstat.vkconnection.ParsingMethodHolder;
import com.vkgroupstat.vkconnection.vkentity.Post;
import com.vkgroupstat.vkconnection.vkentity.Subscriber;
import com.vkgroupstat.vkconnection.vkentity.Subscription;

@Component
public class GroupFiledFiller {
	public void fillActivityField(LinkedList<Subscriber> subscribers, LinkedList<Post> activity) {
		if (activity == null)
			return;
		HashMap<Integer, Integer> baseLikeCount = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> baseCommentCount = new HashMap<Integer, Integer>();
		
		for (Post post : activity) {
			for (Integer likerId : post.getLikersIdList()) {
				baseLikeCount.merge(likerId, 1, (o, n) -> o + n);
			}
			for (Map.Entry<Integer, Integer> comment : post.getCommentsMap().entrySet()) {
				baseCommentCount.merge(comment.getKey(), comment.getValue(), (o, n) -> o + n);
			}
		}
		
		for (Subscriber sub : subscribers) {
			if (baseLikeCount.containsKey(sub.getId()))
				sub.setLikeCount(baseLikeCount.get(sub.getId()));
			if (baseCommentCount.containsKey(sub.getId()))
				sub.setCommentCount(baseCommentCount.get(sub.getId()));
		}
	}

	public void fillInfoField(LinkedList<Subscription> handledList) {
		LinkedList<Integer> listId = handledList
									.stream()
									.collect(LinkedList<Integer>::new
											,(l, item) -> l.add(item.getId())
											,(list1, list2) -> list1.addAll(list2));
		LinkedList<GroupFull> groupInfoHolder = new LinkedList<GroupFull>(ParsingMethodHolder.getGroupsInfo(listId));
		
		Iterator<GroupFull> iterator = groupInfoHolder.iterator();
		for (Subscription item : handledList) {
			GroupFull itemGF = iterator.next();
			item.setStringName(itemGF.getName());
			item.setUrlName(itemGF.getScreenName());
			item.setPhotoUrl(itemGF.getPhoto50());
			item.setThisGroupSubsCount(itemGF.getMembersCount());
			item.setDescription(itemGF.getDescription());
		}
	}	
	
	public Integer getBannedCount(LinkedList<Subscriber> list) {
		Integer count = 0;
		for (Subscriber item : list) {
			if (item.getIsBanned())
				count++;
		}			
		return count;
	}
}
