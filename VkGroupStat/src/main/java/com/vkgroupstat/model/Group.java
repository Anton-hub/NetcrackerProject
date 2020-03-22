package com.vkgroupstat.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


//тестовый вариант
@Document
public class Group {
	@Id
	String id;
	String firstName;
	
	public Group(String firstName) {
		this.firstName = firstName;
	}
	
	public String toString() {
		return "Group with name: " + firstName;
	}
}
