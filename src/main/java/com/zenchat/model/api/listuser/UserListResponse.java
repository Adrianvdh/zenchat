package com.zenchat.model.api.listuser;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class UserListResponse implements Serializable {
    private List<String> users;
}
