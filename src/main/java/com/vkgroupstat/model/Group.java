package com.vkgroupstat.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.Date;
import java.util.LinkedHashMap;


@Document
public class Group {
	@Id
	String id;

	String groupName;
	Integer[] users;
	LinkedHashMap<Integer, Integer> rangeList;
	Date savingDate;

	public Group(String groupName, Integer[] users, LinkedHashMap<Integer, Integer> rangeList) {
		super();
		this.groupName = groupName;
		this.users = users;
		this.rangeList = rangeList;
		savingDate = new Date();
	}

	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Integer[] getUsers() {
		return users;
	}
	public void setUsers(Integer[] users) {
		this.users = users;
	}
	public LinkedHashMap<Integer, Integer> getRangeList() {
		return rangeList;
	}
	public void setRangeList(LinkedHashMap<Integer, Integer> rangeList) {
		this.rangeList = rangeList;
	}
	public Date getSavingDate() {
		return savingDate;
	}
	public String getId() {
		return id;
	}
}
