package com.cloud.yun.utils;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName RedisUtils
 * @Description RedisUtils is to handle xxxx    redis的工具类
 * @Author jack
 * @Date 7/11/2022 4:16 PM
 * @Version 1.0
 **/
@Lazy
@Component
@Slf4j
@NoArgsConstructor
public class RedisUtils {
	
	@Resource
	@Qualifier("redisTemplate")
	private RedisTemplate<Object,Object> redisTemplate;
	
	public boolean set(String key, Object value){
		boolean result = false;

		try {
			ValueOperations<Object, Object> operations = this.redisTemplate.opsForValue();
			operations.set(key, value);
			result = true;
		} catch (Exception var5) {
			log.error("设置redis缓存失败！", var5);
		}

		return result;
	}

	public boolean set(String key, int index, Object value) {
		boolean result = false;

		try {
			this.redisTemplate.opsForList().set(key, (long)index, value);
			result = true;
		} catch (Exception var6) {
			log.error("设置redis缓存失败！", var6);
		}

		return result;
	}

	public Object get(String key) {
		Object result = null;

		try {
			ValueOperations<Object, Object> operations = this.redisTemplate.opsForValue();
			result = operations.get(key);
		} catch (Exception var4) {
			log.error("从redis缓存获取String失败！", var4);
		}

		return result;
	}

	public <T> T get(String key, Class<T> tClass) {
		try {
			ValueOperations<Object, Object> operations = this.redisTemplate.opsForValue();
			return (T) operations.get(key);
		} catch (Exception var4) {
			log.error("从redis缓存获取String失败！", var4);
			return null;
		}
	}

	public void set(String key, Object value, Long expireTime, TimeUnit timeUnit) {
		ValueOperations<Object, Object> operations = this.redisTemplate.opsForValue();
		operations.set(key, value);
		this.redisTemplate.expire(key, expireTime, timeUnit);
	}

	public void resetExpireTime(String key, Long expireTime, TimeUnit timeUnit) {
		this.redisTemplate.expire(key, expireTime, timeUnit);
	}

	public void remove(String... keys) {
		String[] var2 = keys;
		int var3 = keys.length;

		for(int var4 = 0; var4 < var3; ++var4) {
			String key = var2[var4];
			this.remove(key);
		}

	}

	public void removeKeys(String keyPattern) {
		Set<Object> keys = this.redisTemplate.keys(keyPattern);
		if (keys.size() > 0) {
			this.redisTemplate.delete(keys);
		}

	}

	public void remove(String key) {
		if (this.exists(key)) {
			this.redisTemplate.delete(key);
		}

	}

	public boolean exists(String key) {
		return this.redisTemplate.hasKey(key);
	}

	public void hmSet(String key, Object hashKey, Object value) {
		HashOperations<Object, Object, Object> hash = this.redisTemplate.opsForHash();
		hash.put(key, hashKey, value);
	}

	public void hmSet(String key, Object hashKey, Object value,Long expireTime) {
		this.hmSet(key,hashKey,value,expireTime,TimeUnit.SECONDS);
	}

	public void hmSet(String key, Object hashKey, Object value,Long expireTime, TimeUnit timeUnit) {
		BoundHashOperations<Object, Object, Object> hash = redisTemplate.boundHashOps(key);
		hash.put(hashKey,value);
		hash.expire(expireTime,timeUnit);
	}

	public Object hmGet(String key, Object hashKey) {
		HashOperations<Object, Object, Object> hash = this.redisTemplate.opsForHash();
		return hash.get(key, hashKey);
	}

	public void lPush(String key, Object value) {
		ListOperations<Object, Object> list = this.redisTemplate.opsForList();
		list.rightPush(key, value);
	}

	public void lPush(String key, Object value, Long expireTime, TimeUnit timeUnit) {
		ListOperations<Object, Object> list = this.redisTemplate.opsForList();
		list.rightPush(key, value);
		this.redisTemplate.expire(key, expireTime, timeUnit);
	}

	public List getList(String key) {
		ListOperations<Object, Object> oper = this.redisTemplate.opsForList();
		Long size = oper.size(key);
		List list = oper.range(key, 0L, size - 1L);
		return list;
	}

	public void removeListObject(String key, Object obj) {
		ListOperations<Object, Object> oper = this.redisTemplate.opsForList();
		oper.remove(key, 0L, obj);
	}

	public List lrange(String key, int start, int end) {
		ListOperations<Object, Object> oper = this.redisTemplate.opsForList();
		List<Object> list = oper.range(key, (long)start, (long)end);
		return list;
	}

	public void add(String key, Object value) {
		SetOperations<Object, Object> set = this.redisTemplate.opsForSet();
		set.add(key, new Object[]{value});
	}

	public Set<Object> setMembers(String key) {
		SetOperations<Object, Object> set = this.redisTemplate.opsForSet();
		return set.members(key);
	}

	public void zAdd(String key, Object value, double scoure) {
		ZSetOperations<Object, Object> zset = this.redisTemplate.opsForZSet();
		zset.add(key, value, scoure);
	}

	public Set<Object> rangeByScore(String key, double scoure, double scoure1) {
		ZSetOperations<Object, Object> zset = this.redisTemplate.opsForZSet();
		return zset.rangeByScore(key, scoure, scoure1);
	}

	public Set<Object> keys(String pattern) {
		return this.redisTemplate.keys(pattern);
	}

	public Long incr(String key) {
		Long result = 0L;

		try {
			ValueOperations<Object, Object> operations = this.redisTemplate.opsForValue();
			result = operations.increment(key);
		} catch (Exception var4) {
			log.error("redis 自增失败，key +" + key + "！", var4);
		}

		return result;
	}

	public Long incr(String key, long number) {
		Long result = 0L;

		try {
			ValueOperations<Object, Object> operations = this.redisTemplate.opsForValue();
			result = operations.increment(key, number);
		} catch (Exception var6) {
			log.error("redis 自增失败，key +" + key + "！", var6);
		}

		return result;
	}

	public Long decr(String key) {
		Long result = 0L;

		try {
			ValueOperations<Object, Object> operations = this.redisTemplate.opsForValue();
			result = operations.decrement(key);
		} catch (Exception var4) {
			log.error("redis 自减失败，key +" + key + "！", var4);
		}

		return result;
	}

	public Long decr(String key, long number) {
		Long result = 0L;

		try {
			ValueOperations<Object, Object> operations = this.redisTemplate.opsForValue();
			result = operations.decrement(key, number);
		} catch (Exception var6) {
			log.error("redis 自减失败，key +" + key + "！", var6);
		}

		return result;
	}

	public void convertAndSend(String channel, Object message) {
		this.redisTemplate.convertAndSend(channel, message);
	}
}
