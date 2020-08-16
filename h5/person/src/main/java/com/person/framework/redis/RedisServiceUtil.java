package com.person.framework.redis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RedisServiceUtil
{

	/**
	 * 配置的服务器名称或昵称,传参时区分在哪个服务器。 对应[redis.properties]文件中 REDIS.SERVICE_NAME属性
	 * REDIS_COMMON = 6379端口 通用性缓存 —— 
	 */
	public static final String SERVICE_REDIS_COMMON = "REDIS_COMMON";

	static JedisPools jedisPool = new JedisPools();

	/**
	 * jedis
	 * 
	 * @param serviceName 写入的服务器名
	 * @param key
	 * @param value
	 */
	public static void setJedis(String serviceName, String key, String value)
	{
		jedisPool.setJedis(serviceName, key, value);
	}

	public static String getJedis(String serviceName, String key)
	{
		return jedisPool.getJedis(serviceName, key);
	}

	public static void delJedis(String serviceName, String key)
	{
		jedisPool.delJedis(serviceName, key);
	}

	/**
	 * 此方法更加不会失败,更加慎用
	 */
	public static void delALL(String serviceName)
	{
		jedisPool.delALL(serviceName);
	}

	/**
	 * 此方法不会失败,慎用
	 */
	public static void delDB(String serviceName)
	{
		jedisPool.delDB(serviceName);
	}

	public static void setJedisList(String serviceName, String key, List<String> value)
	{
		jedisPool.setJedisList(serviceName, key, value);
	}

	public static List<String> getJedisList(String serviceName, String key)
	{
		return jedisPool.getJedisList(serviceName, key);
	}

	/**
	 * 将Map<String,String> 类型的值,写入Redis key规则: '功能' + '_' + 'id'.
	 * 如:[登入:userLogin_0000000001],以避免key重复 Map 中需带 LoginDate
	 * 
	 * @param key
	 */
	public static void setJedisMap(String serviceName, String key, Map<String, String> map)
	{
		jedisPool.setJedisMap(serviceName, key, map);
	}

	/**
	 * 从Redis中取出Map.
	 * 
	 * @param key
	 * @return Map<String, String>
	 */
	public static Map<String, String> getJedisMap(String serviceName, String key)
	{
		return jedisPool.getJedisMap(serviceName, key);
	}

	/**
	 * 从Redis中取出Object
	 * 
	 * @param key
	 * @param obj
	 */
	public static void setJedisObject(String serviceName, String key, Object obj)
	{
		jedisPool.setJedisObject(serviceName, key, obj);
	}
	
	public static Long getSeq(String serverName,String seqName){
		
		return jedisPool.getSeq(serverName, seqName);
	}

	public static void main(String[] args)
	{

		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "fujianchao");
		map.put("password", "123");
		map.put("age", "12");

		jedisPool.setJedisMap(RedisServiceUtil.SERVICE_REDIS_COMMON, "myMap", map);

		Map<String, String> newMap = new HashMap<String, String>();

		newMap = jedisPool.getJedisMap(RedisServiceUtil.SERVICE_REDIS_COMMON, "myMap");

		System.out.println(newMap.size());

	}

}
