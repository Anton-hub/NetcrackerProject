package com.vkgroupstat.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class Group {
	@Id
	String id;
	String groupName;
	Integer[] users;

	public Group(String groupName, Integer[] users) {
		super();
		this.groupName = groupName;
		this.users = users;
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
	public String getId() {
		return id;
	}	
}
