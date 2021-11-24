package br.com.adrianorodrigues.stocks.configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.TestConfiguration;

import org.springframework.context.annotation.Bean;
import redis.clients.jedis.Jedis;
import redis.embedded.RedisServer;

import java.io.IOException;
import java.net.URISyntaxException;

@TestConfiguration
public class TestRedisConfiguration {

    private RedisServer redisServer;
    private int retry = 0;
    private String url;

    public TestRedisConfiguration(
            @Value("${spring.redis.url}") String url) throws IOException, URISyntaxException {
        var port = url.replaceAll("redis://", "").split(":")[1];
        this.redisServer = new RedisServer(Integer.parseInt(port));
        this.url = url;
    }

    @Bean
    Jedis jedisClient(){
        return new Jedis(url);
    }

    @PostConstruct
    public void postConstruct() {
        try {
            redisServer.start();
        }catch (Exception e){
            if(retry < 5) {
                retry++;
                postConstruct();
            }
        }
    }

    @PreDestroy
    public void preDestroy() throws InterruptedException {
        retry = 0;
        redisServer.stop();
        while (redisServer.isActive());
    }
}