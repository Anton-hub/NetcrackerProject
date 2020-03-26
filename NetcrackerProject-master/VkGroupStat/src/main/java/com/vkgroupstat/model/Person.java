package com.vkgroupstat.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Person {
//    @Id
//    String id;
//    String subscription;
//
//    public Person(String subscription) {
//        this.subscription = subscription;
//    }
//
//    public String toString() {
//        return "Person with subscriptions: " + subscription;
//    }


    private int count;
    private Item[] items;


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }



    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("\n count:" + this.count);
        if (this.items != null) {
            sb.append("\n items:" + this.items.toString());
        }
        return sb.toString();
    }

}
