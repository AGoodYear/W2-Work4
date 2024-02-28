package com.ivmiku.W4R3.pojo;

import lombok.Data;

@Data
public class VideoInput {
    private String keyword;
    private Integer page_size;
    private Integer page_num;
}
