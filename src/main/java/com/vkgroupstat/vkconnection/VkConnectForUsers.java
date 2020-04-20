package com.vkgroupstat.vkconnection;

import java.util.HashMap;

import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.vkgroupstat.model.User;




public class VkConnectForUsers implements VkSdkObjHolder{
    private static String client_secret = "pqnxEFjvEdh3GHY0iOf3";
    private static String redirect_uri = "http://localhost:8080/search";

    public static HashMap<User, String> getUser(String code) {
        String token;
        Integer userId;
        HashMap<User, String> userAndToken = new HashMap<>();
        try {
            UserAuthResponse authResponse = VK.oauth()
                    .userAuthorizationCodeFlow(APPID, client_secret, redirect_uri, code)
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
