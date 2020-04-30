package com.vkgroupstat.model;

import java.util.Date;
import java.util.LinkedList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.vkgroupstat.vkconnection.vkentity.GroupStat;
import com.vkgroupstat.vkconnection.vkentity.Subscription;

@Document
public class Group {
	@Id
	String id;
	Integer clubId;
	String urlName;
	String stringName;
	
	String description;
	GroupStat groupStat;
	LinkedList<Subscription> rangeList;
	Date createDate = new Date();
	
	public Group(Integer clubId
				,String urlName
				,String stringName
				,String description
				,GroupStat groupStat
				,LinkedList<Subscription> rangeList) {		
		this.clubId = clubId;
		this.urlName = urlName;
		this.stringName = stringName;
		this.description = description;
		this.groupStat = groupStat;
		this.rangeList = rangeList;
	}

	public String getId() {	return id; }	
	public Integer getClubId() { return clubId; }
	public String getUrlName() { return urlName; }
	public String getStringName() {	return stringName; }	
	public String getDescription() { return description; }
	public GroupStat getGroupStat() { return groupStat;	}
	public LinkedList<Subscription> getRangeList() { return rangeList; }
	public Date getCreateDate() { return createDate; }
	
	public void setId(String id) { this.id = id; }	
}
