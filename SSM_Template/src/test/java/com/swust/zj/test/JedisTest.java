package com.swust.zj.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisTest {
	private ApplicationContext ac;
	@Before
	public void before() {
		ac = new ClassPathXmlApplicationContext("spring-redis.xml");
	}
	@Test
	public void test() {
		Jedis jedis = ac.getBean(JedisPool.class).getResource();
		System.out.println(jedis.set("name","周杰"));
		System.out.println(jedis.get("name"));
		System.out.println(jedis.expire("name", 5));
		System.out.println(jedis.ttl("name"));
		System.out.println(jedis.del("name"));
		System.out.println(jedis.get("name"));
		jedis.close();
	}
	@After
	public void after() {
	}
}
