package com.ivmiku.W4R3.pojo;

import lombok.Data;

@Data
public class CommentInput {
    private String video_id = null;
    private String comment_id = null;
    private String content;
}
