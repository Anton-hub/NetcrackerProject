package com.vkgroupstat.model;

import java.util.LinkedHashMap;
import java.util.List;

public class AjaxResponseBody {

    String msg;
    Group group;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
