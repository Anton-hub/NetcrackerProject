package com.vkgroupstat.model;

import com.vkgroupstat.vkconnection.vkentity.Subscription;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.Query;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Document
public class User {
    @Id
    String id;
    String userId;
//    @DBRef
//    private List<Group> listGroups;
    List<Group> listGroups;

    public User(String userId) {
        super();
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Group> getListGroups() {
        return listGroups;
    }

    public void setListGroups(List<Group> listGroups) {

        this.listGroups = listGroups;
    }
}
