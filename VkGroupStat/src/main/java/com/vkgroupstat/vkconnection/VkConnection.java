package com.vkgroupstat.vkconnection;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Collectors;

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
		try {
			response = vk.groups().getMembers(actor).groupId(groupName)
						.unsafeParam("access_token", actor.getAccessToken()).offset(offset).executeAsString();
			groupResponseHandler(response, subIdArray);		
			subCount = new JSONObject(response).getJSONObject("response").getInt("count");
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
	
	public static LinkedList<String> getUserSubsVkSdk(Integer userId) {
		LinkedList<String> subsIdArray = null;
		String response = null;
		try {
			response = vk.users()
							.getSubscriptions(actor)
							.unsafeParam("access_token", actor.getAccessToken())
							.userId(userId)
							.count(200)
							.executeAsString();			
		} catch (ClientException e) {
			e.printStackTrace();
		}
		if (response.contains("error"))
			return null;
		subsIdArray = new LinkedList<String>(Arrays.asList(
							new JSONObject(response)
							.getJSONObject("response")
							.getJSONObject("groups")
							.getJSONArray("items") 
							.toString()
							.substring(1)
							.split(",")));
		subsIdArray.set(subsIdArray.size()-1, subsIdArray.get(subsIdArray.size()-1).replaceFirst("]", ""));
		//LinkedList<Integer> intSubsIdArray = new LinkedList<Integer>(subsIdArray.stream().map(Integer::valueOf).collect(Collectors.toList()));
		return subsIdArray;
	}
}