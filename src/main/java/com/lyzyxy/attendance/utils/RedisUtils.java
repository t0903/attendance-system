package com.lyzyxy.attendance.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {
    @Autowired
    protected StringRedisTemplate stringRedisTemplate;

    private static final Logger logger = LoggerFactory.getLogger(RedisUtils.class);
    /**
     * 写入redis缓存（不设置expire存活时间）
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, String value){
        boolean result = false;
        ValueOperations operations = null;
        try {
            operations = stringRedisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            logger.error("写入redis缓存失败！错误信息为：" + e.getMessage());
        }
        return result;
    }

    /**
     * 写入redis缓存（设置expire存活时间）
     * @param key
     * @param value
     * @param expire
     * @return
     */
    public boolean set(final String key, String value, Long expire){
        boolean result = false;
        ValueOperations operations = null;
        try {
            operations = stringRedisTemplate.opsForValue();
            operations.set(key, value);
            stringRedisTemplate.expire(key, expire, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            logger.error("写入redis缓存（设置expire存活时间）失败！错误信息为：" + e.getMessage());
        }
        return result;
    }

    public boolean expire(final String key,Long expire){
        return stringRedisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    /**
     * 读取redis缓存
     * @param key
     * @return
     */
    public String get(final String key){
        String result = null;
        try {
            ValueOperations<String,String> operations = stringRedisTemplate.opsForValue();
            result = operations.get(key);
        } catch (Exception e) {
            logger.error("读取redis缓存失败！错误信息为：" + e.getMessage());
        }
        return result;
    }

    /**
     * 判断redis缓存中是否有对应的key
     * @param key
     * @return
     */
    public boolean exists(final String key){
        boolean result = false;
        try {
            result = stringRedisTemplate.hasKey(key);
        } catch (Exception e) {
            logger.error("判断redis缓存中是否有对应的key失败！错误信息为：" + e.getMessage());
        }
        return result;
    }

    /**
     * redis根据key删除对应的value
     * @param key
     * @return
     */
    public boolean remove(final String key){
        boolean result = false;
        try {
            if(exists(key)){
                stringRedisTemplate.delete(key);
            }
            result = true;
        } catch (Exception e) {
            logger.error("redis根据key删除对应的value失败！错误信息为：" + e.getMessage());
        }
        return result;
    }

    /**
     * redis根据keys批量删除对应的value
     * @param keys
     * @return
     */
    public void remove(final String... keys){
        for(String key : keys){
            remove(key);
        }
    }
}
