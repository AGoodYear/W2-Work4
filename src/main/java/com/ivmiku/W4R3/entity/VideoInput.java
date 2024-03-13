package com.ivmiku.W4R3.entity;

import lombok.Data;

/**
 * @author Aurora
 */
@Data
public class VideoInput {
    private String keyword = null;
    private String username = null;
    private String time = null;
    private Integer page = 1;
    private Integer size = -1;
}
