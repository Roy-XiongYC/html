package com.person.framework.redis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.alibaba.fastjson.JSON;

public class JedisPools
{

	private static final Logger logger = LoggerFactory.getLogger(JedisPools.class);

	private static Map<String, JedisPool> mapPool = new HashMap<String, JedisPool>();

	// private static JedisPool pool;

	private String serviceName;

	// linux 服务器的url --- redis
	private String serviceUrl; // 本地

	private String redisPort;

	// 设置最大的连接数
	private String maxTotal;

	// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
	private String maxIdle;

	// 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
	private String maxWaitMillis;

	// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
	private String testOnBorrow;

	// 在还会给pool时，是否提前进行validate操作
	private String testOnReturn;
	// 超时时间
	private String timeout;
	// 访问密码
	private String auth;

	// 静态代码初始化池配置
	public void init()
	{

		logger.info("************************************************");
		logger.info("***** Load redis pool configuration start. *****");

		String[] SERVICE_NAMES = serviceName.split(",");
		String[] SERVICE_URLS = serviceUrl.split(",");
		String[] REDIS_PORTS = redisPort.split(",");
		String[] MAX_IDLES = maxIdle.split(",");
		String[] MAX_TOTALS = maxTotal.split(",");
		String[] MAX_WAIT_MILLISS = maxWaitMillis.split(",");
		String[] TEST_ON_BORROWS = testOnBorrow.split(",");
		String[] TEST_ON_RETURNS = testOnReturn.split(",");
		String[] TIMEOUTS = timeout.split(",");
		String[] AUTHS = auth.split(",");

		for (int i = 0; i < SERVICE_NAMES.length; i++)
		{

			logger.info("***** Load redis  " + (i + 1) + "/" + SERVICE_NAMES.length + "  *****");
			logger.info("***** " + SERVICE_NAMES[i] + " maxIdle:" + MAX_IDLES[i] + " maxTotal:" + MAX_TOTALS[i] + " maxWaitMillis:" + MAX_WAIT_MILLISS[i] + " testOnBorrow:" + TEST_ON_BORROWS[i] + " testOnReturn:" + TEST_ON_RETURNS[i]);

			// 创建jedis池配置实例
			JedisPoolConfig config = new JedisPoolConfig();

			// 设置池配置项值
			// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
			config.setMaxIdle(Integer.parseInt(MAX_IDLES[i]));

			// 设置最大连接数
			config.setMaxTotal(Integer.parseInt(MAX_TOTALS[i]));

			// 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
			config.setMaxWaitMillis(Integer.parseInt(MAX_WAIT_MILLISS[i]));

			// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
			config.setTestOnBorrow(Boolean.getBoolean(TEST_ON_BORROWS[i]));

			// 在还会给pool时，是否提前进行validate操作
			config.setTestOnReturn(Boolean.getBoolean(TEST_ON_RETURNS[i]));

			// 根据配置实例化jedis池
//			JedisPool pool = new JedisPool(config, SERVICE_URLS[i], Integer.parseInt(REDIS_PORTS[i]),Integer.parseInt(TIMEOUTS[i]),AUTHS[i]);
			JedisPool pool = new JedisPool(config, SERVICE_URLS[i], Integer.parseInt(REDIS_PORTS[i]),Integer.parseInt(TIMEOUTS[i]));

			// 根据 SERVICE_NAME 把连接池存放到 mapPool 中
			mapPool.put(SERVICE_NAMES[i], pool);
		}

		logger.info("***** Load redis pool configuration end.   *****");

	}

	/** 获得jedis对象 */
	public static Jedis getJedisObject(String serverName)
	{
		return mapPool.get(serverName).getResource();
	}

	/** 归还jedis对象 */
	@SuppressWarnings("deprecation")
	public static void recycleJedisOjbect(String serverName, Jedis jedis)
	{
		try
		{
			mapPool.get(serverName).returnResource(jedis);
		}
		catch (Exception e)
		{
			logger.warn("redis is setJedis val Error! method[setJedis(String serverName=" + serverName + ", String jedis=" + jedis + ")]");
			logger.error("redis is setJedis val Error! method[setJedis(String serverName=" + serverName + ", String jedis=" + jedis + ")]");
		}
	}

	public void setJedis(String serverName, String key, String value)
	{
		Jedis jedis = getJedisObject(serverName);// 获得jedis实例
		try
		{
			jedis.set(key, value);
		}
		catch (Exception e)
		{
			logger.warn("redis is setJedis val Error! method[setJedis(String serverName=" + serverName + ", String key=" + key + ", String value=" + value + ")]");
			logger.error("redis is setJedis val Error! method[setJedis(String serverName=" + serverName + ", String key=" + key + ", String value=" + value + ")]");
		}
		finally
		{
			recycleJedisOjbect(serverName, jedis); // 将 获取的jedis实例对象还回迟中
		}
	}

	public void setJedisList(String serverName, String key, List<String> value)
	{
		Jedis jedis = getJedisObject(serverName);// 获得jedis实例
		try
		{
			for (String val : value)
			{
				jedis.lpush(key, val);
			}
		}
		catch (Exception e)
		{
			logger.warn("redis is setJedisList val Error! method[setJedisList(String serverName=" + serverName + ", String key=" + key + ", List<String> value=" + value.toString() + ")]");
			logger.error("redis is setJedisList val Error! method[setJedisList(String serverName=" + serverName + ", String key=" + key + ", List<String> value=" + value.toString() + ")]");
		}
		finally
		{
			recycleJedisOjbect(serverName, jedis); // 将 获取的jedis实例对象还回迟中
		}
	}

