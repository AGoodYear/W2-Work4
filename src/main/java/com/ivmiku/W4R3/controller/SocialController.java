package com.ivmiku.W4R3.controller;

import com.chenkaiwei.krest.KrestUtil;
import com.chenkaiwei.krest.entity.JwtUser;
import com.ivmiku.W4R3.pojo.SubUser;
import com.ivmiku.W4R3.pojo.Subscribe;
import com.ivmiku.W4R3.pojo.UserSub;
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
    public String subscribe(@RequestBody UserSub sub) {
        JwtUser user = KrestUtil.getJwtUser();
        String id = socialService.getUserId(user.getUsername());
        Subscribe subItem = new Subscribe();
        subItem.setId(sub.getTo_user_id());
        subItem.setSubId(sub.getTo_user_id());
        if (sub.getAction_type() == 1) {
            socialService.subscribe(subItem);
        } else if (sub.getAction_type() == 0) {
            socialService.delete(subItem);
        }
        return "s";
    }
//todo
    @GetMapping("/sublist")
    public String getSubList(@RequestParam Long userId) {
        List<SubUser> list = socialService.getSubList(userId);
        return "OK!";
    }
}
