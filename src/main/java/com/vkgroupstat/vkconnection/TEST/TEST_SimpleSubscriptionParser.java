package com.vkgroupstat.vkconnection.TEST;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.vkgroupstat.vkconnection.ParsingMethodHolder;
import com.vkgroupstat.vkconnection.VkSdkObjHolder;
import com.vkgroupstat.vkconnection.vkentity.SimpleSubscription;
import com.vkgroupstat.vkconnection.vkentity.Subscriber;
import com.vkgroupstat.vkconnection.vkentity.Subscription;

public class TEST_SimpleSubscriptionParser implements VkSdkObjHolder{
	
	Integer batchSize;
	String baseGroupName;
	LinkedList<Subscriber> in; 
	LinkedHashMap<Integer, SimpleSubscription> out = new LinkedHashMap<Integer, SimpleSubscription>(); 
	
	public TEST_SimpleSubscriptionParser(Collection<Subscriber> subscriberSet, String baseGroupName) {
		this.baseGroupName = baseGroupName;
		in = new LinkedList<Subscriber>(subscriberSet);
		batchSize = in.size() / 100;
	}
	
	public LinkedList<Subscription> parse() {
		ExecutorService executor = Executors.newCachedThreadPool();		
		for (int i = 0 ; i < 100 ; i++)
			executor.execute(new Request());		
		executor.shutdown();
		try {
			executor.awaitTermination(3, TimeUnit.MINUTES);
		} catch (InterruptedException e) {System.err.println(e);}
		out.remove(ParsingMethodHolder.getGroupInfo(baseGroupName).getId());
		
		LinkedList<SimpleSubscription> SSList = new LinkedList<SimpleSubscription>(out.values());
		Collections.sort(SSList, (o1, o2) -> o2.getTargetSubsCount().compareTo(o1.getTargetSubsCount()));
		LinkedList<Subscription> responseList = new LinkedList<Subscription>(
				SSList.stream().map(SimpleSubscription::castDown).collect(Collectors.toList()));
		return responseList;
	}
	
	class Request implements Runnable {
		LinkedList<Subscriber> threadIn = new LinkedList<Subscriber>();
		LinkedHashMap<Integer, SimpleSubscription> threadOut = new LinkedHashMap<Integer, SimpleSubscription>();
		List<Integer> temp;
		
		public void run() {	
			while (in.size() > 0) {				
				synchronized (in) {
					for (int i = 0; (in.size() > 0)&&(i < batchSize); i++) {
						threadIn.add(in.remove());
					}
				}				
				while (threadIn.size() > 0) {
					Subscriber subscriber = threadIn.remove();
					if (subscriber.getClosed()||subscriber.getIsBanned())
						continue;	
					temp = ParsingMethodHolder.getUserSubscriptions(subscriber.getId());
					if (temp == null)
						continue;	
					while(temp.size() > 0) {
						Integer subscriptionId = temp.remove(0);
						if (threadOut.containsKey(subscriptionId)) {
							threadOut.get(subscriptionId).incSubsCount();
						} else {
							threadOut.put(subscriptionId, new SimpleSubscription(subscriptionId));
						}
					}					
				}
			}			
			synchronized (out) {				
				for (Map.Entry<Integer, SimpleSubscription> entry : threadOut.entrySet()) {
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