package com.vkgroupstat.vkconnection;

//import com.mongodb.util.JSON;
import com.vkgroupstat.model.Group;

import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.json.*;

public class VkConnection {

	private static String token = "b8188fe1b8188fe1b8188fe1ffb868d748bb818b8188fe1e6699966a9d9035f1e14fbda";
	
	public static String getGroup(String groupName) {
		Integer offset = 0;
		Integer subCount = 0;
		
		String url = "https://api.vk.com/method/groups.getMembers?group_id=" + groupName;
		String tempUrl = url;
		String json = null;
		try {			
			tempUrl = url + "&access_token=" + token + "&v=5.103";
			json = IOUtils.toString(new URL(tempUrl).openStream());
			subCount = new JSONObject(json).getJSONObject("response").getInt("count");
            do {
            	tempUrl = url + "&offset=" + offset + "&access_token=" + token + "&v=5.103";
				json += "\n" + IOUtils.toString(new URL(tempUrl).openStream());
				offset += 1000;
			} while (offset < subCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
		json += "\n" + (offset / 1000);
		return json;
	}
}