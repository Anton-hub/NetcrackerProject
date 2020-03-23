package com.vkgroupstat.vkconnection;

//import com.mongodb.util.JSON;
import com.vkgroupstat.model.Group;

import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.IOUtils;

public class VkConnection {

	private static String token = "b8188fe1b8188fe1b8188fe1ffb868d748bb818b8188fe1e6699966a9d9035f1e14fbda";
	
	public static String getGroup(String groupName) {		
		String url = "https://api.vk.com/method/groups.getMembers?group_id=" 
						+ groupName + "&access_token=" + token + "&v=5.103";
		String json = null;
		try {
            json = IOUtils.toString(new URL(url).openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
		return json;
	}
}