package com.vkgroupstat.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Human {
    @JsonProperty("response")
    private Response response;

    public Human() {
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "Response='" + response + '\'' +
                '}';
    }
}
