package com.zenchat.server.api.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Session {
    private String sessionId;
    private DateTime expirationDate;
}
