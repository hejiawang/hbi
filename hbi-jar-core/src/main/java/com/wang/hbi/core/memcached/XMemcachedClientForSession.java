package com.wang.hbi.core.memcached;

import net.rubyeye.xmemcached.CASOperation;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * 存储session信息的Memcached工具类
 *
 * @author HeJiawang
 * @date 20171015
 */
public class XMemcachedClientForSession {
	private static final Logger logger = LoggerFactory.getLogger(XMemcachedClientForSession.class);

	private static MemcachedClient client;

	public final static int DEFAULT_TIME_OUT = 24 * 60 * 60;                             //1 day

	public static MemcachedClient getClient() {
		return client;
	}

	public static void setClient(MemcachedClient client) {
		XMemcachedClientForSession.client = client;

	}

	/**
	 * 仅当缓存中不存在该key时，add 命令会向缓存中添加一个键值对，
	 * 如果缓存中已经存在，则保持原来的值不变
	 * @param key
	 * @param exp
	 * @param value
	 * @return
	 */
	public static boolean add(String key, int exp, Object value) throws Exception {
		try {
			return client.add(key, exp, value);
		} catch (TimeoutException e) {
			logger.error("TimeoutException: " + e.getMessage());
			throw e;
		} catch (InterruptedException e) {
			logger.error("InterruptedException: " + e.getMessage());
			throw e;
		} catch (MemcachedException e) {
			logger.error("MemcachedException: " + e.getMessage());
			throw e;
		}
	}

	/**
	 * 仅当缓存中不存在该key时，add 命令会向缓存中添加一个键值对，
	 * 如果缓存中已经存在，则保持原来的值不变, 有效期为1天
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean add(final String key, final Object value) throws Exception {
		return add(key, DEFAULT_TIME_OUT, value);
	}

	/**
	 * 根据key获取value
	 * @param key
	 * @return
	 */
	public static Object get(String key) throws Exception {

		try {
			return client.get(key);
		} catch (TimeoutException e) {
			logger.error("TimeoutException: " + e.getMessage());
			throw e;
		} catch (InterruptedException e) {
			logger.error("InterruptedException: " + e.getMessage());
			throw e;
		} catch (MemcachedException e) {
			logger.error("MemcachedException: " + e.getMessage());
			throw e;
		}
	}

	public static Map<String, Object> get(Collection<String> keyCollection) throws Exception {
		try {
			return client.get(keyCollection);
		} catch (TimeoutException e) {
			logger.error("TimeoutException: " + e.getMessage());
			throw e;
		} catch (InterruptedException e) {
			logger.error("InterruptedException: " + e.getMessage());
			throw e;
		} catch (MemcachedException e) {
			logger.error("MemcachedException: " + e.getMessage());
			throw e;
		}
	}

	/**
	 * 添加新的键值对， 如果key已经存在，原有值将被替换, 有效期为1天
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean set(final String key, final Object value) throws Exception {
		return set(key, DEFAULT_TIME_OUT, value);
	}

	/**
	 * 添加新的键值对， 如果key已经存在，原有值将被替换
	 * @param key
	 * @param exp
	 * @param value
	 * @return
	 */
	public static boolean set(String key, int exp, Object value) throws Exception {
		try {
			return client.set(key, exp, value);
		} catch (TimeoutException e) {
			logger.error("TimeoutException: " + e.getMessage());
			throw e;
		} catch (InterruptedException e) {
			logger.error("InterruptedException: " + e.getMessage());
			throw e;
		} catch (MemcachedException e) {
			logger.error("MemcachedException: " + e.getMessage());
			throw e;
		}
	}

	/**
	 * 替换缓存中已经存在的内容, 有效期为1天
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean replace(final String key, final Object value) throws Exception {
		return replace(key, DEFAULT_TIME_OUT, value);
	}

	/**
	 * 替换缓存中已经存在的内容
	 * @param key
	 * @param exp
	 * @param value
	 * @return
	 */
	public static boolean replace(String key, int exp, Object value) throws Exception {
		try {
			return client.replace(key, exp, value);
		} catch (TimeoutException e) {
			logger.error("TimeoutException: " + e.getMessage());
			throw e;
		} catch (InterruptedException e) {
			logger.error("InterruptedException: " + e.getMessage());
			throw e;
		} catch (MemcachedException e) {
			logger.error("MemcachedException: " + e.getMessage());
			throw e;
		}
	}

	/**
	 *
	 * 删除key
	 * @param key
	 * @return
	 */
	public static boolean delete(String key) throws Exception {
		try {
			return client.delete(key);
		} catch (TimeoutException e) {
			logger.error("TimeoutException: " + e.getMessage());
			throw e;
		} catch (InterruptedException e) {
			logger.error("InterruptedException: " + e.getMessage());
			throw e;
		} catch (MemcachedException e) {
			logger.error("MemcachedException: " + e.getMessage());
			throw e;
		}
	}

	/**
	 * 删除key
	 * @param key
	 * @param opTimeout 超时时间
	 * @return
	 */
	public static boolean delete(String key, long opTimeout) throws Exception {
		try {
			return client.delete(key, opTimeout);
		} catch (TimeoutException e) {
			logger.error("TimeoutException: " + e.getMessage());
			throw e;
		} catch (InterruptedException e) {
			logger.error("InterruptedException: " + e.getMessage());
			throw e;
		} catch (MemcachedException e) {
			logger.error("MemcachedException: " + e.getMessage());
			throw e;
		}
	}

