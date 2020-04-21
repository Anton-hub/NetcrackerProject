package com.vkgroupstat.vkconnection;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.vkgroupstat.Constants;
import com.vkgroupstat.model.User;

import java.util.HashMap;




public class VkConnectForUsers {

    private static String client_secret = "pqnxEFjvEdh3GHY0iOf3";
    private static VkApiClient vk = new VkApiClient(HttpTransportClient.getInstance());
    private static String redirect_uri = "http://localhost:8080/search";

    public static HashMap<User, String> getUser(String code) {
        String token;
        Integer userId;
        HashMap<User, String> userAndToken = new HashMap<>();
        try {
            UserAuthResponse authResponse = vk.oauth()
                    .userAuthorizationCodeFlow(Constants.APPID, Constants.client_secret, Constants.redirect_uri, code)
                    .execute();

            UserActor actor = new UserActor(authResponse.getUserId(), authResponse.getAccessToken());
            token = authResponse.getAccessToken();
            userId = authResponse.getUserId();

        } catch (ClientException | ApiException e) {
            e.printStackTrace();
            return null;
        }
        User user = new User(userId.toString());
        userAndToken.put(user, token);
        return userAndToken;
    }
}
