package com.vkgroupstat.vkconnection.parsers;

import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vk.api.sdk.objects.wall.WallPostFull;
import com.vkgroupstat.Context;
import com.vkgroupstat.exception.NoDataAccessException;
import com.vkgroupstat.vkconnection.ParsingMethodHolder;
import com.vkgroupstat.vkconnection.vkentity.Post;

public class PostParser {
	
	private static final Logger LOG = LogManager.getLogger(PostParser.class);
	private final ActivityParser activity;
	private final ParsingMethodHolder pmh;
	
	private String groupName;
	private LinkedList<Post> response = new LinkedList<Post>();
	
	public PostParser(String groupName) {
		activity = Context.getBean(ActivityParser.class);
		pmh = Context.getBean(ParsingMethodHolder.class);
		this.groupName = groupName;
	}
	
	/**
	 * loads a list of posts;
	 * their likes and comments;
	 * collects this in the list of Post objects;
	 * @return
	 */
	public LinkedList<Post> pasre(){
		LinkedList<Post> beforeFill;
		try {
			beforeFill = initPostsList();
		} catch (NoDataAccessException e) {
			return null;
		}
		ExecutorService executor = Executors.newCachedThreadPool();
		while (beforeFill.size()> 0) {
			LinkedList<Post> transferArg = new LinkedList<Post>();
			int count = 0;
			while ((count < 10)&&(beforeFill.size() > 0)) {
				transferArg.add(beforeFill.remove());
				count++;
			}
			executor.execute(new Filler(transferArg));
		}
		executor.shutdown();
		try {
			executor.awaitTermination(2, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			LOG.error("Posts take too long to collect!");
			LOG.error(e.getMessage());
			return null;
		}
//		response = new LinkedList<Post>(response.stream().sorted((o1, o2) -> new Long(o2.getDate().getTime()).compareTo(new Long(o1.getDate().getTime()))).collect(Collectors.toList()));
		return response;
	}
	
	/**
	 * function that gets a list of group posts 
	 * (with posts no later than a month ago)
	 */
	private LinkedList<Post> initPostsList() throws NoDataAccessException{
		LinkedList<Post> postsList = new LinkedList<Post>();
		Boolean flag = true;
		Integer offset = 0;
		while (flag) {
			LinkedList<WallPostFull> postFullList = new LinkedList<WallPostFull>(pmh.getWallPosts(groupName, offset));
			for (WallPostFull post : postFullList) {
				postsList.add(new Post(post));
			}
			if ((postFullList.size() > 99)
				&&(new Date().getTime() - postsList.getLast().getDate().getTime() < 2678400000l)
				&&(postsList.size() < 300)
				){
				offset += 100;				
			} else {
				flag = false;
			}		
		}		
		return postsList;
	}
	
	class Filler implements Runnable{
		private LinkedList<Post> in;
		public Filler(LinkedList<Post> list) {
			in = list;
		}
		public void run() {
			for (Post post : in) {		
				activity.initLikersList(post);
				activity.initCommentsMap(post);
			}
			synchronized (response) {
				response.addAll(in);
			}
		}
	}
}