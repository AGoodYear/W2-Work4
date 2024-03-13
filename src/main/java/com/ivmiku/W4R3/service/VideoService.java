package com.ivmiku.W4R3.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ivmiku.W4R3.mapper.UserMapper;
import com.ivmiku.W4R3.mapper.VideoMapper;
import com.ivmiku.W4R3.entity.User;
import com.ivmiku.W4R3.entity.Video;
import com.ivmiku.W4R3.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class VideoService {
    @Autowired
    VideoMapper videoMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisUtil redisUtil;

    public Video getVideoById(String videoId) {
        return videoMapper.selectById(videoId);
    }

    public List<Video> getAll() {
        return videoMapper.selectList(null);
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

    public List<Video> getList(String userId, int current, int size) {
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        Page<Video> page = new Page<>(current, size);
        return videoMapper.selectPage(page, queryWrapper).getRecords();
    }

    public List<Video> searchVideo(String keyword, int current, int size) {
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("title", keyword);
        Page<Video> page = new Page<>(current, size);
        List<Video> temp1 = videoMapper.selectPage(page, queryWrapper).getRecords();
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

    public List<Video> searchVideoByUser(String username, int current, int size) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = userMapper.selectOne(queryWrapper);
        QueryWrapper<Video> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("user_id", user.getId());
        Page<Video> page = new Page<>(current, size);
        return videoMapper.selectPage(page, queryWrapper1).getRecords();
    }

    public List<Video> searchVideoByDate(String time, int current, int size) {
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("created_at", time);
        Page<Video> page = new Page<>(current, size);
        return videoMapper.selectPage(page, queryWrapper).getRecords();
    }

    public void play(String video_id) {
        redisUtil.zsIncr("visit_count", video_id, 1);
    }

    public List<Video> getRankList() {
        Set<DefaultTypedTuple> set = redisUtil.zsReverseRangeWithScores("visit_count");
        List list = new ArrayList<>();
        set.forEach(item->{
            DefaultTypedTuple ii= (DefaultTypedTuple)item;
            Long id = Long.valueOf((String)ii.getValue());

            list.add(selectById(id.toString()));
        });
        return list;
    }

    public List<String> getSearchHistory(String username) {
        List<String> list = redisUtil.getList(username, 1, 20);
        return list;
    }
}
