package com.example.demo;

import com.example.demo.util.JwtUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testJWT() {
        // 将签发的JWT存储到Redis： {JWT-SESSION-{appID} , jwt}
        //redisTemplate.opsForValue().set("JWT-SESSION-" + appId, jwt, refreshPeriodTime, TimeUnit.SECONDS);

//        String refreshJwt = redisTemplate.opsForValue().get("JWT-SESSION-"+appId);
    }

    @Test
    public void testKey() {
        String bmcp = stringRedisTemplate.opsForValue().get("bmcp");
        Set<String> keys = stringRedisTemplate.keys("*");
    }

    @Test
    public void testJwt() {
        String token = JwtUtil.issueJWT();
        Map map = JwtUtil.parserJWT(token);
    }

}
