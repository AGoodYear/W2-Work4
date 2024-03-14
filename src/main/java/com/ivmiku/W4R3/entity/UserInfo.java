package com.ivmiku.W4R3.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import lombok.Data;

/**
 * @author Aurora
 */
@Data
@JSONType(alphabetic = false)
public class UserInfo {
    @JSONField(ordinal = 1)
    private String id;
    @JSONField(ordinal = 2)
    private String username;
    @JSONField(ordinal = 3)
    private String avatar_url;
    @JSONField(ordinal = 4)
    private String created_at;
    @JSONField(ordinal = 5)
    private String updated_at;
    @JSONField(ordinal = 6)
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
