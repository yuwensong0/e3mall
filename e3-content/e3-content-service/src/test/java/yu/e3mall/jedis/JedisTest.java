package yu.e3mall.jedis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.sun.tools.internal.xjc.model.SymbolSpace;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class JedisTest {
/*	@Test
	public void testJedis() throws Exception {
		Jedis jedis = new Jedis("192.168.25.129", 6379);
		jedis.set("test", "my first jedisTest");
		System.out.println(jedis.get("test"));
		jedis.close();
	}
	@Test
	public void testJedisPool() throws Exception {
		JedisPool jedisPool = new JedisPool("192.168.25.129", 6379);
		Jedis jedis = jedisPool.getResource();
		System.out.println(jedis.get("test"));
		jedis.close();
		jedisPool.close();
	}
	@Test
	public void testJedisCluster() throws Exception {
		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.25.129", 7001));
		nodes.add(new HostAndPort("192.168.25.129", 7002));
		nodes.add(new HostAndPort("192.168.25.129", 7003));
		nodes.add(new HostAndPort("192.168.25.129", 7004));
		nodes.add(new HostAndPort("192.168.25.129", 7005));
		nodes.add(new HostAndPort("192.168.25.129", 7006));
		JedisCluster jedisCluster = new JedisCluster(nodes);
		jedisCluster.set("mytest", "jedis cluster test");
		System.out.println(jedisCluster.get("mytest"));
		jedisCluster.close();
	}*/
}
