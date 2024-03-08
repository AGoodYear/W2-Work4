package com.ivmiku.W4R3.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chenkaiwei.krest.KrestUtil;
import com.chenkaiwei.krest.entity.JwtUser;
import com.ivmiku.W4R3.mapper.UserMapper;
import com.ivmiku.W4R3.entity.Base;
import com.ivmiku.W4R3.entity.User;
import com.ivmiku.W4R3.entity.UserInfo;
import com.ivmiku.W4R3.utils.PasswordEncrypt;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public Object getUser(Long id){
        User user = userMapper.selectById(id);
        UserInfo info = new UserInfo();
        info.getInfo(user);
        Base base = new Base(10000, "success");
        JSONObject json = new JSONObject();
        json.put("base", base);
        json.put("data", info);
        return JSON.toJSON(json);
    }

    public User getUserByName(String username) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("username", username);
        List<User> l = userMapper.selectByMap(param);
        return l.get(0);
    }
    public Object login(String username, String password){
        Base base = new Base();
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        List<User> userList = userMapper.selectByMap(params);
        if (userList.isEmpty()) {
            base.setCode(-1);
            base.setMsg("用户不存在");
            return JSON.toJSON(base);
        }
        User user = userList.get(0);
        if (PasswordEncrypt.encrypt(password, user.getSalt()).equals(user.getPassword())) {
            base.setCode(10000);
            base.setMsg("success");
            JSONObject json = new JSONObject();
            UserInfo userinfo = new UserInfo();
            userinfo.getInfo(user);
            JwtUser jwtUser=new JwtUser(username, Arrays.asList("user"));
            json.put("base", base);
            json.put("token", KrestUtil.createJwtTokenByUser(jwtUser));
            json.put("data", userinfo);
            return JSON.toJSON(json);
        } else {
            base.setCode(-1);
            base.setMsg("密码错误");
            return JSON.toJSON(base);
        }
    }

    public Object register(String username, String password) {
        Base base = new Base();
        User user = new User();
        user.setUsername(username);
        user.setSalt(PasswordEncrypt.getSalt(10));
        user.setPassword(PasswordEncrypt.encrypt(password, user.getSalt()));
        try {
            userMapper.insert(user);
        }
        catch (Exception e){
            base.setCode(-1);
            base.setMsg("用户名重复");
            e.printStackTrace();
            return JSON.toJSON(base);
        }
        base.setCode(10000);
        base.setMsg("success");
        return JSON.toJSON(base);
    }

    public Object setAvatarUrl(String url, String username) {
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.eq("username", username);
        User user = new User();
        user.setAvatarUrl(url);
        userMapper.update(user ,userUpdateWrapper);
        Base base = new Base();
        base.setCode(10000);
        base.setMsg("success");
        return JSON.toJSON(base);
    }
    @SneakyThrows
    public Map<String, Collection<String>> getRolePermissionMap() {

        Map<String, Collection<String>> res=new HashMap<String, Collection<String>>();
        res.put("user", Arrays.asList("p1","p2","p3"));
        res.put("admin", Arrays.asList("p1","p2","p3","p4","p5","user:delete","pd"));
        return res;
    }
}
