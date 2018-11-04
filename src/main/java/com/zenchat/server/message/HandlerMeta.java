package com.zenchat.server.message;

import com.zenchat.server.security.SecurityRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HandlerMeta {
    private SecurityRole roleRequired;
}
