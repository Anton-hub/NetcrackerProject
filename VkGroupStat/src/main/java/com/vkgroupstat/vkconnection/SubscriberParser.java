package com.vkgroupstat.vkconnection;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.google.gson.JsonArray;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vkgroupstat.vkconnection.vkentity.Subscriber;

public class SubscriberParser implements VkSdkObjHolder{
	
	private String groupName;	
	private Integer count;
	private LinkedList<Subscriber> response = new LinkedList<Subscriber>();
	
	public SubscriberParser(String groupName) {
		this.groupName = groupName;
		count = ParsingMethodHolder.getGroupSubsCount(groupName);
	}
	public LinkedList<Subscriber> parse(){
		Integer offset = 0;
		ExecutorService executor = Executors.newCachedThreadPool();
		
		while(offset < count) {
			for (int i = 0; (i < 3)&&(offset < count); i++) {
				executor.execute(new Request(offset));
				offset += 8000;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.err.println("interrupt!");
			}
		}
		
		executor.shutdown();
		while (!executor.isTerminated()) {//??
			try {
				executor.awaitTermination(500, TimeUnit.MILLISECONDS);
			} catch (Exception e) {
				System.err.println(e);
			}
		}
		return response;
	}
	
	public LinkedList<Subscriber> getInfoRequest(Integer offset) {
		LinkedList<Subscriber> userInfoList = new LinkedList<Subscriber>();
		JsonArray response = null;
		boolean flag = true;
		int execeptionCount = 0;
		while (flag) {
			try {
				response = VK_U.execute()
							   .storageFunction(U_ACTOR, "getGroupSubsInfo")
							   .unsafeParam("offset", offset)
							   .unsafeParam("count", count)
							   .unsafeParam("groupName", groupName)
							   .execute()
							   .getAsJsonArray();
				response.forEach(item -> userInfoList.add(new Subscriber(item.getAsJsonObject())));
				flag = false;
			} catch (ClientException | ApiException e) {
				System.err.println(e.getMessage());
				execeptionCount++;
				try {
					System.err.println("The request didn't fit in the timing!!! " + execeptionCount + "try.");
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
		public void run() {
			LinkedList<Subscriber> userInfoMap = getInfoRequest(offset);
			synchronized (response) {
				response.addAll(userInfoMap);
			}
		}		
	}	
}