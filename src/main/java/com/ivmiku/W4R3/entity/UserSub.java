package com.ivmiku.W4R3.entity;

import lombok.Data;

/**
 * @author Aurora
 */
@Data
public class UserSub {
    private Long to_user_id;
    /**
     * 1关注，2取关
     */
    private int action_type;
}
