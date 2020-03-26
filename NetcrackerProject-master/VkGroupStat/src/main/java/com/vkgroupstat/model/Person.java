package com.vkgroupstat.model;

import org.json.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class Person {
    @Id
    Integer count;
    Item[] items;

    public Integer getCount() {
        return count;
    }

    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }

    public void setCount(Integer count) {
        this.count = count;
    }



    public String toString() {
        return "Person with subscriptions: " + items;
    }


//    @JsonField(name="count")
//    public String count;
//    @JsonField(name="items")
//    public Item[] items;
//
//    public void say() {
//        System.out.println();
//
//            System.out.println(count + " \n" + items+ "\n");
//
//
//    }
}
