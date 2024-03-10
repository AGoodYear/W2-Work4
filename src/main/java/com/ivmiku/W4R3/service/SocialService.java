package com.ivmiku.W4R3.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ivmiku.W4R3.entity.Base;
import com.ivmiku.W4R3.mapper.SubscribeMapper;
import com.ivmiku.W4R3.mapper.UserMapper;
import com.ivmiku.W4R3.entity.SubUser;
import com.ivmiku.W4R3.entity.Subscribe;
import com.ivmiku.W4R3.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SocialService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SubscribeMapper subscribeMapper;

    public String getUserId(String username) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        List<User> l = userMapper.selectByMap(params);
        User user = l.get(0);
        return user.getId();
    }

    public Base subscribe(Subscribe sub) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", sub.getId());
        params.put("subid", sub.getSubId());
        Base base = new Base();
        if (!subscribeMapper.selectByMap(params).isEmpty()) {
            base.setCode(-1);
            base.setMsg("不可重复关注！");
            return base;
        }
        subscribeMapper.insert(sub);
        base.setCode(10000);
        base.setMsg("success");
        return base;
    }

    public Base delete(Subscribe sub) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", sub.getId());
        params.put("subid", sub.getSubId());
        Base base = new Base();
        if (subscribeMapper.selectByMap(params).isEmpty()) {
            base.setMsg("未关注该用户！");
            base.setCode(-1);
            return base;
        }
        subscribeMapper.deleteByMap(params);
        base.setMsg("success");
        base.setCode(10000);
        return base;
    }

    public List<SubUser> getSubList(Long id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        List<Subscribe> list = subscribeMapper.selectByMap(params);
        List<SubUser> subList = new ArrayList<>();
        for (int i=0; i<list.size(); i++) {
            User user = userMapper.selectById(list.get(i).getSubId());
            SubUser sb = new SubUser();
            sb.setId(user.getId());
            sb.setUsername(user.getUsername());
            sb.setAvatar_url(user.getAvatarUrl());
            subList.add(sb);
        }
        return subList;
    }

    public List<SubUser> getFanList(Long id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("sub_id", id);
        List<Subscribe> list = subscribeMapper.selectByMap(params);
        List<SubUser> subList = new ArrayList<>();
        for (int i=0; i<list.size(); i++) {
            User user = userMapper.selectById(list.get(i).getId());
            SubUser sb = new SubUser();
            sb.setId(user.getId());
            sb.setUsername(user.getUsername());
            sb.setAvatar_url(user.getAvatarUrl());
            subList.add(sb);
        }
        return subList;
    }

    public List<SubUser> getFriendList(Long id) {
        List<SubUser> list1 = getFanList(id);
        List<SubUser> list2 = getSubList(id);
        list1.retainAll(list2);
        return list1;
    }
}
