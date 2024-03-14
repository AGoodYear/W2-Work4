package com.ivmiku.W4R3.entity;

import lombok.Data;

/**
 * @author Aurora
 */
@Data
public class CommentInput {
    private String video_id = null;
    private String comment_id = null;
    private String content;
}
