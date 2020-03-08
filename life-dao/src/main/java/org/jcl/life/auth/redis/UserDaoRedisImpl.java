package org.jcl.life.auth.redis;

import org.jcl.life.MyUtils;
import org.jcl.life.auth.User;
import org.jcl.life.auth.UserDao;
import org.jcl.life.auth.config.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author chenglei
 */
@Repository
public class UserDaoRedisImpl implements UserDao {
    private Logger logger = LoggerFactory.getLogger(UserDaoRedisImpl.class);

    private final String KEY = "user_hash_tab";

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public boolean addUser(User user) {
        user.setId(MyUtils.get32UUID());
        boolean result = redisUtils.hset(KEY, user.getId(), user);
        if (result) {
            logger.info("用户信息[username:{}]添加成功。", user.getUserName());
            return true;
        } else {
            logger.error("用户信息[username:{}]添加失败。", user.getUserName());
            return false;
        }
    }

    @Override
    public boolean delUser(String userId) {
        User user = getUser(userId);
        if (null != user) {
            long result = redisUtils.hdel(KEY, user.getId());
            if (result == 1) {
                logger.info("用户信息[username:{}]删除成功。", user.getUserName());
                return true;
            }
            logger.error("用户信息[username:{}]删除失败。", user.getUserName());
            return false;
        }
        logger.error("用户信息[username:{}]已经被删除了。", user.getUserName());
        return true;
    }

    @Override
    public User getUser(String userId) {
        return redisUtils.hget(KEY, userId, User.class);
    }

    @Override
    public boolean updateUser(User user) {
        User userTmp = getUser(user.getId());
        if (null != user) {
            boolean result = redisUtils.hset(KEY, userTmp.getId(), user);
            if (result) {
                logger.info("用户信息[username:{}]修改成功。", userTmp.getUserName());
                return true;
            } else {
                logger.error("用户信息[username:{}]修改失败。", userTmp.getUserName());
                return false;
            }
        }
        logger.error("用户信息[username:{}]已经被删除了。", user.getUserName());
        return false;
    }

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        Map userMap = redisUtils.hgetall(KEY);
        userMap.forEach((key, value) -> {
            if (null != value) {
                User user = (User) value;
                user.setPassword("");
                users.add(user);
            }
        });
        return users;
    }

    @Override
    public List<User> getUsersWithParam(String param) {
        List<User> users = new ArrayList<User>();
        Map userMap = redisUtils.hgetall(KEY);
        if (userMap != null) {
            userMap.forEach((key, value) -> {
                if (null != value) {
                    User user = (User) value;
                    if (user.getUserName().contains(param)) {
                        user.setPassword("");
                        users.add(user);
                    }
                }
            });
        }
        return users;
    }
}
