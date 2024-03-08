package com.ivmiku.W4R3.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("video")
public class Video {
    @TableId(type = IdType.AUTO)
    private String id;
    @TableField
    private String userId;
    @TableField
    private String videoUrl;
    @TableField
    private String coverUrl;
    @TableField
    private String title;
    private String description;
    private Integer visitCount;
    private Integer likeCount;
    private Integer commentCount;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
}
