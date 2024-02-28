package com.ivmiku.W4R3.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ivmiku.W4R3.mapper.UserMapper;
import com.ivmiku.W4R3.mapper.VideoMapper;
import com.ivmiku.W4R3.pojo.User;
import com.ivmiku.W4R3.pojo.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public List<Video> searchVideo(String keyword) {
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("title", keyword);
        List<Video> temp1 = new ArrayList<>();
        temp1 = videoMapper.selectList(queryWrapper);
        queryWrapper.clear();
        queryWrapper.like("description", keyword);
        List<Video> temp2 = new ArrayList<>();
        temp2 = videoMapper.selectList(queryWrapper);
        Set<Video> set = new HashSet<>();
        set.addAll(temp1);
        set.addAll(temp2);
        List<Video> list = new ArrayList<>(set);
        return list;
    }
}
