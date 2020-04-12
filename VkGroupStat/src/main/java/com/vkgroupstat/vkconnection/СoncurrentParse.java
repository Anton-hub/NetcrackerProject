package com.vkgroupstat.vkconnection;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.springframework.util.comparator.Comparators;

import com.vkgroupstat.vkconnection.vkentity.Subscriber;
import com.vkgroupstat.vkconnection.vkentity.Subscription;

public class СoncurrentParse implements VkSdkObjHolder{
	LinkedList<Subscriber> in; 
	LinkedHashMap<Integer, Subscription> out = new LinkedHashMap<Integer, Subscription>(); 
	//////////////////////////
	Integer baseCountSubs = 0;
	Double baseTime = 0d;
	String log ="";
	//////////////////////////
	public СoncurrentParse(Collection<Subscriber> subscriberSet) {
		in = new LinkedList<Subscriber>(subscriberSet);
	}
	
	public LinkedList<Subscription> parse() {
		////////////////////////////
		long s = new Date().getTime();
		Integer threadCount = 100;
		////////////////////////////
		ExecutorService executor = Executors.newCachedThreadPool();		
		for (int i = 0 ; i < threadCount ; i++)
			executor.execute(new Request());		
		executor.shutdown();
		while (!executor.isTerminated()) {//??
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				System.err.println(e);
			}
		}
		///////////////////////
		System.out.println(log);
		System.err.println("Parse, on " + threadCount + " threads, is completed in " + ((double)(new Date().getTime() - s))/1000 + " seconds!"
							+ "\nAverage number of processed subscribers - " + baseCountSubs/threadCount
							+ "\nAverage time of thread complete - " + baseTime/(threadCount*1000) + " seconds.");
		//////////////////////
		return out
				.values()
				.stream()
				.sorted()
				.collect(Collectors.toList(LinkedList<Subscription>::new));
	}
	
	class Request implements Runnable {
		LinkedList<Subscriber> threadIn = new LinkedList<Subscriber>();
		LinkedHashMap<Integer, Subscription> threadOut = new LinkedHashMap<Integer, Subscription>();
		List<Integer> temp;
		int count = 0;///////////////
		
		public void run() {
			Date t1 = new Date();/////////////////		
			while (in.size() > 0) {				
				synchronized (in) {
					for (int i = 0; (in.size() > 0)&&(i < 200); i++) {
						threadIn.add(in.remove());
					}
				}				
				while (threadIn.size() > 0) {
					count++;
					Subscriber subscriber = threadIn.remove();
					if (subscriber.getClosed())
						continue;	
					temp = ParsingMethodHolder.getUserSubsVkSdk(subscriber.getId());									
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
			//////////////////////////////
			Date t2 = new Date();
			synchronized (baseCountSubs) {
				baseCountSubs += count;
			}
			synchronized (baseTime) {
				baseTime += new Double(t2.getTime() - t1.getTime());
			}
			synchronized (log) {
				String s = Thread.currentThread().getName();
				log += new Date() + " : [" + s.substring(s.length()-15, s.length()) + "] is END! which processed " + count 
						+ " users in " + (t2.getTime() - t1.getTime())/1000 + " seconds\n";
			}
			//////////////////////////////
		}
	}
}