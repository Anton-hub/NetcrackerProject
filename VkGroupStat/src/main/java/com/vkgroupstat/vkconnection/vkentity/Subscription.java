package com.vkgroupstat.vkconnection.vkentity;

import java.util.LinkedList;

public class Subscription implements Comparable<Subscription>{
	
	protected Integer id = null;
	protected String stringName = null;
	protected String urlName = null;
	protected Integer subsCount = null;
	protected SubscriptionStat statistics= null;
	protected LinkedList<Subscriber> subsList = new LinkedList<Subscriber>();
	
	
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
		if (subsList.size() > 0) {
			statistics = new SubscriptionStat(subsList);
			subsCount = sizeList();
			subsList.clear();
		}
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
	public Integer getSubsCount() {
		return subsCount;
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
		return stringName + "  :  " + urlName + "  :  " + id + "  -  " + getSubsCount() + "<br>" + statistics; 
	}
}
