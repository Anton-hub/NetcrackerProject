package com.vkgroupstat.model;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class Item {

        private String name;
        private String screen_name;
        private String id;
        public Item() {

        }

//        public Item(String name, String screen_name) {
////                this.name = name;
////                this.screen_name = screen_name;
////        }
        public Item(String id) {
                this.id = id;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getScreen_name() {
                return screen_name;
        }

        public void setScreen_name(String screen_name) {
                this.screen_name = screen_name;
        }

        public String getId() {
                return id;
        }

        public void setId(String id) {
                this.id = id;
        }

        @Override
        public String toString() {
                return id + ", ";
        }
//        @JsonField(name="id")
//        public String id;
//        @JsonField(name="name")
//        public String name;
//        @JsonField(name="screen_name")
//        public String screen_name;
//        @JsonField(name="is_closed")
//        public Integer is_closed;
//        @JsonField(name="type")
//        public String type;
//        @JsonField(name="is_admin")
//        public Integer is_admin;
//        @JsonField(name="is_member")
//        public Integer is_member;
//        @JsonField(name="photo_50")
//        public String photo_50;
//        @JsonField(name="photo_100")
//        public String photo_100;
//        @JsonField(name="photo_200")
//        public String photo_200;

}
