package com.vkgroupstat.vkconnection.parsers;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonArray;
import com.vkgroupstat.Context;
import com.vkgroupstat.constants.VkSdkObjHolder;
import com.vkgroupstat.exception.NoDataAccessException;
import com.vkgroupstat.exception.TooManyRequestException;
import com.vkgroupstat.vkconnection.ParsingMethodHolder;
import com.vkgroupstat.vkconnection.vkentity.Subscriber;

public class SubscriberParser implements VkSdkObjHolder{
	
	private static final Logger LOG = LogManager.getLogger(SubscriberParser.class);
	private final ParsingMethodHolder pmh;
	
	private String groupName;	
	private Integer count;
	private LinkedList<Subscriber> response = new LinkedList<Subscriber>();
	
	public SubscriberParser(String groupName) {
		pmh = Context.getBean(ParsingMethodHolder.class);
		this.groupName = groupName;
		count = pmh.getGroupSubsCount(groupName);
	}
	public LinkedList<Subscriber> parse() throws NoDataAccessException{
		Integer offset = 0;
		ExecutorService executor = Executors.newCachedThreadPool();
		
		try {
			pmh.getSubscribersInfo(groupName, offset, count)
				.getAsJsonArray()
				.forEach(item -> response.add(new Subscriber(item.getAsJsonObject())));
		} catch (TooManyRequestException e) {}
		
		offset += 8000;
		try { Thread.sleep(350); } catch (InterruptedException e) {}	
		
		while(offset < count) {
			for (int i = 0; (i < 3)&&(offset < count); i++) {
				executor.execute(new Request(offset));
				offset += 8000;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				LOG.error(e.getMessage());
			}
		}
		
		executor.shutdown();
		try {
			if (executor.awaitTermination(5, TimeUnit.MINUTES)) {// ДОРАБОТАТЬ
				 executor.shutdownNow();
			}
		} catch (InterruptedException e) {LOG.error(e.getMessage());}
		return response;
	}
	
	class Request implements Runnable{
		Integer offset;
		public Request(Integer offset) {
			this.offset = offset;
		}
		@Override
		public void run(){
			LinkedList<Subscriber> userInfoList = new LinkedList<Subscriber>();				
			JsonArray jsonArray = null;
			boolean flag = true;
			int execeptionCount = 0;
			while (flag) {
				try {
					jsonArray = pmh.getSubscribersInfo(groupName, offset, count).getAsJsonArray();
					flag = false;
				} catch (NoDataAccessException e) {
					return;
				} catch (TooManyRequestException e) {
					LOG.error(e.getMessage());
					execeptionCount++;
					try {
						LOG.error("The request didn't fit in the timing!!! " + execeptionCount + "try.");
						Thread.sleep(3000);//!!
					} catch (InterruptedException e1) {}
				}
			}
			jsonArray.forEach(item -> userInfoList.add(new Subscriber(item.getAsJsonObject())));				
			synchronized (response) {
				response.addAll(userInfoList);
			}
		}		
	}	
}