	/**
	 * 在已经存在的item后增加item
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean append(String key, Object value) throws Exception {
		try {
			return client.append(key, value);
		} catch (TimeoutException e) {
			logger.error("TimeoutException: " + e.getMessage());
			throw e;
		} catch (InterruptedException e) {
			logger.error("InterruptedException: " + e.getMessage());
			throw e;
		} catch (MemcachedException e) {
			logger.error("MemcachedException: " + e.getMessage());
			throw e;
		}
	}

	/**
	 * 在已经存在的item前增加新item
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean prepend(String key, Object value) throws Exception {
		try {
			return client.prepend(key, value);
		} catch (TimeoutException e) {
			logger.error("TimeoutException: " + e.getMessage());
			throw e;
		} catch (InterruptedException e) {
			logger.error("InterruptedException: " + e.getMessage());
			throw e;
		} catch (MemcachedException e) {
			logger.error("MemcachedException: " + e.getMessage());
			throw e;
		}
	}

	/**
	 *
	 * @param key 名称
	 * @param delta 递增幅度
	 * @return
	 */
	public static long incr(String key, long delta) throws Exception {
		try {
			return client.incr(key, delta);
		} catch (TimeoutException e) {
			logger.error("TimeoutException: " + e.getMessage());
			throw e;
		} catch (InterruptedException e) {
			logger.error("InterruptedException: " + e.getMessage());
			throw e;
		} catch (MemcachedException e) {
			logger.error("MemcachedException: " + e.getMessage());
			throw e;
		}
	}

	/**
	 * Incr.
	 *
	 * @param key 名称
	 * @param delta 递增幅度
	 * @param initValue the init value 初始值
	 * @return the long
	 */
	public static long incr(String key, long delta, long initValue) throws Exception {
		try {
			return client.incr(key, delta, initValue);
		} catch (TimeoutException e) {
			logger.error("TimeoutException: " + e.getMessage());
			throw e;
		} catch (InterruptedException e) {
			logger.error("InterruptedException: " + e.getMessage());
			throw e;
		} catch (MemcachedException e) {
			logger.error("MemcachedException: " + e.getMessage());
			throw e;
		}
	}

	/**
	 *
	 * @param key 名称
	 * @param delta 递减幅度
	 * @return
	 */
	public static long decr(String key, long delta) throws Exception {
		try {
			return client.decr(key, delta);
		} catch (TimeoutException e) {
			logger.error("TimeoutException: " + e.getMessage());
			throw e;
		} catch (InterruptedException e) {
			logger.error("InterruptedException: " + e.getMessage());
			throw e;
		} catch (MemcachedException e) {
			logger.error("MemcachedException: " + e.getMessage());
			throw e;
		}
	}

	/**
	 * decr.
	 *
	 * @param key 名称
	 * @param delta 递减幅度
	 * @param initValue the init value 初始值
	 * @return the long
	 */
	public static long decr(String key, long delta, long initValue) throws Exception {
		try {
			return client.decr(key, delta, initValue);
		} catch (TimeoutException e) {
			logger.error("TimeoutException: " + e.getMessage());
			throw e;
		} catch (InterruptedException e) {
			logger.error("InterruptedException: " + e.getMessage());
			throw e;
		} catch (MemcachedException e) {
			logger.error("MemcachedException: " + e.getMessage());
			throw e;
		}
	}

	/**
	 * 新建计数器
	 * @param key 名称
	 * @param initialValue 初始值
	 * @return 返回计数器对象
	 */
	public static Object getCounter(String key, long initialValue) throws Exception {
		return client.getCounter(key, initialValue);
	}

	/**
	 * 原子操作
	 * @param key
	 * @param exp
	 * @param value
	 * @param cas
	 * @return
	 */
	public static boolean cas(String key, int exp, Object value, long cas) throws Exception {
		try {
			return client.cas(key, exp, value, cas);
		} catch (TimeoutException e) {
			logger.error("TimeoutException: " + e.getMessage());
			throw e;
		} catch (InterruptedException e) {
			logger.error("InterruptedException: " + e.getMessage());
			throw e;
		} catch (MemcachedException e) {
			logger.error("MemcachedException: " + e.getMessage());
			throw e;
		}
	}

	/**
	 * 原子操作
	 * @param key
	 * @param exp
	 * @param value
	 * @param tryNums
	 * @return
	 */
	public static boolean casWithCasOperation(String key, int exp, int value, int tryNums)
			throws Exception {
		final int iTryNums = tryNums;
		final int iValue = value;
		try {
			return client.cas(key, exp, new CASOperation<Integer>() {
				public int getMaxTries() {
					return iTryNums;
				}

				public Integer getNewValue(long currentCAS, Integer currentValue) {
					return iValue;
				}
			});
		} catch (TimeoutException e) {
			logger.error("TimeoutException: " + e.getMessage());
			throw e;
		} catch (InterruptedException e) {
			logger.error("InterruptedException: " + e.getMessage());
			throw e;
		} catch (MemcachedException e) {
			logger.error("MemcachedException: " + e.getMessage());
			throw e;
		}
	}

	/**
	 * 删除所有保存的项目
	 *
	 */
	public static void flushAll() throws Exception {
		try {
			client.flushAll();
		} catch (TimeoutException e) {
			logger.error("TimeoutException: " + e.getMessage());
			throw e;
		} catch (InterruptedException e) {
			logger.error("InterruptedException: " + e.getMessage());
			throw e;
		} catch (MemcachedException e) {
			logger.error("MemcachedException: " + e.getMessage());
			throw e;
		}

	}
}
