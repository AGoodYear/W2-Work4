package com.ivmiku.W4R3.config;

import com.auth0.jwt.algorithms.Algorithm;
import com.chenkaiwei.krest.config.KrestConfigurer;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KRestConfig implements KrestConfigurer {
    //1.按下图方式配置角色-权限映射，返回值中key为角色（Role）名称，value为该角色所拥有的所有权限（Permission）。
    //此处的硬编码仅作示例，您可以将这部分数据以任何您喜欢的方式存储（数据库、钥匙串、文本文件等，均可），只需在本方法中以同步方法取出并确保其按标准格式返回即可。
    @Override
    public Map<String, Collection<String>> configRolePermissionsMap() {
        Map<String, Collection<String>> res=new HashMap<String, Collection<String>>();
        res.put("admin", Arrays.asList("p1","p2","p3","p4"));
        res.put("user", Arrays.asList("p3","p4"));
        return res;
    }

    //2.配置jwt Token的加密策略，字符串部分为秘钥
    //同上，该加密策略中的加密方式和秘钥，也可自由实现存取方式，只需返回格式符合要求即可。
    @Override
    public Algorithm configJwtAlgorithm() {
        return Algorithm.HMAC256("ouneideshou");
    }

    //3.（可选）配置忽略jwt验证的路径规则，默认配置如下四条。本方法中的语法来自shiro，如果您对路径映射规则有更多的需求，也可一并在本方法中配置。
    @Override
    public void configFilterChainDefinitionMap(Map<String, String> filterRuleMap) {
        //配置不参与token验证的uri
        filterRuleMap.put("/static/*", "anon");
        filterRuleMap.put("/error", "anon");
        filterRuleMap.put("/api/user/register", "anon");
        filterRuleMap.put("/api/user/login", "anon");
    }
}
