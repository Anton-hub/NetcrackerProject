package com.vkgroupstat.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Response {
//    @JsonProperty("users")
////    private Users users;
    @JsonProperty("groups")
    private Groups groups;

    public Response() {
    }

    public Response(Groups groups) {
        this.groups = groups;
    }


    public Groups getGroups() {
        return groups;
    }

    public void setGroups(Groups qroups) {
        this.groups = groups;
    }

    @Override
    public String toString() {
        return "Response{" +
//                "Users='" + users + '\'' +
                "Groups='" + groups + '\'' +
                '}';
    }
}
