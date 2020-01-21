package org.jcl.life.auth.config;

import org.jcl.life.string.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
@ConditionalOnBean({RedisTemplate.class})
public class RedisUtils {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 设置key的超时时间
     *
     * @param key  redis的key
     * @param time 超时时间为秒
     * @return 设置成功返回true 其他情况是false
     */
    public boolean expire(final String key, final long time) {
        if (StringUtils.isNotEmpty(key) && time > 0) {
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
            return true;
        }
        return false;
    }

    /**
     * 取得指定key的超时时间
     *
     * @param key redis的key
     * @return 返回指定key的超时时间
     * key为空或者不存在返回-2
     * key是永久的返回-1
     */
    public long getExpire(final String key) {
        if (StringUtils.isNotEmpty(key)) {
            return redisTemplate.getExpire(key);
        }
        return -2;
    }

    /**
     * 判断key是否存在
     *
     * @param key redis的key
     * @return 存在返回true 其它是false
     */
    public boolean hashKey(final String key) {
        if (StringUtils.isNotEmpty(key)) {
            return redisTemplate.hasKey(key);
        }
        return false;
    }

    /**
     * 根据key删除缓存
     *
     * @param key key可以是一个或者多个
     */
    public void del(final String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    // ===== String 类型的 redis 操作 =====

    /**
     * 根据key取得redis中存储的value值
     *
     * @param key  redis的key
     * @param type 返回数据的类型
     * @return 取不到或其他返回null
     */
    public <T> T get(final String key, final Class<T> type) {
        if (StringUtils.isNotEmpty(key)) {
            return (T) redisTemplate.opsForValue().get(key);
        }
        return null;
    }

    /**
     * 根据key 设置key对应的value值
     *
     * @param key   redis的key
     * @param value 对应的value值
     * @return 设置成功返回true 其它返回false
     */
    public boolean set(final String key, final Object value) {
        if (StringUtils.isNotEmpty(key) && null != value) {
            redisTemplate.opsForValue().set(key, value);
            return true;
        }
        return false;
    }

    /**
     * 同上 只是添加了个存在的时间
     *
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean set(final String key, final Object value, final long time) {
        if (StringUtils.isNotEmpty(key) && null != value) {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time);
            } else {
                redisTemplate.opsForValue().set(key, value);
            }
            return true;
        }
        return false;
    }

    // ===== Hash 类型的 redis 操作 =====

    /**
     * HashGet
     *
     * @param key
     * @param item
     * @param type
     * @param <T>
     * @return
     */
    public <T> T hget(final String key, final String item, final Class<T> type) {
        if (StringUtils.isNotEmpty(key, item)) {
            return (T) redisTemplate.opsForHash().get(key, item);
        }
        return null;
    }

    /**
     * 根据key取得所有的键值
     *
     * @param key
     * @return
     */
    public Map<Object, Object> hgetall(final String key) {
        if (StringUtils.isNotEmpty(key)) {
            return redisTemplate.opsForHash().entries(key);
        }
        return new HashMap<>(0);
    }

    /**
     * hset
     *
     * @param key
     * @param field
     * @param value
     * @return
     */
    public boolean hset(final String key, final String field, final Object value) {
        if (StringUtils.isNotEmpty(key, field) && null != value) {
            redisTemplate.opsForHash().put(key, field, value);
            return true;
        }
        return false;
    }

    /**
     * hmset
     *
     * @param key
     * @param map
     * @return
     */
    public boolean hmset(final String key, final Map<String, Object> map) {
        if (StringUtils.isNotEmpty(key) && map != null) {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        }
        return false;
    }

    /**
     * hdel
     *
     * @param key
     * @param item
     * @return
     */
    public Long hdel(String key, Object... item) {
        if (StringUtils.isNotEmpty(key) && item != null && item.length > 0) {
            return redisTemplate.opsForHash().delete(key, item);
        }
        return 0L;
    }

    /**
     * hexists
     *
     * @param key
     * @param field
     * @return
     */
    public boolean hexists(final String key, final String field) {
        if (StringUtils.isNotEmpty(key, field)) {
            return redisTemplate.opsForHash().hasKey(key, field);
        }
        return false;
    }

    // ===== Set 类型的 redis 操作 =====

    /**
     * @param key
     * @return
     */
    public Set<Object> smembers(final String key) {
        if (StringUtils.isNotEmpty(key)) {
            return redisTemplate.opsForSet().members(key);
        }
        return new HashSet<Object>(0);
    }

    /**
     * @param key
     * @param member
     * @return
     */
    public boolean sismember(final String key, final String member) {
        if (StringUtils.isNotEmpty(key, member)) {
            return redisTemplate.opsForSet().isMember(key, member);
        }
        return false;
    }


    /**
     * @param key
     * @param member
     * @return
     */
    public long sadd(final String key, final Object... member) {
        if (StringUtils.isNotEmpty(key) && null != member && member.length > 0) {
            return redisTemplate.opsForSet().add(key, member);
        }
        return 0L;
    }

    /**
     * @param key
     * @return
     */
    public long scard(final String key) {
        if (StringUtils.isNotEmpty(key)) {
            return redisTemplate.opsForSet().size(key);
        }
        return 0L;
    }

    /**
     * @param key
     * @param member
     * @return
     */
    public long srem(final String key, final Object... member) {
        if (StringUtils.isNotEmpty(key) && null != member && member.length > 0) {
            return redisTemplate.opsForSet().remove(key, member);
        }
        return 0L;
    }

    // ===== List 类型的 redis 操作 =====

    /**
     * @param key
     * @param object
     * @return
     */
    public long lpush(final String key, final Object... object) {
        if (StringUtils.isNotEmpty(key) && object != null && object.length > 0) {
            return redisTemplate.opsForList().leftPushAll(key, object);
        }
        return 0L;
    }

    /**
     * @param key
     * @return
     */
    public long llen(final String key) {
        if (StringUtils.isNotEmpty(key)) {
            return redisTemplate.opsForList().size(key);
        }
        return 0L;
    }

    /**
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<Object> lrange(final String key, final long start, final long end) {
        if (StringUtils.isNotEmpty(key)) {
            return redisTemplate.opsForList().range(key, start, end);
        }
        return new ArrayList<Object>(0);
    }

    /**
     * @param key
     * @return
     */
    public List<Object> lrange(final String key) {
        return lrange(key, 0, llen(key));
    }

    /**
     * @param key
     * @param index
     * @return
     */
    public Object lindex(final String key, final long index) {
        if (StringUtils.isNotEmpty(key)) {
            return redisTemplate.opsForList().index(key, index);
        }
        return null;
    }

    /**
     * @param key
     * @param index
     * @param obj
     * @return
     */
    public boolean lset(final String key, final long index, final Object obj) {
        if (StringUtils.isNotEmpty(key) && obj != null) {
            redisTemplate.opsForList().set(key, index, obj);
            return true;
        }
        return false;
    }

    /**
     * @param key
     * @param count
     * @param obj
     * @return
     */
    public long lrem(final String key, final long count, final Object obj) {
        if (StringUtils.isNotEmpty(key) && null != obj) {
            return redisTemplate.opsForList().remove(key, count, obj);
        }
        return 0L;
    }

    /**
     * @param key
     * @return
     */
    public Object rpop(final String key) {
        if (StringUtils.isNotEmpty(key)) {
            return redisTemplate.opsForList().rightPop(key);
        }
        return null;
    }

    // ===== zset 类型的 redis 操作 =====
}
