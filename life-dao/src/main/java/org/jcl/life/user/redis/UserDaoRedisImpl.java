package org.jcl.life.user.redis;

import org.jcl.life.user.User;
import org.jcl.life.user.UserDao;
import org.jcl.life.user.config.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoRedisImpl implements UserDao {
	private Logger logger = LoggerFactory.getLogger(UserDaoRedisImpl.class);

	@Autowired
	private RedisUtils redisUtils;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Override
	public User getUser() {
		User user = new User();
		String string = (String)stringRedisTemplate.opsForValue().get("name");
		logger.info("redis:{}, name:{}", redisUtils, string);
		user.setUserName(string);
		return user;
	}
}
