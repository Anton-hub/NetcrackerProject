package com.vkgroupstat.model;

import java.util.LinkedList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User {
    @Id
    String id;
    Integer userId;
    LinkedList<String> listGroupsId = new LinkedList<String>();
    

    public User(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
	public LinkedList<String> getListGroupsId() {
		return listGroupsId;
	}
	
	public void addGroupId(String groupId) {
		if (!listGroupsId.contains(groupId))
			listGroupsId.add(groupId);
	}
}
