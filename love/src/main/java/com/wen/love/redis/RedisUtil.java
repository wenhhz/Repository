package com.wen.love.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisUtil {

    @Qualifier("redisTemplate")
    @Autowired
    private StringRedisTemplate redisTemplate;

    public void set(String key,String value){
        redisTemplate.opsForValue().set(key,value);
    }

    public String get(String key){
        return redisTemplate.opsForValue().get(key);
    }

}
