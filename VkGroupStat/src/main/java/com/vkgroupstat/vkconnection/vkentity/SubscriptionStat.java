package com.vkgroupstat.vkconnection.vkentity;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

public class SubscriptionStat {
	private LinkedHashMap<String, Integer> sexStat = new LinkedHashMap<String, Integer>();
	private LinkedHashMap<String, Integer> cityStat = new LinkedHashMap<String, Integer>();
	private LinkedHashMap<String, Integer> ageStat = new LinkedHashMap<String, Integer>();	
	{
		ageStat.put("менее 10", 0);
		for (int i = 10; i < 50; i += 10)
			ageStat.put((i + " - " + (i + 10)).toString(), 0);
		ageStat.put("60 и более", 0);
		ageStat.put("Не указан", 0);
	}
	
	public SubscriptionStat() {}
	
	public SubscriptionStat(LinkedList<Subscriber> subsList){
		countUp(subsList);
	}
	
	public void countUp(LinkedList<Subscriber> subsList) {			
		for (Subscriber subscriber : subsList) {
			sexStat.merge(subscriber.getSex(), 1, (value, inc) -> value + inc);
			cityStat.merge(subscriber.getCity(), 1, (value, inc) -> value + inc);
			
			if (subscriber.getAge() == null) {
				ageStat.merge("Не указан", 1, (oldValue, newValue) -> oldValue + newValue);
			} else {
				Double age = subscriber.getAge().doubleValue();
				switch (new Double(age / 10d).intValue()) {
				case 0:
					ageStat.merge("менее 10", 1, (oldValue, newValue) -> oldValue + newValue);
					break;
				case 1:
					ageStat.merge("10 - 20", 1, (oldValue, newValue) -> oldValue + newValue);
					break;
				case 2:
					ageStat.merge("20 - 30", 1, (oldValue, newValue) -> oldValue + newValue);
					break;
				case 3:
					ageStat.merge("30 - 40", 1, (oldValue, newValue) -> oldValue + newValue);
					break;
				case 4:
					ageStat.merge("40 - 50", 1, (oldValue, newValue) -> oldValue + newValue);
					break;
				case 5:
					ageStat.merge("40 - 50", 1, (oldValue, newValue) -> oldValue + newValue);
					break;
				default:
					ageStat.merge("60 и более", 1, (oldValue, newValue) -> oldValue + newValue);
					break;
				}
			}
		}
		
		if (sexStat.containsKey("1"))
			sexStat.put("Женщин", sexStat.remove("1"));
		if (sexStat.containsKey("2"))
			sexStat.put("Мужчин", sexStat.remove("2"));
		
		if (cityStat.containsKey(null)) {
			cityStat.put("Не указан", cityStat.get(null));
			cityStat.remove(null);
		}
		cityStat = cityStat.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
							.collect(Collectors
							.toMap(Map.Entry::getKey
							,Map.Entry::getValue
							,(oldValue, newValue) -> oldValue, LinkedHashMap::new));
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
		cityStat.put("Другие", otherCityCount);			
	}

	public LinkedHashMap<String, Integer> getSexStat() {
		return sexStat;
	}
	public LinkedHashMap<String, Integer> getCityStat() {
		return cityStat;
	}
	public LinkedHashMap<String, Integer> getAgeStat() {
		return ageStat;
	}

	@Override
	public String toString() {
		return "sexStat =  " + sexStat + "<br>cityStat = " + cityStat + "<br>ageStat = " + ageStat + "<br>";
	}
	
}
