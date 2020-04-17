package com.vkgroupstat.service;

import com.vkgroupstat.model.User;
import com.vkgroupstat.repository.UserRepository;
import com.vkgroupstat.vkconnection.VkConnectForUsers;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UserService {

        private final UserRepository repository;
        public UserService(UserRepository repository) {
            this.repository = repository;
        }

        public String userRequestHandler(String code) {
            HashMap<User, String> userAndToken = VkConnectForUsers.getUser(code);
            User newUser = (User)userAndToken.keySet().toArray()[0];
            String token = userAndToken.get(newUser);
            User user = repository.findByuserId(newUser.getUserId());
            if (user == null) {
                repository.save(newUser);
            }
            return token;
        }


    }
