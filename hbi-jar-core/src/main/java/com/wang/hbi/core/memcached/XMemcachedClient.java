package com.wang.hbi.core.memcached;

import com.google.code.ssm.Cache;
import com.google.code.ssm.api.format.SerializationType;
import com.google.code.ssm.providers.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * Memcached客户端
 *
 * @author HeJiawang
 * @date 20171015
 */
@Component
public class XMemcachedClient {

	private static final Logger logger = LoggerFactory.getLogger(XMemcachedClient.class);

	private static Cache client;

	@Autowired(required = true)
	public void setClient(Cache client) {
		XMemcachedClient.client = client;
	}

	public final static int DEFAULT_TIME_OUT = 24 * 60 * 60; //1 day

	/**
	 * 仅当缓存中不存在该key时，add 命令会向缓存中添加一个键值对，
	 * 如果缓存中已经存在，则保持原来的值不变, 有效期为1天
	 * @param key
	 * @param value
	 */
	public static void add(final String key, final Object value) throws Exception {
		add(key, DEFAULT_TIME_OUT, value, SerializationType.PROVIDER);
	}

	public static void add(final String key, final Object value, SerializationType serializationType) throws Exception {
		add(key, DEFAULT_TIME_OUT, value, serializationType);
	}

	public static void add(final String key, final int exp, final Object value) throws Exception {
		add(key, exp, value, SerializationType.PROVIDER);
	}

	/**
	 * 仅当缓存中不存在该key时，add 命令会向缓存中添加一个键值对，
	 * 如果缓存中已经存在，则保持原来的值不变
	 * @param key
	 * @param exp
	 * @param value
	 * @param serializationType
	 */
	public static void add(String key, int exp, Object value, SerializationType serializationType) throws Exception {
		try {
			client.add(key, exp, value, serializationType);
		} catch (TimeoutException e) {
			logger.error("TimeoutException: " + e.getMessage());
			throw e;
		} catch (CacheException e) {
			logger.error("CacheException: " + e.getMessage());
			throw e;
		}
	}

	public static Object get(String key) throws Exception {
		return get(key, SerializationType.PROVIDER);
	}

	/**
	 * 根据key获取value
	 * @param key
	 * @return Object
	 */
	public static Object get(String key, SerializationType serializationType) throws Exception {
		try {
			return client.get(key, serializationType);
		} catch (TimeoutException e) {
			logger.error("TimeoutException: " + e.getMessage());
			throw e;
		} catch (CacheException e) {
			logger.error("CacheException: " + e.getMessage());
			throw e;
		}
	}

	public static Map<String, Object> get(Collection<String> keyCollection) throws Exception {
		return get(keyCollection, SerializationType.PROVIDER);
	}

	/**
	 * 获得集合
	 * @param keyCollection
	 * @return Map<String, Object>
	 */
	public static Map<String, Object> get(Collection<String> keyCollection, SerializationType serializationType) throws Exception {
		try {
			return client.getBulk(keyCollection, serializationType);
		} catch (TimeoutException e) {
			logger.error("TimeoutException: " + e.getMessage());
			throw e;
		} catch (CacheException e) {
			logger.error("CacheException: " + e.getMessage());
			throw e;
		}
	}

	/**
	 * 添加新的键值对， 如果key已经存在，原有值将被替换, 有效期为1天
	 * @param key
	 * @param value
	 */
	public static void set(final String key, final Object value) throws Exception {
		set(key, DEFAULT_TIME_OUT, value, SerializationType.PROVIDER);
	}

	public static void set(final String key, final int exp, final Object value) throws Exception {
		client.set(key, exp, value, SerializationType.PROVIDER);
	}

	public static void set(final String key, final Object value, SerializationType serializationType) throws Exception {
		client.set(key, DEFAULT_TIME_OUT, value, serializationType);
	}

	/**
	 * 添加新的键值对， 如果key已经存在，原有值将被替换
	 * @param key
	 * @param exp
	 * @param value
	 */
	public static void set(String key, int exp, Object value, SerializationType serializationType) throws Exception {
		try {
			client.set(key, exp, value, serializationType);
		} catch (TimeoutException e) {
			logger.error("TimeoutException: " + e.getMessage());
			throw e;
		} catch (CacheException e) {
			logger.error("CacheException: " + e.getMessage());
			throw e;
		}
	}

	/**
	 *
	 * 删除key
	 * @param key
	 * @return boolean
	 */
	public static boolean delete(String key) throws Exception {
		try {
			return client.delete(key);
		} catch (TimeoutException e) {
			logger.error("TimeoutException: " + e.getMessage());
			throw e;
		} catch (CacheException e) {
			logger.error("CacheException: " + e.getMessage());
			throw e;
		}
	}

