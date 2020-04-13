package com.vkgroupstat.vkconnection.vkentity;

import java.util.LinkedList;

public class Subscription implements Comparable<Subscription>{
	
	private Integer id = null;
	private String stringName = null;
	private String urlName = null;
	private SubscriptionStat statistics= null;
	private LinkedList<Subscriber> subsList = new LinkedList<Subscriber>();
	
	
	public Subscription() {}
	public Subscription(Integer id, Subscriber firstSub) {
		this.id = id;
		addSub(firstSub);
	}	
	public Subscription(Integer id, String stringName, String urlName, LinkedList<Subscriber> subsList) {
		super();
		this.id = id;
		this.stringName = stringName;
		this.urlName = urlName;
		this.subsList = subsList;
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
	public void countUp() {
		statistics = new SubscriptionStat(subsList);
	}
	

	public Integer getId() {
		return id;
	}
	public String getStringName() {
		return stringName;
	}
	public String getUrlName() {
		return urlName;
	}
	public LinkedList<Subscriber> getSubsList() {
		return subsList;
	}	
	public SubscriptionStat getStatistics() {
		return statistics;
	}


	public void setStringName(String stringName) {
		this.stringName = stringName;
	}
	public void setUrlName(String urlName) {
		this.urlName = urlName;
	}
	

	@Override
	public int compareTo(Subscription o) {		
		return new Integer(o.getSubsList().size()).compareTo(new Integer(this.getSubsList().size()));
	}
	@Override
	public String toString() {
		return stringName + "  :  " + urlName + "  :  " + id + "  -  " + sizeList() + "<br>" + statistics; 
	}
}
