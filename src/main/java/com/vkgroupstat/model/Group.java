package com.vkgroupstat.model;

import java.util.Date;
import java.util.LinkedList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.vkgroupstat.vkconnection.vkentity.Subscription;
import com.vkgroupstat.vkconnection.vkentity.SubscriptionStat;

@Document
public class Group {
	@Id
	String id;
	String urlName;
	String stringName;

	SubscriptionStat baseStat;
	LinkedList<Subscription> rangeList;
	Date createDate = new Date();
	
	public Group(String urlName, String stringName, SubscriptionStat baseStat, LinkedList<Subscription> rangeList) {
		super();
		this.urlName = urlName;
		this.stringName = stringName;
		this.baseStat = baseStat;
		this.rangeList = rangeList;
	}

	public String getId() {
		return id;
	}
	public String getUrlName() {
		return urlName;
	}
	public String getStringName() {
		return stringName;
	}
	public SubscriptionStat getBaseStat() {
		return baseStat;
	}
	public LinkedList<Subscription> getRangeList() {
		return rangeList;
	}
	public Date getCreateDate() {
		return createDate;
	}

	
	public void setId(String id) {
		this.id = id;
	}	
}
