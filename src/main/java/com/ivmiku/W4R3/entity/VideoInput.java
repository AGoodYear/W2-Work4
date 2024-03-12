package com.ivmiku.W4R3.entity;

import lombok.Data;

@Data
public class VideoInput {
    private String keyword = null;
    private String username = null;
    private Integer page;
    private Integer size;
}
