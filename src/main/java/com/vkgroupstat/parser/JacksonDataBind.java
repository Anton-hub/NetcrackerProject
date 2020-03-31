package com.vkgroupstat.parser;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vkgroupstat.model.Human;
import com.vkgroupstat.model.Person;
import com.vkgroupstat.model.Response;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class JacksonDataBind {
    public static void parseJson(String response) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Human person = null;
        //read JSON like DOM Parser
        try {
        JsonNode rootNode = objectMapper.readTree(response);
        JsonNode responseNode = rootNode.path("response");
        JsonNode groupsNode = responseNode.path("groups");

        JsonNode itemsNode = groupsNode.path("items");
        Iterator<JsonNode> elements = itemsNode.elements();
        while(elements.hasNext()){
            JsonNode phone = elements.next();
            System.out.println("id = "+phone.asLong());
        }
        } catch (IOException e) {
            e.printStackTrace();
        }

//        try {
//            person = objectMapper.readValue(response, Human.class);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(person);
    }
}