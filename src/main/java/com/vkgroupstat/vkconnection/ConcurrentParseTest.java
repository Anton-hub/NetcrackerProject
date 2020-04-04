package com.vkgroupstat.vkconnection;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

//В РАЗРАБОТКЕ!!!
public class ConcurrentParseTest {
	LinkedList<Integer> in; 
	LinkedHashMap<Integer, Integer> out = new LinkedHashMap<Integer, Integer>(); 
	String token1 = "ad2deb63ad2deb63ad2deb63ebad5d5087aad2dad2deb63f3abd5d8d7b7c18a57ddd0c0";
	Integer id1 = 7388132;
	String token2 = "b6166003b6166003b61660038eb666dbeabb616b6166003e8905f92ed1c7d66665201ff";
	Integer id2 = 7388137;
	String token3 = "ceae46b0ceae46b0ceae46b000cedefd5bcceaeceae46b09028796c9682837c82621528";
	Integer id3 = 7388139;
//	String token4 = "ad2deb63ad2deb63ad2deb63ebad5d5087aad2dad2deb63f3abd5d8d7b7c18a57ddd0c0";
//	Integer id4 = 7388132;
//	String token5 = "ad2deb63ad2deb63ad2deb63ebad5d5087aad2dad2deb63f3abd5d8d7b7c18a57ddd0c0";
//	Integer id5 = 7388132;
	
	public ConcurrentParseTest(LinkedList<Integer> list) {
		in = new LinkedList<Integer>(list);
	}
	
	public LinkedHashMap<Integer, Integer> start() {
		ExecutorService executor = Executors.newCachedThreadPool();
		executor.execute(new Worker(id1, token1));
		executor.execute(new Worker(id2, token2));
		executor.execute(new Worker(id3, token3));
		
		
		executor.shutdown();
		while (!executor.isTerminated()) {
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				System.err.println(e);
			}
		}
		System.out.println(out.size());
		//вывод новой отсортированной карты на основе out
		return 	out.entrySet()
				.stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,(oldValue, newValue) -> oldValue, LinkedHashMap::new));
	}
	
	class Worker implements Runnable {
		LinkedList<Integer> threadIn = new LinkedList<Integer>();
		LinkedHashMap<Integer, Integer> threadOut = new LinkedHashMap<Integer, Integer>();
		List<Integer> temp;
		int count = 0;	
		
		ServiceActor actor;
		VkApiClient vk;
		public Worker(Integer id, String token) {
			actor = new ServiceActor(id, token);
			vk = new VkApiClient(HttpTransportClient.getInstance());
		}
		
		
		public void run() {
			Date t1 = new Date();
			while (in.size() > 0) {
				
				synchronized (in) {
					for (int i = 0; (in.size() > 0)&&(i < 100); i++) {
						threadIn.add(in.remove());
					}
				}
				
				while (threadIn.size() > 0) {
					count++;
					temp = getUserSubsVkSdk(threadIn.remove());
					if (temp == null)
						continue;
					while(temp.size() > 0) {
						threadOut.merge(temp.remove(0), 1, (oldVal, newVal) -> oldVal + newVal);						
					}					
				}
			}	
			
			synchronized (out) {
				for (Map.Entry<Integer, Integer> entry : threadOut.entrySet()) {
			        out.merge(entry.getKey(), entry.getValue(), (oldVal, newVal) -> oldVal + newVal);           
			    }				
			}
			
			Date t2 = new Date();
			System.out.println(Thread.currentThread().getName() + " is END! which processed " + count + " users in " + (t2.getTime() - t1.getTime())/1000 + " seconds");
		}
		private List<Integer> getUserSubsVkSdk(Integer userId) {
			List<Integer> subsIdArray = null;
			try {
				subsIdArray = vk.users()
							.getSubscriptions(actor)
							.userId(userId)
							.execute()
							.getGroups()
							.getItems();
			} catch (ApiException | ClientException e) {
				return null;
			}
			return subsIdArray;
		}
	}
}