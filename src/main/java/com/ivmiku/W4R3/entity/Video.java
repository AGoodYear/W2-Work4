package com.ivmiku.W4R3.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("video")
@JSONType(alphabetic = false)
public class Video {
    @TableId(type = IdType.AUTO)
    @JSONField(ordinal = 1)
    private String id;
    @TableField
    @JSONField(ordinal = 2)
    private String userId;
    @TableField
    @JSONField(ordinal = 3)
    private String videoUrl;
    @TableField
    @JSONField(ordinal = 4)
    private String coverUrl;
    @TableField
    @JSONField(ordinal = 5)
    private String title;
    @JSONField(ordinal = 6)
    private String description;
    @JSONField(ordinal = 7)
    private Integer visitCount;
    @JSONField(ordinal = 8)
    private Integer likeCount;
    @JSONField(ordinal = 9)
    private Integer commentCount;
    @JSONField(ordinal = 10)
    private String createdAt;
    @JSONField(ordinal = 11)
    private String updatedAt;
    @JSONField(ordinal = 12)
    private String deletedAt;
}
