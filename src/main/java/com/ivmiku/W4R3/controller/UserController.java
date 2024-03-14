package com.ivmiku.W4R3.controller;

import com.alibaba.fastjson.JSON;
import com.chenkaiwei.krest.KrestUtil;
import com.chenkaiwei.krest.entity.JwtUser;
import com.ivmiku.W4R3.entity.Base;
import com.ivmiku.W4R3.entity.User;
import com.ivmiku.W4R3.entity.UserInput;
import com.ivmiku.W4R3.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@Slf4j
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService service;

    /**
     * 获取用户信息
     * @param id 用户id
     * @return 用户信息
     */
    @GetMapping("/info")
    public Object getUser(@RequestParam Long id){
        return service.getUser(id);
    }

    /**
     * 登录
     * @param user UserInput
     * @return 操作结果
     */
    @PostMapping("/login")
    public Object login(@RequestBody User user) {
        return service.login(user.getUsername(), user.getPassword());
    }

    /**
     * 注册
     * @param input UserInput
     * @return 操作结果
     */
    @PostMapping("/register")
    public Object register(@RequestBody UserInput input) {
        return service.register(input.getUsername(), input.getPassword());
    }

    /**
     * 上传头像
     * @param file 要上传的头像文件
     * @return 操作结果
     */
    @PutMapping("/avatar")
    public Object uploadAvatar(@RequestParam MultipartFile file) {
        JwtUser jwtUser = KrestUtil.getJwtUser();
        try {
            if (file.isEmpty()){
                Base base = new Base(-1, "文件为空！");
                return JSON.toJSON(base);
            }
            //获取文件名
            String fileName = file.getOriginalFilename();
            log.info("上传的文件名："+fileName);
            //获取文件后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            log.info("文件后缀名："+suffixName);
            //设置文件存储路径
            String filePath = "/home/danmaku/avatar/";
            String path = filePath+fileName;
            File dest = new File(path);
            //检测是否存在该目录
            if (!dest.getParentFile().exists()){
                dest.getParentFile().mkdirs();
            }
            //写入文件
            file.transferTo(dest);
            service.setAvatarUrl(path,jwtUser.getUsername());
            Base base = new Base(10000, "success");
            return JSON.toJSON(base);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Base base = new Base(-1, "上传失败！");
        return JSON.toJSON(base);
    }
}
