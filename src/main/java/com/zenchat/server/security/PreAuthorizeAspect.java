package com.zenchat.server.security;

import com.zenchat.server.api.user.model.User;

public class PreAuthorizeAspect {

    public boolean hasRole(User user, SecurityRole role) {
        return false;
    }
}
