package com.ivmiku.W4R3.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("subscribe")
@Data
public class Subscribe {
    private Long id;
    private Long subId;
}
