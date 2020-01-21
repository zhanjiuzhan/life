package org.jcl.life.auth.redis;

import org.jcl.life.MyUtils;
import org.jcl.life.auth.Role;
import org.jcl.life.auth.RoleDao;
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
public class RoleDaoReidsImpl implements RoleDao {

    private Logger logger = LoggerFactory.getLogger(RoleDaoReidsImpl.class);

    private final String KEY = "role_hash_tab";

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public boolean addRole(Role role) {
        role.setId(MyUtils.get32UUID());
        boolean result = redisUtils.hset(KEY, role.getId(), role);
        if (result) {
            logger.info("角色信息[name:{}]添加成功。", role.getName());
            return true;
        }
        logger.error("角色信息[name:{}]添加失败。", role.getName());
        return false;
    }

    @Override
    public Role getRole(String roleId) {
        return redisUtils.hget(KEY, roleId, Role.class);
    }

    @Override
    public List<Role> getRoles() {
        List<Role> roles = new ArrayList<>();
        Map roleMap = redisUtils.hgetall(KEY);
        roleMap.forEach((key, value) -> {
            if (null != value) {
                Role role = (Role) value;
                roles.add(role);
            }
        });
        return roles;
    }

    @Override
    public boolean deleteRole(String roleId) {
        return false;
    }

    @Override
    public boolean updateRole(Role role) {
        return false;
    }
}
