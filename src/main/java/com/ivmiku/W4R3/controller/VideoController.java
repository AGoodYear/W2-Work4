package com.ivmiku.W4R3.controller;


import com.alibaba.fastjson.JSONObject;
import com.chenkaiwei.krest.KrestUtil;
import com.chenkaiwei.krest.entity.JwtUser;
import com.ivmiku.W4R3.pojo.Base;
import com.ivmiku.W4R3.pojo.Video;
import com.ivmiku.W4R3.service.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/video")
@Slf4j
public class VideoController {
    @Autowired
    private VideoService service;

    @GetMapping("/list")
    public String getVideoList(@RequestParam String userId){
        List<Video> list =  service.getList(userId);
        JSONObject json = new JSONObject();
        Base base = new Base();
        base.setCode(10000);
        base.setMsg("success");
        json.put("base", base);
        json.put("data", list);
        return json.toString();
    }

    @PostMapping("/upload")
    public String upload(@RequestPart("file") MultipartFile file, @RequestPart("cover") MultipartFile cover, @RequestParam("title") String title, @RequestParam("description") String description) throws IOException {
        //获取文件名
        String fileName = file.getOriginalFilename();
        log.info("上传的文件名："+fileName);
        //获取文件后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
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
        Base base = new Base();
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
        JSONObject json = new JSONObject();
        json.put("base", base);
        return json.toString();
    }
}
