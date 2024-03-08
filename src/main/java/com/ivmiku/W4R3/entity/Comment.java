package com.ivmiku.W4R3.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("comment")
public class Comment {
    @TableId(type = IdType.AUTO)
    private String id;
    private String user_id;
    private String video_id;
    private String parent_id;
    private int like_count;
    private int child_count;
    private String content;
    private String created_at;
    private String updated_at;
    private String deleted_at;
}
