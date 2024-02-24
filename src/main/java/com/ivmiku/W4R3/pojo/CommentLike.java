package com.ivmiku.W4R3.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("commentlike")
@Data
public class CommentLike {
    private String commentId;
    private String userId;
}
