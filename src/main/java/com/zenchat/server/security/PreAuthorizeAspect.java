package com.zenchat.server.security;

import com.zenchat.common.message.Message;
import com.zenchat.server.api.user.model.User;
import com.zenchat.server.api.user.repository.UserRepository;
import com.zenchat.server.message.HandlerMeta;

import static com.zenchat.common.message.HeadersProperties.SESSION_ID;

public class PreAuthorizeAspect {
    private UserRepository userRepository;

    public PreAuthorizeAspect(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void preAuthorize(HandlerMeta handlerMeta, Message message) {
        if (handlerMeta == null) {
            return;
        }

        if (!message.getHeaders().containsHeader(SESSION_ID)) {
            throw new AuthorizationException("Please login first!");
        }
        SecurityRole roleRequired = handlerMeta.getRoleRequired();
        String sessionId = message.getHeaders().get(SESSION_ID);

        User user = userRepository.findBySessionId(sessionId);
        SecurityRole userSecurityRole = user.getSecurityRole();

        if (roleRequired != userSecurityRole) {
            throw new AuthorizationException("You don't have permission to do this!");
        }
    }
}
