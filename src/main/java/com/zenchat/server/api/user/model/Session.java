package com.zenchat.server.api.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.joda.time.DateTime;

@Data
@AllArgsConstructor
public class Session {
    private String sessionId;
    private DateTime expirationDate;
}
