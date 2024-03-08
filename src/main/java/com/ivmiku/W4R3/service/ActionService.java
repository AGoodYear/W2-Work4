package com.ivmiku.W4R3.service;

import com.ivmiku.W4R3.mapper.*;
import com.ivmiku.W4R3.entity.*;
import com.ivmiku.W4R3.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ActionService {
    @Autowired
    private VideoLikeMapper videoLikeMapper;

    @Autowired
    private CommentLikeMapper commentLikeMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Transactional
    public Base likeVideo(VideoLike video) {
        Video videoEntity = videoMapper.selectById(video.getVideoid());
        Base base = new Base();
        if (videoEntity == null) {
            base.setCode(10000);
            base.setMsg("视频不存在！");
            return base;
        }
        videoLikeMapper.insert(video);
        videoEntity.setLikeCount(videoEntity.getVisitCount()+1);
        videoMapper.updateById(videoEntity);
        base.setCode(10000);
        base.setMsg("success");
        log.info("视频点赞成功");
        return base;
    }

    @Transactional
    public String likeComment(CommentLike comment) {
        commentLikeMapper.insert(comment);
        log.info("评论点赞成功");
        return "OK";
    }

    @Transactional
    public String deleteLikeVideo(VideoLike video) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("videoid", video.getVideoid());
        params.put("userid", video.getUserid());
        videoLikeMapper.deleteByMap(params);
        Video videoEntity = videoMapper.selectById(video.getVideoid());
        videoEntity.setLikeCount(videoEntity.getVisitCount()-1);
        videoMapper.updateById(videoEntity);
        return "OK";
    }

    @Transactional
    public String deleteLikeComment(CommentLike comment) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("commentid", comment.getCommentId());
        params.put("userid", comment.getUserId());
        videoLikeMapper.deleteByMap(params);
        return "OK";
    }

    public List<Video> getLikeList(String id) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("userid", id);
        List<VideoLike> list = videoLikeMapper.selectByMap(param);
        List<Video> videoList = new ArrayList<>();
        VideoService videoService = new VideoService();
        for (int i=0; i<list.size(); i++) {
            videoList.add(videoService.getVideoById(videoList.get(i).getId()));
        }
        return videoList;
    }

    public String getUserId(String username) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        List<User> l = userMapper.selectByMap(params);
        User user = l.get(0);
        return user.getId();
    }

    public String comment(Comment comment) {
        commentMapper.insert(comment);
        return "OK";
    }

    public List<Comment> getCommentList(String video_id) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("video_id", video_id);
        List<Comment> list = commentMapper.selectByMap(param);
        return list;
    }

    public String deleteComment(String comment_id) {
        commentMapper.deleteById(comment_id);
        return "ok";
    }
}
