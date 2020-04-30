package com.vkgroupstat.vkconnection.parsers;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonArray;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vkgroupstat.constants.VkSdkObjHolder;
import com.vkgroupstat.exception.NoDataAccessException;
import com.vkgroupstat.vkconnection.ParsingMethodHolder;
import com.vkgroupstat.vkconnection.vkentity.Subscriber;

public class SubscriberParser implements VkSdkObjHolder{
	
	private static final Logger LOG = LogManager.getLogger(SubscriberParser.class);
	
	private String groupName;	
	private Integer count;
	private LinkedList<Subscriber> response = new LinkedList<Subscriber>();
	
	public SubscriberParser(String groupName) {
		this.groupName = groupName;
		count = ParsingMethodHolder.getGroupSubsCount(groupName);
	}
	public LinkedList<Subscriber> parse() throws NoDataAccessException{
		Integer offset = 0;
		ExecutorService executor = Executors.newCachedThreadPool();
		
		response.addAll(getInfoRequest(0));
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
			executor.awaitTermination(3, TimeUnit.MINUTES); // ДОРАБОТАТЬ
		} catch (InterruptedException e) {LOG.error(e.getMessage());}
		return response;
	}
	
	public LinkedList<Subscriber> getInfoRequest(Integer offset) throws NoDataAccessException{
		LinkedList<Subscriber> userInfoList = new LinkedList<Subscriber>();
		JsonArray response = null;
		boolean flag = true;
		int execeptionCount = 0;
		while (flag) {
			try {
				response = VK.execute()
							   .storageFunction(U_ACTOR, "getGroupSubsInfo")
							   .unsafeParam("offset", offset)
							   .unsafeParam("count", count)
							   .unsafeParam("groupName", groupName)
							   .unsafeParam("token", U_TOKEN)
							   .execute()
							   .getAsJsonArray();
				response.forEach(item -> userInfoList.add(new Subscriber(item.getAsJsonObject())));
				flag = false;
			} catch ( ApiException e) {
				LOG.error("Нет доступа к парсингу подписчиков, возможно, в группе, закрыт список подписчиков");
				throw new NoDataAccessException();
			} catch (ClientException e) {
				LOG.error(e.getMessage());
				execeptionCount++;
				try {
					LOG.error("The request didn't fit in the timing!!! " + execeptionCount + "try.");
					Thread.sleep(3000);//!!
				} catch (InterruptedException e1) {}
			} 
		}		
		return userInfoList;
	}
	
	class Request implements Runnable{
		Integer offset;
		public Request(Integer offset) {
			this.offset = offset;
		}
		@Override
		public void run(){
			LinkedList<Subscriber> userInfoMap;
			try {
				userInfoMap = getInfoRequest(offset);
			} catch (NoDataAccessException e) {	return; }
			synchronized (response) {
				response.addAll(userInfoMap);
			}
		}		
	}	
}