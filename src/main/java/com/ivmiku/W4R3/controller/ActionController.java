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

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/action")
public class ActionController {

    @Autowired
    private ActionService actionService;

    @PostMapping("/like")
    public Object Like(@RequestBody ActionInput input) {
        JwtUser user = KrestUtil.getJwtUser();
        Base base = new Base();
        if (input.getComment_id() == null) {
            VideoLike video = new VideoLike();
            video.setUserid(actionService.getUserId(user.getUsername()));
            video.setVideoid(input.getVideo_id());
            if (input.getAction_type().equals("1")){
                base = actionService.likeVideo(video);
            } else {
                actionService.deleteLikeVideo(video);
            }
        } else if (input.getVideo_id() == null) {
            CommentLike comment = new CommentLike();
            comment.setUserId(actionService.getUserId(user.getUsername()));
            comment.setCommentId(input.getComment_id());
            if (input.getAction_type().equals("1")){
                base = actionService.likeComment(comment);
            } else {
                actionService.deleteLikeComment(comment);
            }
        }
        JSONObject json = new JSONObject();
        json.put("base", base);
        return JSON.toJSON(json);
    }

    @GetMapping("/likelist")
    public Object getLikeList(@RequestParam String id) {
        List<Video> list = actionService.getLikeList(id);
        JSONObject json = new JSONObject();
        Base base = new Base();
        base.setCode(10000);
        base.setMsg("success");
        json.put("base", base);
        json.put("data", list);
        return JSON.toJSON(json);
    }

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
        actionService.comment(comment);
        JSONObject json = new JSONObject();
        Base base = new Base();
        base.setCode(10000);
        base.setMsg("success");
        json.put("base", base);
        return JSON.toJSON(json);
    }

    @GetMapping("/getcomlist")
    public Object getCommentList(@RequestParam String video_id) {
        List<Comment> list = actionService.getCommentList(video_id);
        JSONObject json = new JSONObject();
        Base base = new Base();
        base.setCode(10000);
        base.setMsg("success");
        json.put("base", base);
        json.put("data", list);
        return JSON.toJSON(json);
    }

    @GetMapping("/getchild")
    public Object getChildComment(@RequestParam String comment_id) {
        List<Comment> list = actionService.getChildComment(comment_id);
        JSONObject json = new JSONObject();
        Base base = new Base(10000, "success");
        json.put("base", base);
        json.put("data", list);
        return JSON.toJSON(json);
    }

    @DeleteMapping("/delete")
    public Object deleteComment(@RequestBody CommentInput input) {
        actionService.deleteComment(input.getComment_id());
        JSONObject json = new JSONObject();
        Base base = new Base();
        base.setCode(10000);
        base.setMsg("success");
        json.put("base", base);
        return JSON.toJSON(json);
    }
}

