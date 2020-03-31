package com.vkgroupstat.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Users {
    @JsonProperty("count")
    private Integer count;
    @JsonProperty("items")
    private List<Integer> items;

    public Users() {
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Integer> getItems() {
        return items;
    }

    public void setItems(List<Integer> items) {
        this.items = items;
    }
    @Override
    public String toString() {
        return "Users{" +
                "count='" + count + '\'' +
                ", items='" + items + '\'' +
                '}';
    }
}
