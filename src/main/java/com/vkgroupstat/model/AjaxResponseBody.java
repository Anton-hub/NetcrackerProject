package com.vkgroupstat.model;

import java.util.LinkedHashMap;
import java.util.List;

public class AjaxResponseBody {

    String msg;
    LinkedHashMap<String, Integer> result;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public LinkedHashMap<String, Integer> getResult() {
        return result;
    }

    public void setResult(LinkedHashMap<String, Integer> result) {
        this.result = result;
    }
}
