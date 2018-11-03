package com.zenchat.server.api.user;

import com.zenchat.model.api.login.LoginUserRequest;
import com.zenchat.model.api.login.UserLoginResponse;
import com.zenchat.server.api.user.exception.AuthenticationException;
import com.zenchat.server.api.user.model.Session;
import com.zenchat.server.api.user.model.User;
import com.zenchat.server.api.user.repository.UserRepository;
import com.zenchat.server.message.MessageHandler;
import org.joda.time.DateTime;

import java.util.Optional;
import java.util.UUID;

public class UserLoginHandler implements MessageHandler<UserLoginResponse, LoginUserRequest> {

    private UserRepository userRepository;

    public UserLoginHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserLoginResponse handle(LoginUserRequest loginUserRequest) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByUsername(loginUserRequest.getUsername()));
        if (!userOptional.isPresent()) {
            throw new AuthenticationException(String.format("User with username '%s' does not exists!", loginUserRequest.getUsername()));
        }

        User user = userOptional.get();
        if (!credentialsAreValid(loginUserRequest, user)) {
            throw new AuthenticationException("The credentials you have entered are incorrect!");
        }

        String sessionId = UUID.randomUUID().toString();
        Session session = new Session(sessionId, DateTime.now().plusDays(3));
        userRepository.updateUserSession(user.getUserId(), session);

        return new UserLoginResponse(sessionId);
    }

    private boolean credentialsAreValid(LoginUserRequest request, User user) {
        return user.getUsername().equals(request.getUsername()) && user.getPassword().equals(request.getPassword());
    }
}
