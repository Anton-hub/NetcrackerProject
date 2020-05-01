package com.vkgroupstat.vkconnection.fillers;

import static com.vkgroupstat.constants.StatNameConstant.*;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

import com.vkgroupstat.vkconnection.vkentity.Subscriber;
import com.vkgroupstat.vkconnection.vkentity.SubscriptionStat;

public class SubscriptionStatFiller {
	
	private LinkedHashMap<String, Integer> sexStat = new LinkedHashMap<String, Integer>();
	private LinkedHashMap<String, Integer> cityStat = new LinkedHashMap<String, Integer>();
	private LinkedHashMap<String, Integer> ageStat = new LinkedHashMap<String, Integer>();
	private LinkedHashMap<String, Integer> activityStat = new LinkedHashMap<String, Integer>();
	{
		ageStat.put(AGE_1, 0);
		ageStat.put(AGE_2, 0);
		ageStat.put(AGE_3, 0);
		ageStat.put(AGE_4, 0);
		ageStat.put(AGE_5, 0);
		ageStat.put(AGE_6, 0);
		ageStat.put(AGE_7, 0);
		ageStat.put(AGE_ABSENT, 0);
		
		activityStat.put(ACTIVITY_1, 0);
		activityStat.put(ACTIVITY_2, 0);
	}
	
	public SubscriptionStatFiller(LinkedList<Subscriber> subsList) {
		countUp(subsList);
	}
	
	public void countUp(LinkedList<Subscriber> subsList) {			
		for (Subscriber subscriber : subsList) {
			
			if (subscriber.getLikeCount() > 0) {
				activityStat.merge(ACTIVITY_1, 1, (oldValue ,newValue) -> oldValue + newValue);
				if (subscriber.getCommentCount() > 0)
					activityStat.merge(ACTIVITY_2, 1, (oldValue, newValue) -> oldValue + newValue);
			}
			
			sexStat.merge(subscriber.getSex(), 1, (value, inc) -> value + inc);

			if ((subscriber.getCity() != null)&&(subscriber.getCity().contains("."))) {
				cityStat.merge(subscriber.getCity().replaceAll(".", " "), 1, (value, inc) -> value + inc);
			}else {
				cityStat.merge(subscriber.getCity(), 1, (value, inc) -> value + inc);
			}			
			
			if (subscriber.getAge() == null) {
				ageStat.merge(AGE_ABSENT, 1, (oldValue, newValue) -> oldValue + newValue);
			} else {
				Double age = subscriber.getAge().doubleValue();
				switch (new Double(age / 10d).intValue()) {
				case 0:
					ageStat.merge(AGE_1, 1, (oldValue, newValue) -> oldValue + newValue);
					break;
				case 1:
					ageStat.merge(AGE_2, 1, (oldValue, newValue) -> oldValue + newValue);
					break;
				case 2:
					ageStat.merge(AGE_3, 1, (oldValue, newValue) -> oldValue + newValue);
					break;
				case 3:
					ageStat.merge(AGE_4, 1, (oldValue, newValue) -> oldValue + newValue);
					break;
				case 4:
					ageStat.merge(AGE_5, 1, (oldValue, newValue) -> oldValue + newValue);
					break;
				case 5:
					ageStat.merge(AGE_6, 1, (oldValue, newValue) -> oldValue + newValue);
					break;
				default:
					ageStat.merge(AGE_7, 1, (oldValue, newValue) -> oldValue + newValue);
					break;
				}
			}
		}
		
		if (sexStat.containsKey("1"))
			sexStat.put(SEX_1, sexStat.remove("1"));
		if (sexStat.containsKey("2"))
			sexStat.put(SEX_2, sexStat.remove("2"));
		if (sexStat.containsKey("0"))
			sexStat.put(SEX_ABSENT, sexStat.remove("0"));
			
		
		if (cityStat.containsKey(null)) {
			cityStat.put(CITY_ABSENT, cityStat.get(null));
			cityStat.remove(null);
		}
		cityStat = sortedByValue(cityStat);
		int cityInChartCount = 5;
		int otherCityCount = 0;
		Iterator<Integer> iterator = cityStat.values().iterator();
		while (iterator.hasNext()) {
			if (cityInChartCount-- > 0) {
				iterator.next();
			} else {
				otherCityCount += iterator.next();
				iterator.remove();
			}			
		}
		cityStat.put(CITY_OTHERS, otherCityCount);	
		cityStat = sortedByValue(cityStat);
	}
	
	public SubscriptionStat fillStat(SubscriptionStat stat) {
		stat.setSexStat(sexStat);
		stat.setCityStat(cityStat);
		stat.setAgeStat(ageStat);
		stat.setActivityStat(activityStat); 
		return stat;
	}
	
	private LinkedHashMap<String, Integer> sortedByValue(LinkedHashMap<String, Integer> map) {
		return map.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.collect(Collectors
				.toMap(Map.Entry::getKey
				,Map.Entry::getValue
				,(oldValue, newValue) -> oldValue, LinkedHashMap::new));
	}
}
