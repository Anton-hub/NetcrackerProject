package com.vkgroupstat.vkconnection;

import com.vkgroupstat.model.Group;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;

import org.apache.commons.io.IOUtils;
import org.json.*;

public class VkConnection {

	private static String token = "b8188fe1b8188fe1b8188fe1ffb868d748bb818b8188fe1e6699966a9d9035f1e14fbda";
	
	public static String getGroup(String groupName) {
		Integer offset = 0;
		Integer subscribersCount = 0;
		
		String staticPartUrl = "https://api.vk.com/method/groups.getMembers?group_id=" + groupName;
		String dynPartUrl = staticPartUrl;
		String response = null;
		
		try {			
			dynPartUrl = staticPartUrl + "&access_token=" + token + "&v=5.103";
			response = IOUtils.toString(new URL(dynPartUrl).openStream());
			subscribersCount = new JSONObject(response).getJSONObject("response").getInt("count");
			
            do {
            	dynPartUrl = staticPartUrl + "&offset=" + offset + "&access_token=" + token + "&v=5.103";
            	response += "\n" + IOUtils.toString(new URL(dynPartUrl).openStream());
				offset += 1000;
			} while (offset < subscribersCount);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
		response += "\n" + (offset / 1000); //количество запросов
		return response;
	}
	public static String getUserSubs(Integer userId) {
		String response = null;
		String url = "https://api.vk.com/method/users.getSubscriptions?user_id=" + userId 
						+ "&extended=1&count=200&access_token=" + token + "&v=5.103";
		
		try {
			response = IOUtils.toString(new URL(url).openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject json = new JSONObject(response);
		response = "";
		Iterator<Object> items = json.getJSONObject("response").getJSONArray("items").iterator();
		while (items.hasNext()) {
			response += "\n" + ((JSONObject)items.next()).getString("screen_name"); //не видит screen_name
		}//http://localhost:8080/usersubs?userId=169589685
		return response;
		
		/*пример того как выглядит ответ на вызываемый метод
		 * {
  "response": {
    "count": 37,
    "items": [
      {
        "id": 125004421,
        "name": "Настоящий Лентач",
        "screen_name": "true_lentach",
        "is_closed": 0,
        "type": "page",
        "photo_50": "https://sun9-11.userapi.com/c850020/v850020444/170e07/J8tqsBt-S-A.jpg?ava=1",
        "photo_100": "https://sun9-20.userapi.com/c850020/v850020444/170e06/xEUdAaPYrcM.jpg?ava=1",
        "photo_200": "https://sun9-54.userapi.com/c850020/v850020444/170e05/ex-Y2ubuSr0.jpg?ava=1"
      },
      {
        "id": 29534144,
        "name": "Лентач",
        "screen_name": "lentach",
        "is_closed": 0,
        "type": "page",
        "photo_50": "https://sun9-55.userapi.com/c205520/v205520309/6ba4a/7sggvySs-J4.jpg?ava=1",
        "photo_100": "https://sun9-39.userapi.com/c205520/v205520309/6ba49/FZnT81BEgh0.jpg?ava=1",
        "photo_200": "https://sun9-50.userapi.com/c205520/v205520309/6ba48/hd6ghzm5Lnw.jpg?ava=1"
      }
    ]
  }
}
		 */
	}
}