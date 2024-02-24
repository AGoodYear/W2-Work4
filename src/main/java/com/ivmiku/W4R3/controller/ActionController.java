package com.ivmiku.W4R3.controller;

import com.chenkaiwei.krest.KrestUtil;
import com.chenkaiwei.krest.entity.JwtUser;
import com.ivmiku.W4R3.pojo.*;
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
    public String Like(@RequestBody ActionInput input) {
        JwtUser user = KrestUtil.getJwtUser();
        if (input.getComment_id() == null) {
            VideoLike video = new VideoLike();
            video.setUserId(actionService.getUserId(user.getUsername()));
            video.setVideoId(input.getVideo_id());
            if (input.getAction_type().equals("1")){
                actionService.likeVideo(video);
            } else {
                actionService.deleteLikeVideo(video);
            }
        } else if (input.getVideo_id() == null) {
            CommentLike comment = new CommentLike();
            comment.setUserId(actionService.getUserId(user.getUsername()));
            comment.setCommentId(input.getComment_id());
            if (input.getAction_type().equals("1")){
                actionService.likeComment(comment);
            } else {
                actionService.deleteLikeComment(comment);
            }
        }
        return "OK";
    }

    @GetMapping("/likelist")
    public String getLikeList(@RequestParam String id) {
        List<Video> list = actionService.getLikeList(id);
        return "OK";
    }

    @PostMapping("/comment")
    public String comment(@RequestBody CommentInput input) {
        JwtUser user = KrestUtil.getJwtUser();
        Comment comment = new Comment();
        comment.setContent(input.getContent());
        comment.setUser_id(actionService.getUserId(user.getUsername()));
        comment.setVideo_id(input.getVideo_id());
        if (input.getComment_id() != null) {
            comment.setParent_id(input.getComment_id());
        }
        actionService.comment(comment);
        return "OK";
    }

    @GetMapping("/getcomlist")
    public String getCommentList(@RequestParam String video_id) {
        actionService.getCommentList(video_id);
        return "OK";
    }

    @DeleteMapping("delete")
    public String deleteComment(@RequestBody CommentInput input) {
        actionService.deleteComment(input.getComment_id());
        return "OK";
    }
}

