package com.ivmiku.W4R3.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ivmiku.W4R3.mapper.*;
import com.ivmiku.W4R3.entity.*;
import com.ivmiku.W4R3.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
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
            base.setCode(-1);
            base.setMsg("视频不存在！");
            return base;
        }
        HashMap<String, Object> params = new HashMap<>();
        params.put("userid", video.getUserid());
        params.put("videoid", video.getVideoid());
        if (!videoLikeMapper.selectByMap(params).isEmpty()) {
            log.error("该用户已点过赞");
            base.setMsg("不可重复点赞！");
            base.setCode(-1);
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
    public Base likeComment(CommentLike comment) {
        Comment comment1 = commentMapper.selectById(comment.getCommentId());
        Base base = new Base();
        if (comment1 == null) {
            log.error("要点赞的评论不存在！");
            base.setMsg("评论不存在！");
            base.setCode(-1);
            return base;
        }
        HashMap<String, Object> params = new HashMap<>();
        params.put("user_id", comment.getUserId());
        params.put("comment_id", comment.getCommentId());
        if (!commentLikeMapper.selectByMap(params).isEmpty()) {
            log.error("该用户已点过赞");
            base.setMsg("不可重复点赞！");
            base.setCode(-1);
            return base;
        }
        commentLikeMapper.insert(comment);
        comment1.setLikeCount(comment1.getLikeCount()+1);
        commentMapper.updateById(comment1);
        log.info("评论点赞成功");
        base.setCode(10000);
        base.setMsg("success");
        return base;
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

    public List<Video> getLikeList(String id, int current, int size) {
        QueryWrapper<VideoLike> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid", id);
        Page<VideoLike> page = new Page<>(current, size);
        List<VideoLike> list = videoLikeMapper.selectPage(page, queryWrapper).getRecords();
        List<Video> videoList = new ArrayList<>();
        VideoService videoService = new VideoService();
        for (int i=0; i<list.size(); i++) {
            videoList.add(videoMapper.selectById(list.get(i).getVideoid()));
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

    @Transactional
    public String comment(Comment comment) {
        commentMapper.insert(comment);
        if (comment.getParentId() != null) {
            Comment parentComment = commentMapper.selectById(comment.getParentId());
            parentComment.setChildCount(parentComment.getChildCount()+1);
            commentMapper.updateById(parentComment);
        }
        return "OK";
    }

    public List<Comment> getCommentList(String video_id) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("video_id", video_id);
        List<Comment> list = commentMapper.selectByMap(param);
        return list;
    }

    public List<Comment> getChildComment(String comment_id) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("parent_id", comment_id);
        return commentMapper.selectByMap(param);
    }

    public String deleteComment(String comment_id) {
        commentMapper.deleteById(comment_id);
        return "ok";
    }
}
