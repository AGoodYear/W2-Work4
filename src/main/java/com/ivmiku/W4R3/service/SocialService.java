package com.ivmiku.W4R3.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

/**
 * @author Aurora
 */
@Service
public class SocialService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SubscribeMapper subscribeMapper;

    /**
     * 获取用户id
     * @param username 用户名
     * @return 用户id
     */
    public String getUserId(String username) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        List<User> l = userMapper.selectByMap(params);
        User user = l.get(0);
        return user.getId();
    }

    /**
     * 关注
     * @param sub 关注操作相关信息
     * @return 操作结果
     */
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

    /**
     * 取关
     * @param sub 关注操作相关信息
     * @return 操作结果
     */
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

    /**
     * 获取关注列表
     * @param id 用户id
     * @param current 分页参数
     * @param size 分页参数
     * @return 关注列表
     */
    public List<SubUser> getSubList(Long id, int current, int size) {
        QueryWrapper<Subscribe> queryWrapper= new QueryWrapper<>();
        queryWrapper.eq("id", id);
        Page<Subscribe> page = new Page<>(current, size);
        Page<Subscribe> result = subscribeMapper.selectPage(page, queryWrapper);
        List<Subscribe> list = new ArrayList<>(result.getRecords());
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

    /**
     * 获取粉丝列表
     * @param id 用户id
     * @param current 分页参数
     * @param size 分页参数
     * @return 用户列表
     */
    public List<SubUser> getFanList(Long id, int current, int size) {
        QueryWrapper<Subscribe> queryWrapper= new QueryWrapper<>();
        queryWrapper.eq("sub_id", id);
        Page<Subscribe> page = new Page<>(current, size);
        Page<Subscribe> result = subscribeMapper.selectPage(page, queryWrapper);
        List<Subscribe> list = new ArrayList<>(result.getRecords());
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

    /**
     * 获取朋友列表
     * @param id 用户id
     * @return 朋友列表
     */
    public List<SubUser> getFriendList(Long id) {
        List<SubUser> list1 = getFanList(id,1,-1);
        List<SubUser> list2 = getSubList(id,1,-1);
        list1.retainAll(list2);
        return list1;
    }
}