	/**
	 * 删除keys
	 */
	public static void delete(Collection<String> keys) throws Exception {
		try {
			client.delete(keys);
		} catch (TimeoutException e) {
			logger.error("TimeoutException: " + e.getMessage());
			throw e;
		} catch (CacheException e) {
			logger.error("CacheException: " + e.getMessage());
			throw e;
		}
	}

	/**
	 * 计数器递增
	 * @param key 名称
	 * @param delta 递增幅度
	 * @param initValue the init value 初始值
	 * @return  long
	 */
	public static long incr(String key, int delta, long initValue) throws Exception {
		try {
			return client.incr(key, delta, initValue);
		} catch (TimeoutException e) {
			logger.error("TimeoutException: " + e.getMessage());
			throw e;
		} catch (CacheException e) {
			logger.error("CacheException: " + e.getMessage());
			throw e;
		}
	}

	/**
	 * Incr.
	 *
	 * @param key 名称
	 * @param delta 递增幅度
	 * @param initValue the init value 初始值
	 * @param exp 过期时间
	 * @return  long
	 */
	public static long incr(String key, int delta, long initValue, int exp) throws Exception {
		try {
			return client.incr(key, delta, initValue, exp);
		} catch (TimeoutException e) {
			logger.error("TimeoutException: " + e.getMessage());
			throw e;
		} catch (CacheException e) {
			logger.error("CacheException: " + e.getMessage());
			throw e;
		}
	}

	/**
	 *
	 * @param key 名称
	 * @param delta 递减幅度
	 * @return long
	 */
	public static long decr(String key, int delta) throws Exception {
		try {
			return client.decr(key, delta);
		} catch (TimeoutException e) {
			logger.error("TimeoutException: " + e.getMessage());
			throw e;
		} catch (CacheException e) {
			logger.error("CacheException: " + e.getMessage());
			throw e;
		}
	}

    /**
     * 新建计数器
     * @param key 名称
     * @return 返回计数器对象
     */
    public static Long getCounter(String key) throws Exception {
	    try {
		    return client.getCounter(key);
	    } catch (TimeoutException e) {
		    logger.error("TimeoutException: " + e.getMessage());
		    throw e;
	    } catch (CacheException e) {
		    logger.error("CacheException: " + e.getMessage());
		    throw e;
	    }
    }

	public static void setCounter(String cacheKey, int expiration, long value) throws TimeoutException, CacheException {
		try {
			client.setCounter(cacheKey, expiration, value);
		} catch (TimeoutException e) {
			logger.error("TimeoutException: " + e.getMessage());
			throw e;
		} catch (CacheException e) {
			logger.error("CacheException: " + e.getMessage());
			throw e;
		}
	}

	//    /**
	//     * 原子操作
	//     * @param key
	//     * @param exp
	//     * @param value
	//     * @param cas
	//     * @return
	//     */
	//    public static boolean cas(String key, int exp, Object value, long cas) throws Exception {
	//        try {
	//            return client.cas(key, exp, value, cas);
	//        } catch (TimeoutException e) {
	//            logger.error("TimeoutException: " + e.getMessage());
	//            throw e;
	//        } catch (InterruptedException e) {
	//            logger.error("InterruptedException: " + e.getMessage());
	//            throw e;
	//        } catch (MemcachedException e) {
	//            logger.error("MemcachedException: " + e.getMessage());
	//            throw e;
	//        }
	//    }

	//    /**
	//     * 原子操作
	//     * @param key
	//     * @param exp
	//     * @param value
	//     * @param tryNums
	//     * @return
	//     */
	//    public static boolean casWithCasOperation(String key, int exp, final int value,
	//                                              final int tryNums) throws Exception {
	//        try {
	//            return client.cas(key, exp, new CASOperation<Integer>() {
	//                public int getMaxTries() {
	//                    return tryNums;
	//                }
	//
	//                public Integer getNewValue(long currentCAS, Integer currentValue) {
	//                    return value;
	//                }
	//            });
	//        } catch (TimeoutException e) {
	//            logger.error("TimeoutException: " + e.getMessage());
	//            throw e;
	//        } catch (InterruptedException e) {
	//            logger.error("InterruptedException: " + e.getMessage());
	//            throw e;
	//        } catch (MemcachedException e) {
	//            logger.error("MemcachedException: " + e.getMessage());
	//            throw e;
	//        }
	//    }

	/**
	 * 删除所有保存的项目
	 * @throws Exception
	 */
	public static void flushAll() throws Exception {
		try {
			client.flush();
		} catch (TimeoutException e) {
			logger.error("TimeoutException: " + e.getMessage());
			throw e;
		} catch (CacheException e) {
			logger.error("CacheException: " + e.getMessage());
			throw e;
		}

	}

	/*
	 * 通过域名和AssignedKey名获取缓存KEY
	 */
	public static String getAssignedCacheKey(String namespace, String assignedKey) {
		return namespace + ":" + assignedKey;
	}
}