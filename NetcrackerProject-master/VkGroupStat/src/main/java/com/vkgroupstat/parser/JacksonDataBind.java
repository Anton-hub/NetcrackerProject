package com.vkgroupstat.parser;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vkgroupstat.model.Person;
import com.vkgroupstat.model.Response;

import java.io.File;
import java.io.IOException;

public class JacksonDataBind {
    public static void parseJson(String response) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Person person = null;

        try {
            person = objectMapper.readValue(response, Person.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(person);
    }
}