package com.ivmiku.W4R3.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chenkaiwei.krest.KrestUtil;
import com.chenkaiwei.krest.entity.JwtUser;
import com.ivmiku.W4R3.entity.Base;
import com.ivmiku.W4R3.entity.SubUser;
import com.ivmiku.W4R3.entity.Subscribe;
import com.ivmiku.W4R3.entity.UserSub;
import com.ivmiku.W4R3.service.SocialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/social")
public class SocialController {
    @Autowired
    private SocialService socialService;

    @PostMapping("/subscribe")
    public Object subscribe(@RequestBody UserSub sub) {
        JwtUser user = KrestUtil.getJwtUser();
        String id = socialService.getUserId(user.getUsername());
        Subscribe subItem = new Subscribe();
        subItem.setId(Long.valueOf(id));
        subItem.setSubId(sub.getTo_user_id());
        Base base = new Base();
        if (sub.getAction_type() == 1) {
            base = socialService.subscribe(subItem);
        } else if (sub.getAction_type() == 0) {
            base = socialService.delete(subItem);
        }
        return JSON.toJSON(base);
    }

    @GetMapping("/sublist")
    public Object getSubList(@RequestParam Long id, @RequestParam int page, @RequestParam int size) {
        List<SubUser> list = socialService.getSubList(id, page, size);
        Base base = new Base(10000,"success");
        JSONObject json = new JSONObject();
        json.put("data", list);
        json.put("base", base);
        json.put("token", KrestUtil.createNewJwtTokenIfNeeded());
        return JSON.toJSON(json);
    }

    @GetMapping("/fanlist")
    public Object getFanList(@RequestParam Long id, @RequestParam int page, @RequestParam int size) {
        List<SubUser> list = socialService.getFanList(id, page, size);
        Base base = new Base(10000,"success");
        JSONObject json = new JSONObject();
        json.put("data", list);
        json.put("base", base);
        json.put("token", KrestUtil.createNewJwtTokenIfNeeded());
        return JSON.toJSON(json);
    }

    @GetMapping("/friend")
    public Object getFriendList(@RequestParam Long id) {
        List<SubUser> list = socialService.getFriendList(id);
        Base base = new Base(10000,"success");
        JSONObject json = new JSONObject();
        json.put("data", list);
        json.put("base", base);
        json.put("token", KrestUtil.createNewJwtTokenIfNeeded());
        return JSON.toJSON(json);
    }
}
