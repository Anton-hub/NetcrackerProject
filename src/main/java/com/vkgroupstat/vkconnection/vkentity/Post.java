package com.vkgroupstat.vkconnection.vkentity;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.vk.api.sdk.objects.wall.WallPostFull;

public class Post {
	private Integer postId = null;
	private Integer ownerId = null;
	private Integer likesCount = null;
	private Integer commentsCount = null;
	private Date date = null;
	
	private LinkedList<Integer> likersIdList = null;
	
	public Post() {}
	public Post(WallPostFull post) {
		postId = post.getId();
		date = new Date(new Long(post.getDate()) * 1000l);
		likesCount = post.getLikes().getCount();
		commentsCount = post.getComments().getCount();
		ownerId = post.getOwnerId();
	}

		
	public Integer getPostId() {
		return postId;
	}
	public Integer getOwnerId() {
		return ownerId;
	}
	public Integer getLikesCount() {
		return likesCount;
	}
	public Integer getCommentsCount() {
		return commentsCount;
	}
	public Date getDate() {
		return date;
	}
	
	public void initLikersIdList(List<Integer> list) {
		likersIdList = new LinkedList<Integer>(list);
	}
	
	
	@Override
	public String toString() {
		return "<br> owner = " + ownerId + " [POST]   id = " + postId + " // date = " + date + " // likes = " + likesCount 
				+ " // comments = " + commentsCount + " // likers = " 
				+ likersIdList.stream().limit(5).map(Object::toString).collect(Collectors.joining(",")) + " ...";
	}
}
