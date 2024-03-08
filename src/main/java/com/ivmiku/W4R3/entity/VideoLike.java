package com.ivmiku.W4R3.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("videolike")
@Data
public class VideoLike {
    private String videoid;
    private String userid;
}
