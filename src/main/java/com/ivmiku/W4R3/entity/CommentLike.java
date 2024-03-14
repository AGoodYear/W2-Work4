package com.ivmiku.W4R3.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Aurora
 */
@TableName("commentlike")
@Data
public class CommentLike {
    private String commentId;
    private String userId;
}
