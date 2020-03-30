package com.vkgroupstat.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Groups {
    @JsonProperty("count")
    private Integer count;
    @JsonProperty("items")
    private List<String> items;


    public Groups() {
    }

    public Groups(Integer count, List<String> items) {
        this.count = count;
        this.items = items;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }
    @Override
    public String toString() {
        return "Groups{" +
                "count='" + count + '\'' +
                ", items='" + items + '\'' +
                '}';
    }
}
