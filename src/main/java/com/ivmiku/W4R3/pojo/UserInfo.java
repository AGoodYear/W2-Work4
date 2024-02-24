package com.ivmiku.W4R3.pojo;

import lombok.Data;

@Data
public class UserInfo {
    private String id;
    private String username;
    private String avatar_url;
    private String created_at;
    private String updated_at;
    private String deleted_at;

    public void getInfo(User user) {
        id = user.getId();
        username = user.getUsername();
        avatar_url = user.getAvatarUrl();
        created_at = user.getCreatedAt();
        updated_at = user.getUpdatedAt();
        deleted_at = user.getDeletedAt();
    }
}
