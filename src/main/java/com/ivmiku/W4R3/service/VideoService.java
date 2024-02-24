package com.ivmiku.W4R3.service;


import com.ivmiku.W4R3.mapper.VideoMapper;
import com.ivmiku.W4R3.pojo.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class VideoService {
    @Autowired
    VideoMapper videoMapper;

    public Video getVideoById(String videoId) {
        return videoMapper.selectById(videoId);
    }

    public List<Video> getAll() {
        return Arrays.asList(videoMapper.selectById(1));
    }

    public Video selectById(String id) {
        return videoMapper.selectById(id);
    }

    public void updateInfo(Video video) {
        videoMapper.updateById(video);
    }
}
