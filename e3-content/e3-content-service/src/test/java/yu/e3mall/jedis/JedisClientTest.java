package yu.e3mall.jedis;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.JedisCluster;
import yu.e3mall.common.jedis.JedisClient;
import yu.e3mall.common.jedis.JedisClientCluster;
import yu.e3mall.common.jedis.JedisClientPool;

public class JedisClientTest {
	/*@Test
	public void testJedisClient() throws Exception {
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
		JedisClient jedisClientPool = ac.getBean(JedisClientPool.class);
		System.out.println(jedisClientPool.get("test"));
	}
	
	@Test
	public void testJedisClient2() throws Exception {
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
		JedisClient jedisClientPool = ac.getBean(JedisClientCluster.class);
		System.out.println(jedisClientPool.get("mytest"));
	}*/
}
