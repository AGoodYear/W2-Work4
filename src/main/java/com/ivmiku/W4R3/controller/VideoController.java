package com.ivmiku.W4R3.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chenkaiwei.krest.KrestUtil;
import com.chenkaiwei.krest.entity.JwtUser;
import com.ivmiku.W4R3.entity.Base;
import com.ivmiku.W4R3.entity.Video;
import com.ivmiku.W4R3.entity.VideoInput;
import com.ivmiku.W4R3.service.VideoService;
import com.ivmiku.W4R3.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/video")
@Slf4j
public class VideoController {
    @Autowired
    private VideoService service;

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/list")
    public Object getVideoList(@RequestParam String user_id){
        List<Video> list =  service.getList(user_id);
        JSONObject json = new JSONObject();
        Base base = new Base();
        base.setCode(10000);
        base.setMsg("success");
        json.put("base", base);
        json.put("data", list);
        return JSON.toJSON(json);
    }

    @PostMapping("/upload")
    public Object upload(@RequestPart("file") MultipartFile file, @RequestPart("cover") MultipartFile cover, @RequestParam("title") String title, @RequestParam("description") String description) throws IOException {
        //获取文件名
        String fileName = file.getOriginalFilename();
        log.info("上传的文件名："+fileName);
        //获取文件后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        Base base = new Base();
        JSONObject json = new JSONObject();
        if (suffixName != "mp4") {
            base.setCode(-1);
            base.setMsg("上传的文件非mp4格式文件");
            json.put("base", base);
            return JSON.toJSON(json);
        }
        //设置文件存储路径
        String filePath = "d:/upload/";
        String path = filePath+fileName;
        File dest = new File(path);
        String path2 =filePath+cover.getOriginalFilename();
        File dest2 = new File(path2);
        //检测是否存在该目录
        if (!dest.getParentFile().exists()){
            dest.getParentFile().mkdirs();
        }
        //写入文件
        file.transferTo(dest);
        cover.transferTo(dest2);
        JwtUser user = KrestUtil.getJwtUser();
        Video video = new Video();
        video.setUserId(service.getUserByName(user.getUsername()).getId());
        video.setVideoUrl(path);
        video.setTitle(title);
        video.setDescription(description);
        video.setCoverUrl(path2);
        service.uploadVideo(video);
        base.setCode(10000);
        base.setMsg("上传成功");
        json.put("base", base);
        return JSON.toJSON(json);
    }

    @PostMapping("/search")
    public Object search(@RequestBody VideoInput input) {
        JSONObject json = new JSONObject();
        List<Video> list = new ArrayList<>();
        if (input.getKeyword() != null) {
            list = service.searchVideo(input.getKeyword());
        } else if (input.getUsername() != null) {
            list = service.searchVideoByUser(input.getUsername());
        }
        json.put("data", list);
        redisUtil.insertList(KrestUtil.getJwtUser().getUsername(), input.getKeyword());
        Base base = new Base();
        base.setMsg("success");
        base.setCode(10000);
        json.put("base", base);
        return JSON.toJSON(json);
    }

    @GetMapping("play")
    public Object play(@RequestParam String video_id) {
        service.play(video_id);
        Base base = new Base();
        base.setMsg("success");
        base.setCode(10000);
        return JSON.toJSON(base);
    }

    @GetMapping("rank")
    public Object rankList() {
        List<Video> list = service.getRankList();
        Base base = new Base();
        base.setCode(10000);
        base.setMsg("success");
        JSONObject json = new JSONObject();
        json.put("base", base);
        json.put("data", list);
        return JSON.toJSON(json);
    }
}
