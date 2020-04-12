package com.vkgroupstat.vkconnection.vkentity;

import java.util.LinkedHashMap;
import java.util.LinkedList;

public class Subscription implements Comparable<Subscription>{
	
	private Integer id = null;	
	private LinkedList<Subscriber> subsList = new LinkedList<Subscriber>();
	//statistic
	private LinkedHashMap<String, Integer> sexStat = new LinkedHashMap<String, Integer>();
	private LinkedHashMap<String, Integer> ageStat = new LinkedHashMap<String, Integer>();
	private LinkedHashMap<String, Integer> cityStat = new LinkedHashMap<String, Integer>();
	
	
	public Subscription(Integer id, Subscriber firstSub) {
		this.id = id;
		addSub(firstSub);
	}
	
	
	public Integer sizeList() {
		return subsList.size();
	}
	public void concat(Subscription concatSubs) {
		subsList.addAll(concatSubs.getSubsList());
	}
	public void addSub(Subscriber sub) {
		subsList.add(sub);
	}
	
	
	public LinkedHashMap<String, Integer> getSexStat() {
		return sexStat;
	}

	public void setSexStat(LinkedHashMap<String, Integer> sexStat) {
		this.sexStat = sexStat;
	}

	public LinkedHashMap<String, Integer> getAgeStat() {
		return ageStat;
	}

	public void setAgeStat(LinkedHashMap<String, Integer> ageStat) {
		this.ageStat = ageStat;
	}

	public LinkedHashMap<String, Integer> getCityStat() {
		return cityStat;
	}

	public void setCityStat(LinkedHashMap<String, Integer> cityStat) {
		this.cityStat = cityStat;
	}		

	public Integer getId() {
		return id;
	}

	public LinkedList<Subscriber> getSubsList() {
		return subsList;
	}
	
	@Override
	public int compareTo(Subscription o) {		
		return new Integer(o.getSubsList().size()).compareTo(new Integer(this.getSubsList().size()));
	}
}
