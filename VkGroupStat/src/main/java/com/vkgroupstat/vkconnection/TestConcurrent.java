package com.vkgroupstat.vkconnection;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.json.JSONObject;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;

public class TestConcurrent {
	public static String test(String groupName) {
		String result = "";
		ExecutorService executor = Executors.newCachedThreadPool();
		try {
			Future<LinkedList<Integer>> f1, f2, f3, f4;
			f1 = executor.submit(new Parser(groupName));
			f2 = executor.submit(new Parser(groupName));
			f3 = executor.submit(new Parser(groupName));
			f4 = executor.submit(new Parser(groupName));
			result = Integer.toString(f1.get().size()) + "  :  " +
					 Integer.toString(f2.get().size()) + "  :  " +
					 Integer.toString(f3.get().size()) + "  :  " +
					 Integer.toString(f4.get().size());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return result;		
	}
}
class Parser implements Callable<LinkedList <Integer>>{
	String groupName = "";
	public Parser(String groupName) {
		this.groupName = groupName;
	}
	public LinkedList <Integer> call() {
		String token = "b8188fe1b8188fe1b8188fe1ffb868d748bb818b8188fe1e6699966a9d9035f1e14fbda";
		ServiceActor actor = new ServiceActor(7362729, token);
		VkApiClient vk = new VkApiClient(HttpTransportClient.getInstance());
		Helper helper = new Helper();
		LinkedList <Integer> subIdArray = new LinkedList<Integer>();
		Integer offset = 0;
		Integer subCount = 0;
		String response = "";
		JSONObject jsonresponse = null;
		
		try {
			response = vk.groups().getMembers(actor).groupId(groupName)
						.unsafeParam("access_token", actor.getAccessToken()).offset(offset).executeAsString();
			helper.groupResponseHandler(response, subIdArray);
			jsonresponse = new JSONObject(response).getJSONObject("response");			
			subCount = jsonresponse.getInt("count");
			offset += 1000;
			while (offset < subCount) {				
				response = vk.groups().getMembers(actor).groupId(groupName)
							.unsafeParam("access_token", actor.getAccessToken()).offset(offset).executeAsString();
				helper.groupResponseHandler(response, subIdArray);
				offset += 1000;
			}
		} catch (ClientException e) {
			e.printStackTrace();
		}
		return subIdArray;
	}
	class Helper{
		void groupResponseHandler(String response, LinkedList <Integer> anyList) {
			JSONObject jsonresponse = new JSONObject(response).getJSONObject("response");
			Iterator<Object> items = jsonresponse.getJSONArray("items").iterator();
			while (items.hasNext()) {
				anyList.add((Integer)items.next());
			}
		}
	}
}
