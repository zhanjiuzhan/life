package org.jcl.life.user.config;

import org.jcl.life.string.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class RedisUtils {
	private Logger logger = LoggerFactory.getLogger(RedisUtils.class);

	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	/**
	 * 取得普通的值
	 * @param key
	 * @return
	 */
	public Object get(String key) {
		return StringUtils.isEmpty(key) ? null :
				redisTemplate.opsForValue().get(key);

	}
}
