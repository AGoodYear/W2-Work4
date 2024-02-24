package com.ivmiku.W4R3.utils;

import com.ivmiku.W4R3.pojo.Video;
import com.ivmiku.W4R3.service.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class ListenHandler {
    @Autowired
    private VideoService videoService;

    @Autowired
    private RedisUtil redisUtil;

    public ListenHandler() {
        log.info("Redis初始化…");
    }

    @PostConstruct
    public void init() {
        List<Video> videoList = videoService.getAll();
        videoList.forEach(Video -> {
            HashMap<String, Object> h = new HashMap<>();
            h.put("visit_count", Video.getVisitCount());
            redisUtil.zsAdd("visit_count", Video.getId(), h);
        });
        log.info("写入Redis成功");
    }

    public void writeNum(Set<DefaultTypedTuple> set, String fieldName) {
        set.forEach(item->{
            DefaultTypedTuple ii= (DefaultTypedTuple)item;
            Long id = Long.valueOf((String)ii.getValue());
            Integer num = ii.getScore().intValue();

            Video video = videoService.selectById(id.toString());
            if (fieldName.equals("visit_count")){
                video.setVisitCount(num);
            }

            videoService.updateInfo(video);
        });
    }
    @PreDestroy
    public void afterDestroy() {
        log.info("Redis持久化中…");
        Set<DefaultTypedTuple> visit_count = redisUtil.zsReverseRangeWithScores("visit_count");
        this.writeNum(visit_count, "visit_count");
        log.info("Redis持久化完成");
    }
}
