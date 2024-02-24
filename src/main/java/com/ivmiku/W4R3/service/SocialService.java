package com.ivmiku.W4R3.service;

import com.ivmiku.W4R3.mapper.SubscribeMapper;
import com.ivmiku.W4R3.mapper.UserMapper;
import com.ivmiku.W4R3.pojo.SubUser;
import com.ivmiku.W4R3.pojo.Subscribe;
import com.ivmiku.W4R3.pojo.User;
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

    public int subscribe(Subscribe sub) {
        subscribeMapper.insert(sub);
        return 1;
    }

    public int delete(Subscribe sub) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", sub.getId());
        params.put("subid", sub.getSubId());
        subscribeMapper.deleteByMap(params);
        return 0;
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
        params.put("subid", id);
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

    public List<SubUser> getFriendList(Long id) {
        List<SubUser> list1 = getFanList(id);
        List<SubUser> list2 = getSubList(id);
        list1.retainAll(list2);
        return list1;
    }
}
