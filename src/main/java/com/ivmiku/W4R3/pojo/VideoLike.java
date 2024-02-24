package com.ivmiku.W4R3.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("videolike")
@Data
public class VideoLike {
    private String videoId;
    private String userId;
}
