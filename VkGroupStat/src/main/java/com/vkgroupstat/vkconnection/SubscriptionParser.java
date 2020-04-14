package com.vkgroupstat.vkconnection;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.vkgroupstat.vkconnection.vkentity.Subscriber;
import com.vkgroupstat.vkconnection.vkentity.Subscription;

public class SubscriptionParser implements VkSdkObjHolder{
	String baseGroupName;
	LinkedList<Subscriber> in; 
	LinkedHashMap<Integer, Subscription> out = new LinkedHashMap<Integer, Subscription>(); 
	public SubscriptionParser(Collection<Subscriber> subscriberSet, String baseGroupName) {
		this.baseGroupName = baseGroupName;
		in = new LinkedList<Subscriber>(subscriberSet);
	}
	
	public LinkedList<Subscription> parse() {
		Integer threadCount = 50;
		ExecutorService executor = Executors.newCachedThreadPool();		
		for (int i = 0 ; i < threadCount ; i++)
			executor.execute(new Request());		
		executor.shutdown();
		while (!executor.isTerminated()) {//??
			try {
				executor.awaitTermination(500, TimeUnit.MILLISECONDS);
			} catch (Exception e) {
				System.err.println(e);
			}
		}
		out.remove(ParsingMethodHolder.getGroupInfo(baseGroupName).getId());
		LinkedList<Subscription> responseList = new LinkedList<Subscription>(out.values());
		Collections.sort(responseList);
		return responseList;
	}
	
	class Request implements Runnable {
		LinkedList<Subscriber> threadIn = new LinkedList<Subscriber>();
		LinkedHashMap<Integer, Subscription> threadOut = new LinkedHashMap<Integer, Subscription>();
		List<Integer> temp;
		
		public void run() {	
			while (in.size() > 0) {				
				synchronized (in) {
					for (int i = 0; (in.size() > 0)&&(i < 500); i++) {
						threadIn.add(in.remove());
					}
				}				
				while (threadIn.size() > 0) {
					Subscriber subscriber = threadIn.remove();
					if (subscriber.getClosed())
						continue;	
					temp = ParsingMethodHolder.getUserSubscriptions(subscriber.getId());
					if (temp == null)
						continue;	
					while(temp.size() > 0) {
						Integer subscriptionId = temp.remove(0);
						if (threadOut.containsKey(subscriptionId)) {
							threadOut.get(subscriptionId).addSub(subscriber);
						} else {
							threadOut.put(subscriptionId, new Subscription(subscriptionId, subscriber));
						}
					}					
				}
			}			
			synchronized (out) {				
				for (Map.Entry<Integer, Subscription> entry : threadOut.entrySet()) {
					if (out.containsKey(entry.getKey())) {
						out.get(entry.getKey()).concat(entry.getValue());
					} else {
						out.put(entry.getKey(), entry.getValue());
					}          
			    }				
			}
		}
	}
}