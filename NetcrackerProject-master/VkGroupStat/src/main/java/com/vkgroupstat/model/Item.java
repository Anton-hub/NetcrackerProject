package com.vkgroupstat.model;

public class Item {

        private String name;
        private String screen_name;

        public Item() {

        }

        public Item(String name, String screen_name) {
                this.name = name;
                this.screen_name = screen_name;
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


        @Override
        public String toString() {
                return name + ", " + screen_name;
        }
}