	public void setJedisMap(String serverName, String key, Map<String, String> map)
	{
		Jedis jedis = getJedisObject(serverName);// 获得jedis实例;
		try
		{
			jedis.hmset(key, map);
		}
		catch (Exception e)
		{
			logger.warn("redis is setJedisList val Error! method[setJedisMap(String serverName=" + serverName + ", String key=" + key + ", Map<String, String> map=" + map.toString() + ")]");
			logger.error("redis is setJedisList val Error! method[setJedisMap(String serverName=" + serverName + ", String key=" + key + ", Map<String, String> map=" + map.toString() + ")]");
		}
		finally
		{
			recycleJedisOjbect(serverName, jedis); // 将 获取的jedis实例对象还回迟中
		}
	}

	public void setJedisObject(String serverName, String key, Object obj)
	{
		Jedis jedis = getJedisObject(serverName);// 获得jedis实例
		try
		{
			String objectJson = JSON.toJSONString(obj);
			jedis.set(key, objectJson);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}finally{
			recycleJedisOjbect(serverName, jedis); // 将 获取的jedis实例对象还回迟中
		}
	}

	public String getJedis(String serverName, String key)
	{
		String val = "";
		Jedis jedis = getJedisObject(serverName);// 获得jedis实例
		try
		{
			val = jedis.get(key);
			logger.debug("(String val="+val+" String serverName=" + serverName + ", String key=" + key + ")");
		}
		catch (Exception e)
		{
			logger.warn("redis is getJedis val Error! method[getJedis(String serverName=" + serverName + ", String key=" + key + ")]");
			logger.error("redis is getJedis val Error! method[getJedis(String serverName=" + serverName + ", String key=" + key + ")]");
		}
		finally
		{
			recycleJedisOjbect(serverName, jedis); // 将 获取的jedis实例对象还回迟中
		}
		return val;
	}

	/**
	 * 获取redis中存放的
	 * 
	 * @param mapName
	 * @return
	 */
	public Map<String, String> getJedisMap(String serverName, String mapName)
	{
		Jedis jedis = getJedisObject(serverName);// 获得jedis实例
		Set<String> listMapKey = jedis.hkeys(mapName);
		Map<String, String> map = new HashMap<String, String>();
		for (String key : listMapKey)
		{
			List<String> value = jedis.hmget(mapName, key);
			map.put(key, value.get(0));
		}
		recycleJedisOjbect(serverName, jedis); // 将 获取的jedis实例对象还回迟中
		return map;
	}

	public List<String> getJedisList(String serverName, String key)
	{
		Jedis jedis = getJedisObject(serverName);// 获得jedis实例
		List<String> list = jedis.lrange(key, 0, -1);
		recycleJedisOjbect(serverName, jedis); // 将 获取的jedis实例对象还回迟中
		return list;
	}

	public void delJedis(String serverName, String key)
	{
		Jedis jedis = getJedisObject(serverName);// 获得jedis实例
		jedis.del(key);
		recycleJedisOjbect(serverName, jedis); // 将 获取的jedis实例对象还回迟中
	}

	/**
	 * 删除所有数据库中的所有key，此方法不会失败。更加慎用
	 * 
	 * @param key
	 * @param value
	 */
	public void delDB(String serverName)
	{
		Jedis jedis = getJedisObject(serverName);// 获得jedis实例
		jedis.flushDB();// 删除所有数据库中的所有key，此方法不会失败。更加慎用
	}

	/**
	 * 删除所有数据库中的所有key，此方法不会失败。更加慎用
	 * 
	 * @param key
	 * @param value
	 */
	public void delALL(String serverName)
	{
		Jedis jedis = getJedisObject(serverName);// 获得jedis实例
		jedis.flushAll();// 删除所有数据库中的所有key，此方法不会失败。更加慎用
	}
	
	public Long getSeq(String serverName,String seqName){
		Jedis jedis = getJedisObject(serverName);// 获得jedis实例
		Long seq=null;
		try
		{
			seq = jedis.incr(seqName);
			if(seq>999999){
				jedis.set(seqName, "0");
			}
		}catch(Exception e){
			logger.error("redis is incr val Error!seqName:"+seqName);
		}finally{
			recycleJedisOjbect(serverName, jedis); 
		}
		return seq;
	}

	/**
	 * 
	 * 测试jedis池方法
	 */

	public static void main(String[] args)
	{

//		String serverName = "";
//
//		Jedis jedis = getJedisObject(serverName);// 获得jedis实例

	}

	/**
	 * set方法
	 * 
	 * @param uRL
	 */
	public static Map<String, JedisPool> getMapPool()
	{
		return mapPool;
	}

	public static void setMapPool(Map<String, JedisPool> mapPool)
	{
		JedisPools.mapPool = mapPool;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	public String getRedisPort() {
		return redisPort;
	}

	public void setRedisPort(String redisPort) {
		this.redisPort = redisPort;
	}

	public String getMaxTotal() {
		return maxTotal;
	}

	public void setMaxTotal(String maxTotal) {
		this.maxTotal = maxTotal;
	}

	public String getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(String maxIdle) {
		this.maxIdle = maxIdle;
	}

	public String getMaxWaitMillis() {
		return maxWaitMillis;
	}

	public void setMaxWaitMillis(String maxWaitMillis) {
		this.maxWaitMillis = maxWaitMillis;
	}

	public String getTestOnBorrow() {
		return testOnBorrow;
	}

	public void setTestOnBorrow(String testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	public String getTestOnReturn() {
		return testOnReturn;
	}

	public void setTestOnReturn(String testOnReturn) {
		this.testOnReturn = testOnReturn;
	}

	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

}
