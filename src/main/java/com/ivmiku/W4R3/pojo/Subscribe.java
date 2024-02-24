package com.ivmiku.W4R3.pojo;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("subscribe")
@Data
public class Subscribe {
    private Long id;
    private Long subId;
}
