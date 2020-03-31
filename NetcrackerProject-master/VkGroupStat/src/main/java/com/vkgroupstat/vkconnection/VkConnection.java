package com.vkgroupstat.vkconnection;

import com.bluelinelabs.logansquare.LoganSquare;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;

import com.vkgroupstat.model.Item;
import com.vkgroupstat.model.Person;
import com.vkgroupstat.parser.JacksonDataBind;
import org.apache.commons.io.IOUtils;
import org.json.*;

public class VkConnection {

	private static String token = "b8188fe1b8188fe1b8188fe1ffb868d748bb818b8188fe1e6699966a9d9035f1e14fbda";
	private static ServiceActor actor = new ServiceActor(7362729, token);
	private static VkApiClient vk = new VkApiClient(HttpTransportClient.getInstance());
	
	public static String getGroupVkSdk(String groupName) {
		LinkedList <Integer> subIdArray = new LinkedList<Integer>();
		Integer offset = 0;
		Integer subCount = 0;
		String response = "";
		JSONObject jsonresponse = null;
		
		try {
			response = vk.groups().getMembers(actor).groupId(groupName)
						.unsafeParam("access_token", actor.getAccessToken()).offset(offset).executeAsString();
			responseHandler(response, subIdArray);
			jsonresponse = new JSONObject(response).getJSONObject("response");			
			subCount = jsonresponse.getInt("count");
			offset += 1000;
			while (offset < subCount) {				
				response = vk.groups().getMembers(actor).groupId(groupName)
							.unsafeParam("access_token", actor.getAccessToken()).offset(offset).executeAsString();
				responseHandler(response, subIdArray);
				offset += 1000;
			}
		} catch (ClientException e) {
			e.printStackTrace();
		}
		return subIdArray.toString();//можно использовать для засовывания, как массив, в документ монго
	}
	private static void responseHandler(String response, LinkedList <Integer> anyList) {
		JSONObject jsonresponse = new JSONObject(response).getJSONObject("response");
		Iterator<Object> items = jsonresponse.getJSONArray("items").iterator();
		while (items.hasNext()) {
			anyList.add((Integer)items.next());
		}
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
			response += "\n" + ((JSONObject) items.next()).getString("screen_name"); //не видит screen_name
		}//http://localhost:8080/usersubs?userId=169589685
		return response;
	}
		
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
		public static Person getUserSubsVkSdk(Integer userId) {

			ServiceActor actor = new ServiceActor(7362729, token);
			VkApiClient vk = new VkApiClient(HttpTransportClient.getInstance());

			String response = "";

			try {
				response = vk.users()
						.getSubscriptions(actor)
						.userId(userId)
						.unsafeParam("access_token", actor.getAccessToken())//без этого выдает ошибку
						.offset(200)
						.count(200)
						.executeAsString();
			} catch (ClientException e) {
				e.printStackTrace();
			}
			JSONObject json = new JSONObject(response);
			Person person = new Person();
			person.setCount(json.getJSONObject("response").getJSONObject("groups").getInt("count"));
			Item[] items = new Item[json.getJSONObject("response").getJSONObject("groups").getInt("count")];
			StringBuilder[] id = new StringBuilder[200];
			for (int i=0; i<json.getJSONObject("response").getJSONObject("groups").getInt("count"); i++) {
				id[i] = new StringBuilder(json.getJSONObject("response").getJSONObject("groups").getJSONArray("items").getInt(i));
				items[i].setId(id[i].toString());
			}
			person.setItems(items);
			return person;
		}
	public static String getUserSubsVkSdkWorking(Integer userId) {

		ServiceActor actor = new ServiceActor(7362729, token);
		VkApiClient vk = new VkApiClient(HttpTransportClient.getInstance());

		String response = "";

		try {
			response = vk.users()
					.getSubscriptions(actor)
					.userId(userId)
					.unsafeParam("access_token", actor.getAccessToken())//без этого выдает ошибку
					.offset(200)
					.count(200)
					.executeAsString();
		} catch (ClientException e) {
			e.printStackTrace();
		}
		JacksonDataBind.parseJson(response);
		return response;
	}
//	public static String getUserSubsVkSdkWorkingAlmost(Integer userId) throws Exception {
//
//		ServiceActor actor = new ServiceActor(7362729, token);
//		VkApiClient vk = new VkApiClient(HttpTransportClient.getInstance());
//
//		String response = "";
//
//		try {
//			response = vk.users()
//					.getSubscriptions(actor)
//					.userId(userId)
//					.unsafeParam("access_token", actor.getAccessToken())//без этого выдает ошибку
//					.offset(200)
//					.count(200)
//					.executeAsString();
//		} catch (ClientException e) {
//			e.printStackTrace();
//		}
//		JSONArray jsonarray = new JSONArray(response);
//	//	Person newPerson = LoganSquare.parse(response, Person.class);
//		for (int i = 0; i < jsonarray.length(); i++) {
//			JSONObject jsonModel = jsonarray.getJSONObject(i);
//			String count = jsonModel.getString("count");
//			JSONArray items = jsonModel.getJSONArray("items");
//			String[] id = new String[items.length()];
//			String[] name = new String[items.length()];
//			String[] screen_name = new String[items.length()];
//
//
//
//			for(int j = 0; j < items.length(); j++){
//				id[j] = jsonModel.getString("id");
//				name[j] = jsonModel.getString("name");
//				screen_name[j] = jsonModel.getString("screen_name");
//			}
//
//
//		}
//		Person newPerson = LoganSquare.parse(response, Person.class);
//		newPerson.say(); // print "Hi , World!"
//		return response;
//	}

}