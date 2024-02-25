package com.ivmiku.W4R3.service;


import com.ivmiku.W4R3.mapper.UserMapper;
import com.ivmiku.W4R3.mapper.VideoMapper;
import com.ivmiku.W4R3.pojo.User;
import com.ivmiku.W4R3.pojo.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service
public class VideoService {
    @Autowired
    VideoMapper videoMapper;

    @Autowired
    UserMapper userMapper;

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

    public void uploadVideo(Video video) {
        videoMapper.insert(video);
    }

    public User getUserByName(String username) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("username", username);
        List<User> l = userMapper.selectByMap(param);
        return l.get(0);
    }

    public List<Video> getList(String userId) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("user_id", userId);
        return videoMapper.selectByMap(param);
    }
}
