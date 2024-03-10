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
    private String userId;
    private String videoId;
    private String parentId;
    private int likeCount;
    private int childCount;
    private String content;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
}
