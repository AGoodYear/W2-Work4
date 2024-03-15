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

/**
 * @author Aurora
 */
@RestController
@RequestMapping("/api/video")
@Slf4j
public class VideoController {
    @Autowired
    private VideoService service;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 获取用户上传的视频列表
     * @param user_id 用户id
     * @param page 分页参数
     * @param size 分页参数
     * @return 视频列表
     */
    @GetMapping("/list")
    public Object getVideoList(@RequestParam String user_id, @RequestParam int page, @RequestParam int size){
        List<Video> list =  service.getList(user_id, page, size);
        JSONObject json = new JSONObject();
        Base base = new Base();
        base.setCode(10000);
        base.setMsg("success");
        json.put("base", base);
        json.put("data", list);
        return JSON.toJSON(json);
    }

    /**
     * 投稿
     * @param file 视频文件
     * @param cover 视频封面
     * @param title 视频标题
     * @param description 视频描述
     * @return 操作结果
     * @throws IOException 上传异常
     */
    @PostMapping("/upload")
    public Object upload(@RequestPart("file") MultipartFile file, @RequestPart("cover") MultipartFile cover, @RequestParam("title") String title, @RequestParam("description") String description) throws IOException {
        //获取文件名
        String fileName = file.getOriginalFilename();
        log.info("上传的文件名："+fileName);
        //获取文件后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        Base base = new Base();
        JSONObject json = new JSONObject();
        if (!".mp4".equals(suffixName)) {
            base.setCode(-1);
            base.setMsg("上传的文件非mp4格式文件");
            json.put("base", base);
            return JSON.toJSON(json);
        }
        //设置文件存储路径
        String filePath = "/home/danmaku/video";
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
        json.put("token", KrestUtil.createNewJwtTokenIfNeeded());
        return JSON.toJSON(json);
    }

    /**
     * 搜索视频
     * @param input VideoInput， 根据输入的条件不同查询视频
     * @return 视频列表
     */
    @PostMapping("/search")
    public Object search(@RequestBody VideoInput input) {
        JSONObject json = new JSONObject();
        List<Video> list = new ArrayList<>();
        if (input.getKeyword() != null) {
            list = service.searchVideo(input.getKeyword(), input.getPage(), input.getSize());
            redisUtil.insertList(KrestUtil.getJwtUser().getUsername(), input.getKeyword());
        } else if (input.getUsername() != null) {
            list = service.searchVideoByUser(input.getUsername(), input.getPage(), input.getSize());
            redisUtil.insertList(KrestUtil.getJwtUser().getUsername(), input.getUsername());
        } else if (input.getTime() != null) {
            list = service.searchVideoByDate(input.getTime(), input.getPage(), input.getSize());
            redisUtil.insertList(KrestUtil.getJwtUser().getUsername(), input.getTime());
        }
        json.put("data", list);
        Base base = new Base();
        base.setMsg("success");
        base.setCode(10000);
        json.put("base", base);
        json.put("token", KrestUtil.createNewJwtTokenIfNeeded());
        return JSON.toJSON(json);
    }

    /**
     * 播放（播放量+1）
     * @param video_id 视频id
     * @return 执行结果
     */
    @GetMapping("play")
    public Object play(@RequestParam String video_id) {
        service.play(video_id);
        Base base = new Base();
        base.setMsg("success");
        base.setCode(10000);
        return JSON.toJSON(base);
    }

    /**
     * 视频排行榜
     * @return 视频排行
     */
    @GetMapping("/rank")
    public Object rankList() {
        List<Video> list = service.getRankList();
        Base base = new Base();
        base.setCode(10000);
        base.setMsg("success");
        JSONObject json = new JSONObject();
        json.put("base", base);
        json.put("data", list);
        json.put("token", KrestUtil.createNewJwtTokenIfNeeded());
        return JSON.toJSON(json);
    }

    /**
     * 搜索历史
     * @return 搜索历史关键词20个
     */
    @GetMapping("/history")
    public Object getHistory() {
        List<String> list = service.getSearchHistory(KrestUtil.getJwtUser().getUsername());
        JSONObject json = new JSONObject();
        Base base = new Base(10000,"success");
        json.put("base", base);
        json.put("data", list);
        json.put("token", KrestUtil.createNewJwtTokenIfNeeded());
        return JSON.toJSON(json);
    }
}
