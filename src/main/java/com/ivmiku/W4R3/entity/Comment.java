package com.ivmiku.W4R3.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Aurora
 */
@Data
@TableName("comment")
@JSONType(alphabetic = false)
public class Comment {
    @TableId(type = IdType.AUTO)
    @JSONField(ordinal = 1)
    private String id;
    @JSONField(ordinal = 2)
    private String userId;
    @JSONField(ordinal = 3)
    private String videoId;
    @JSONField(ordinal = 4)
    private String parentId;
    @JSONField(ordinal = 5)
    private int likeCount;
    @JSONField(ordinal = 6)
    private int childCount;
    @JSONField(ordinal = 7)
    private String content;
    @JSONField(ordinal = 8)
    private String createdAt;
    @JSONField(ordinal = 9)
    private String updatedAt;
    @JSONField(ordinal = 10)
    private String deletedAt;
}
