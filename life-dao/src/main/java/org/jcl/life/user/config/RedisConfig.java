package org.jcl.life.user.config;

import org.springframework.cache.annotation.CachingConfigurerSupport;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisConfig extends CachingConfigurerSupport {

	/**
	 * retemplate相关配置
	 * @param factory
	 * @return
	 */

	public RedisTemplate<String, Object> redeisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		// 配置连接工厂
		template.setConnectionFactory(factory);
		// 使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值(默认使用JDK的序列化方式)

		return template;
	}
}
