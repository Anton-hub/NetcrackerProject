package com.vkgroupstat.vkconnection.TEST;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.vk.api.sdk.objects.users.UserXtrCounters;
import com.vkgroupstat.vkconnection.VkSdkObjHolder;
import com.vkgroupstat.vkconnection.vkentity.Post;

public class TEST_StringOutPost implements VkSdkObjHolder {
	public static String out(Post post) {		
		return "<br>[POST]   id = " + post.getPostId() + " // date = " + post.getDate() + " // likes = "
				+ post.getLikesCount() + " // comments = " + post.getCommentsCount() + " // "
				+ post.getLikersIdList().stream().limit(5).map(Object::toString).collect(Collectors.joining(",")) + " ..."
				+ testhelper(post.getCommentsMap());
	}
	private static String testhelper(LinkedHashMap<Integer, Integer> commentsMap) {
		String response = "";
		if (commentsMap.size() > 0) {
			for (Map.Entry<String, Integer> item : keyIntToString(commentsMap).entrySet()) {
				response += "<br> //////" + item.getKey() + " " + item.getValue();
			}
		}
		return response;
	}
	private static LinkedHashMap<String, Integer> keyIntToString(LinkedHashMap<Integer, Integer> inMap){
		LinkedHashMap<String, Integer> outMap = new LinkedHashMap<String, Integer>();
		for (Map.Entry<Integer, Integer> item : inMap.entrySet()) {
			outMap.put(getName(item.getKey()), item.getValue());
		}
		return outMap;
	}
	private static String getName(Integer id) {
		try {
			UserXtrCounters temp = VK.users().get(S_ACTOR).userIds(id.toString()).execute().get(0);
			String s = temp.getFirstName() + " " + temp.getLastName();
			return s;
		}catch (Exception e) {
			System.err.println(id);
			return null;
		}
	}
}
