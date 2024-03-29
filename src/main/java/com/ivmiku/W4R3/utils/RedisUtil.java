package com.ivmiku.W4R3.utils;

import com.ivmiku.W4R3.entity.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @author Aurora
 */
@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * zset增加操作
     * @param key
     * @param value  属性值
     * @param map    具体分数
     * @return
     */
    public Boolean zsAdd(String key, String value, HashMap<String, Object> map){
        try {
//            redisTemplate.opsForZSet().add("viewNum", "h1", Double.valueOf(h1.get("viewNum").toString()));

            redisTemplate.opsForZSet().add(key, value, Double.valueOf(map.get(key).toString()));

            return true;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }

    }

    /**
     * zset给某个key某个属性增值操作
     * @param key
     * @param value  属性值
     * @param delta  增加值
     * @return
     */
    public Boolean zsIncr(String key, String value, Integer delta){
        try {
            redisTemplate.opsForZSet().incrementScore(key, value, delta);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * zset逆向排序
     * @param key
     * @return
     */
    public Set<Object> zsReverseRange(String key){
        Set viewNum = redisTemplate.opsForZSet().reverseRange(key,0,-1);

        return viewNum;

    }

    public Set zsReverseRangeWithScores(String key){
        return redisTemplate.opsForZSet().reverseRangeWithScores(key, 0, 20);
    }

    /**
     * zscore 返回属性值
     * @param key  key值
     * @param value 属性值
     * @return
     */
    public Double zscore(String key,String value){
        Double score = redisTemplate.opsForZSet().score(key, value);
        return score;
    }

    /**
     * List插入操作
     * @param key 键
     * @param content 内容
     */
    public void insertList(String key, String content) {
        redisTemplate.opsForList().leftPush(key, content);
    }

    /**
     * List返回操作
     * @param key 键
     * @param s 开始
     * @param e 结束
     * @return 查询的List列表
     */
    public List<String> getList(String key, int s, int e) {
        return redisTemplate.opsForList().range(key, s, e);
    }
}
