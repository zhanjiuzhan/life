package org.jcl.life.auth.redis;

import org.jcl.life.MyUtils;
import org.jcl.life.auth.RoleAndUser;
import org.jcl.life.auth.RoleAndUserDao;
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
public class RoleAndUserDaoReidsImpl implements RoleAndUserDao {

    private Logger logger = LoggerFactory.getLogger(RoleAndUserDaoReidsImpl.class);

    private final String KEY = "role_and_user_hash_tab";

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public boolean addRelation(RoleAndUser roleAndUser) {
        roleAndUser.setId(MyUtils.get32UUID());
        boolean rs = redisUtils.hset(KEY, roleAndUser.getId(), roleAndUser);
        if (rs) {
            logger.info("添加用户角色关联信息成功。{}", roleAndUser.toString());
            return true;
        }
        logger.error("添加用户角色关联信息失败。{}", roleAndUser.toString());
        return false;
    }

    @Override
    public boolean deleteRelation(String relationId) {
        RoleAndUser roleAndUser = getRoleAndUser(relationId);
        if (null != roleAndUser) {
            long rs = redisUtils.hdel(KEY, roleAndUser.getId());
            if (rs == 1) {
                logger.info("删除用户角色关联信息成功。{}", roleAndUser.toString());
                return true;
            }
            logger.error("删除用户角色关联信息失败。{}", roleAndUser.toString());
            return false;
        }
        logger.warn("用户角色关联信息已被删除。{}", roleAndUser.toString());
        return true;
    }

    @Override
    public boolean updateRelation(String relationId, String userId) {
        return false;
    }

    @Override
    public List<RoleAndUser> getRelations(String userId) {
        return new ArrayList<>(0);
    }

    @Override
    public List<RoleAndUser> getRelations() {
        List<RoleAndUser> roleAndUsers = new ArrayList<>();
        Map userMap = redisUtils.hgetall(KEY);
        userMap.forEach((key, value) -> {
            if (null != value) {
                RoleAndUser roleAndUser = (RoleAndUser) value;
                roleAndUsers.add(roleAndUser);
            }
        });
        return roleAndUsers;
    }

    private RoleAndUser getRoleAndUser(String id) {
        return redisUtils.hget(KEY, id, RoleAndUser.class);
    }
}
