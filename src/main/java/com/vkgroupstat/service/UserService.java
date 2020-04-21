package com.vkgroupstat.service;

import com.vkgroupstat.controller.WebController;
import com.vkgroupstat.model.Group;
import com.vkgroupstat.model.User;
import com.vkgroupstat.repository.GroupRepository;
import com.vkgroupstat.repository.UserRepository;
import com.vkgroupstat.vkconnection.VkConnectForUsers;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
        private User currentUser;
        private final UserRepository repository;
        private final GroupRepository gRepository;
        public UserService(UserRepository repository, GroupRepository gRepository) {
            this.repository = repository;
            this.gRepository = gRepository;
        }

        public String userRequestHandler(String code) {
            HashMap<User, String> userAndToken = VkConnectForUsers.getUser(code);
            User newUser = (User)userAndToken.keySet().toArray()[0];

//            newUser.setGroups();
//            Group userGroup = GroupRepository.findByUserId("newUser.getUserId()");
//            newUser.setGroups(new HashSet<>(Arrays.asList(userGroup)));
//            userRepository.save(user);
            String token = userAndToken.get(newUser);
            User user = repository.findByuserId(newUser.getUserId());
            currentUser = user;
            if (user == null) {
                repository.save(newUser);
                currentUser = newUser;
            }
            return newUser.getUserId();
        }
    public void addGroup() {

        User user = repository.findByuserId(WebController.USER_ID);
        List <Group> groupList = gRepository.findByUserId(user.getUserId());
            user.setListGroups(groupList);
            repository.save(user);
            currentUser = user;


//        return repository.findByuserId(newUser.getUserId());
    }
    public User getUser(String userId){
        User user = repository.findByuserId(userId);
        return user;
    }

    }
