package com.ivmiku.W4R3.entity;

import lombok.Data;

/**
 * @author Aurora
 */
@Data
public class ActionInput {
    private String video_id = null;
    private String comment_id = null;
    /**
     * 1点赞， 2取消
     */
    private String action_type;
}
