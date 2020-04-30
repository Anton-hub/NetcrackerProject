package com.vkgroupstat.model;

import java.util.LinkedList;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User {
    @Id
    String id;
    Integer userId;
    @Indexed(unique = true, direction = IndexDirection.DESCENDING, dropDups = true)
    private String email;
    private String password;
    private String fullname;
    private boolean enabled;
    @DBRef
    private Set<Role> roles;

    LinkedList<String> listGroupsId = new LinkedList<String>();

    public User() {
    }

    public User(Integer userId) {
        this.userId = userId;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
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
