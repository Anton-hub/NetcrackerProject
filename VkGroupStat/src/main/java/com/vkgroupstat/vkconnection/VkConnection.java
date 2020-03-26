package com.vkgroupstat.vkconnection;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;

import io.netty.buffer.UnpooledByteBufAllocator;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.commons.io.IOUtils;
import org.json.*;

public class VkConnection {

	private static String token = "b8188fe1b8188fe1b8188fe1ffb868d748bb818b8188fe1e6699966a9d9035f1e14fbda";
	private static ServiceActor actor = new ServiceActor(7362729, token);
	private static VkApiClient vk = new VkApiClient(HttpTransportClient.getInstance());
	
	public static LinkedList <Integer> getGroupVkSdk(String groupName) {
		LinkedList <Integer> subIdArray = new LinkedList<Integer>();
		Integer offset = 0;
		Integer subCount = 0;
		String response = "";
		JSONObject jsonresponse = null;
		
		try {
			response = vk.groups().getMembers(actor).groupId(groupName)
						.unsafeParam("access_token", actor.getAccessToken()).offset(offset).executeAsString();
			groupResponseHandler(response, subIdArray);
			jsonresponse = new JSONObject(response).getJSONObject("response");			
			subCount = jsonresponse.getInt("count");
			offset += 1000;
			while (offset < subCount) {				
				response = vk.groups().getMembers(actor).groupId(groupName)
							.unsafeParam("access_token", actor.getAccessToken()).offset(offset).executeAsString();
				groupResponseHandler(response, subIdArray);
				offset += 1000;
			}
		} catch (ClientException e) {
			e.printStackTrace();
		}
		return subIdArray;
	}
	private static void groupResponseHandler(String response, LinkedList <Integer> anyList) {
		JSONObject jsonresponse = new JSONObject(response).getJSONObject("response");
		Iterator<Object> items = jsonresponse.getJSONArray("items").iterator();
		while (items.hasNext()) {
			anyList.add((Integer)items.next());
		}
	}
	
	
	public static String getUserSubsVkSdk(Integer userId) {
		LinkedList<String> subsIdArray = new LinkedList<String>();
		Integer subCount = 0;
		Integer offset = 0;
		String response = null;
		JSONObject jsonresponse = null;
		try {
			response = vk.users().getSubscriptionsExtended(actor)
						.unsafeParam("access_token", actor.getAccessToken())
						.userId(userId).count(200).offset(offset).executeAsString();
			subCount = new JSONObject(response).getJSONObject("response").getInt("count");
			userResponseHandler(response, subsIdArray);
			if (subCount > 200) {
				while (offset < subCount) {
					offset += 200;
					response = vk.users().getSubscriptionsExtended(actor)
							.unsafeParam("access_token", actor.getAccessToken()).userId(userId).count(200)
							.offset(offset).executeAsString();
					userResponseHandler(response, subsIdArray);
				}
			}
		} catch (ClientException e) {
			e.printStackTrace();
		}
		return subsIdArray.toString();
	}
	private static void userResponseHandler(String response, LinkedList <String> anyList) {
		JSONObject jsonresponse = new JSONObject(response).getJSONObject("response");
		Iterator<Object> items = jsonresponse.getJSONArray("items").iterator();
		while (items.hasNext()) {
			anyList.add(((JSONObject)items.next()).getString("screen_name"));
		}
	}
}