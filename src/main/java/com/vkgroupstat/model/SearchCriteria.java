package com.vkgroupstat.model;

import org.hibernate.validator.constraints.NotBlank;

public class SearchCriteria {

    @NotBlank(message = "username can't empty!")

    String groupName;

    public SearchCriteria() {
    }

    public String getGroupName() {
        return groupName;
    }

    public SearchCriteria(String groupName) {
        this.groupName = groupName;
    }
}