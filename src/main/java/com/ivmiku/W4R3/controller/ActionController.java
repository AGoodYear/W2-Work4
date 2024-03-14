package com.ivmiku.W4R3.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chenkaiwei.krest.KrestUtil;
import com.chenkaiwei.krest.entity.JwtUser;
import com.ivmiku.W4R3.entity.*;
import com.ivmiku.W4R3.service.ActionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/action")
public class ActionController {

    @Autowired
    private ActionService actionService;

    /**
     * 点赞
     * @param input Actioninput结构体
     * @return 执行结果
     */
    @PostMapping("/like")
    public Object Like(@RequestBody ActionInput input) {
        JwtUser user = KrestUtil.getJwtUser();
        Base base = new Base();
        if (input.getComment_id() == null) {
            VideoLike video = new VideoLike();
            video.setUserid(actionService.getUserId(user.getUsername()));
            video.setVideoid(input.getVideo_id());
            if ("1".equals(input.getAction_type())){
                base = actionService.likeVideo(video);
            } else {
                base = actionService.deleteLikeVideo(video);
            }
        } else if (input.getVideo_id() == null) {
            CommentLike comment = new CommentLike();
            comment.setUserId(actionService.getUserId(user.getUsername()));
            comment.setCommentId(input.getComment_id());
            if ("1".equals(input.getAction_type())){
                base = actionService.likeComment(comment);
            } else {
                base = actionService.deleteLikeComment(comment);
            }
        }
        JSONObject json = new JSONObject((new LinkedHashMap<>()));
        json.put("base", base);
        json.put("token", KrestUtil.createNewJwtTokenIfNeeded());
        return JSON.toJSON(json);
    }

    /**
     *
     * @param id 用户ID
     * @param page 分页页数
     * @param size 分页大小
     * @return
     */
    @GetMapping("/likelist")
    public Object getLikeList(@RequestParam String id, @RequestParam int page, @RequestParam int size) {
        List<Video> list = actionService.getLikeList(id, page, size);
        JSONObject json = new JSONObject();
        Base base = new Base();
        base.setCode(10000);
        base.setMsg("success");
        json.put("base", base);
        json.put("data", list);
        json.put("token", KrestUtil.createNewJwtTokenIfNeeded());
        return JSON.toJSON(json);
    }

    /**
     *
     * @param input CommentInput结构体
     * @return 执行结果
     */
    @PostMapping("/comment")
    public Object comment(@RequestBody CommentInput input) {
        JwtUser user = KrestUtil.getJwtUser();
        Comment comment = new Comment();
        comment.setContent(input.getContent());
        comment.setUserId(actionService.getUserId(user.getUsername()));
        comment.setVideoId(input.getVideo_id());
        if (input.getComment_id() != null) {
            comment.setParentId(input.getComment_id());
        }
        Base base = actionService.comment(comment);
        JSONObject json = new JSONObject((new LinkedHashMap<>()));
        json.put("base", base);
        json.put("token", KrestUtil.createNewJwtTokenIfNeeded());
        return JSON.toJSON(json);
    }

    /**
     *
     * @param video_id 要获取评论的视频id
     * @param page 分页参数
     * @param size 分页参数
     * @return 评论列表
     */
    @GetMapping("/getcomlist")
    public Object getCommentList(@RequestParam String video_id, @RequestParam int page, @RequestParam int size) {
        List<Comment> list = actionService.getCommentList(video_id, page, size);
        JSONObject json = new JSONObject((new LinkedHashMap<>()));
        Base base = new Base();
        base.setCode(10000);
        base.setMsg("success");
        json.put("base", base);
        json.put("data", list);
        json.put("token", KrestUtil.createNewJwtTokenIfNeeded());
        return JSON.toJSON(json);
    }

    /**
     *
     * @param comment_id 要获取子评论的评论id
     * @return 子评论列表
     */
    @GetMapping("/getchild")
    public Object getChildComment(@RequestParam String comment_id) {
        List<Comment> list = actionService.getChildComment(comment_id);
        JSONObject json = new JSONObject((new LinkedHashMap<>()));
        Base base = new Base(10000, "success");
        json.put("base", base);
        json.put("data", list);
        json.put("token", KrestUtil.createNewJwtTokenIfNeeded());
        return JSON.toJSON(json);
    }

    /**
     *
     * @param input Commentinput结构体
     * @return 执行结构
     */
    @DeleteMapping("/delete")
    public Object deleteComment(@RequestBody CommentInput input) {
        Base base = actionService.deleteComment(input.getComment_id());
        JSONObject json = new JSONObject((new LinkedHashMap<>()));
        json.put("base", base);
        json.put("token", KrestUtil.createNewJwtTokenIfNeeded());
        return JSON.toJSON(json);
    }
}

