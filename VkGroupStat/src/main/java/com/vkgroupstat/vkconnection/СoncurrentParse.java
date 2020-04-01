package com.vkgroupstat.vkconnection;

import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class СoncurrentParse {
	LinkedList<Integer> in; 
	LinkedHashMap<Integer, Integer> out = new LinkedHashMap<Integer, Integer>(); 
	
	public СoncurrentParse(LinkedList<Integer> list) {
		in = new LinkedList<Integer>(list);
	}
	
	public LinkedHashMap<Integer, Integer> start() {
		ExecutorService executor = Executors.newCachedThreadPool();
		
		for (int i = 0 ; i < 15 ; i++)
			executor.execute(new Worker());
		
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
					temp = VkConnection.getUserSubsVkSdk(threadIn.remove());
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
	}
}