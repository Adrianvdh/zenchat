package com.zenchat.server.api.user;

import com.zenchat.model.api.listuser.ListUsersRequest;
import com.zenchat.model.api.listuser.UserListResponse;
import com.zenchat.server.api.user.model.User;
import com.zenchat.server.api.user.repository.UserRepository;
import com.zenchat.server.message.MessageHandler;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ListUserHandler implements MessageHandler<UserListResponse, ListUsersRequest> {

    private UserRepository userRepository;

    public ListUserHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserListResponse handle(ListUsersRequest request) {

        List<User> users = userRepository.findAll();
        if(users == null) {
            return new UserListResponse(Collections.emptyList());
        }

        List<String> usernames = users.stream().map(User::getUsername).collect(Collectors.toList());
        return new UserListResponse(usernames);
    }
}
