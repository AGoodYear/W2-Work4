package com.ivmiku.W4R3.entity;

import lombok.Data;

@Data
public class ActionInput {
    private String video_id = null;
    private String comment_id = null;
    private String action_type;
}
