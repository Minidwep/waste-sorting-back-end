package com.minidwep.wasteSorting.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:zhibo.lv@wolaidai.com">zhibo.lv</a>
 */
@Slf4j
@Configuration
@Component
@PropertySource("classpath:redis-cluster.conf")
public class RedisAutoConfiguration  {

    @Value("${redis.node1.ip}")
    private String node1Ip;
    @Value("${redis.node1.port}")
    private int node1Port;
    @Value("${redis.node2.ip}")
    private String node2Ip;
    @Value("${redis.node2.port}")
    private int node2Port;
    @Value("${redis.node3.ip}")
    private String node3Ip;
    @Value("${redis.node3.port}")
    private int node3Port;
    @Value("${redis.node4.ip}")
    private String node4Ip;
    @Value("${redis.node4.port}")
    private int node4Port;
    @Value("${redis.node5.ip}")
    private String node5Ip;
    @Value("${redis.node5.port}")
    private int node5Port;
    @Value("${redis.node6.ip}")
    private String node6Ip;
    @Value("${redis.node6.port}")
    private int node6Port;
    @Value("${redis.node.password:null}")
    private String password;
    @Value("${redis.connection_timeout:2000}")
    private int connectionTimeout;
    @Value("${redis.so_timeout:2000}")
    private int soTimeout;
    @Value("${redis.max_attempts:10}")
    private int maxAttempts;
    @Value("${redis.pool.maxTotal:800}")
    private int maxTotal;
    @Value("${redis.pool.minIdle:50}")
    private int minIdle;
    @Value("${redis.pool.maxIdle:200}")
    private int maxIdle;
    @Value("${redis.pool.maxWait:3000}")
    private int maxWaitMillis;


    @Bean
    public JedisCluster jedisCluster() {
        HostAndPort hostAndPort1 = new HostAndPort(node1Ip, node1Port);
        HostAndPort hostAndPort2 = new HostAndPort(node2Ip, node2Port);
        HostAndPort hostAndPort3 = new HostAndPort(node3Ip, node3Port);
        HostAndPort hostAndPort4 = new HostAndPort(node4Ip, node4Port);
        HostAndPort hostAndPort5 = new HostAndPort(node5Ip, node5Port);
        HostAndPort hostAndPort6 = new HostAndPort(node6Ip, node6Port);
        Set<HostAndPort> nodes = new HashSet<HostAndPort>();
        nodes.add(hostAndPort1);
        nodes.add(hostAndPort2);
        nodes.add(hostAndPort3);
        nodes.add(hostAndPort4);
        nodes.add(hostAndPort5);
        nodes.add(hostAndPort6);
        GenericObjectPoolConfig pool = new GenericObjectPoolConfig();
        pool.setMaxTotal(maxTotal);
        pool.setMinIdle(minIdle);
        pool.setMaxIdle(maxIdle);
        pool.setMaxWaitMillis(maxWaitMillis);
        JedisCluster jedisCluster = new JedisCluster(nodes, connectionTimeout, soTimeout, maxAttempts,password, pool);
        return jedisCluster;
    }
}