package org.jcl.life.auth.redis;

import org.jcl.life.MyUtils;
import org.jcl.life.auth.PermissionAndRole;
import org.jcl.life.auth.PermissionAndRoleDao;
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
public class PermissionAndRoleDaoReidsImpl implements PermissionAndRoleDao {

    private Logger logger = LoggerFactory.getLogger(PermissionAndRoleDaoReidsImpl.class);

    private final String KEY = "permission_adn_role_hash_tab";

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public boolean addRelation(PermissionAndRole permissionAndRole) {
        permissionAndRole.setId(MyUtils.get32UUID());
        boolean result = redisUtils.hset(KEY, permissionAndRole.getId(),
                permissionAndRole);
        if (result) {
            logger.info("添加角色权限关系成功[{}]", permissionAndRole.toString());
            return true;
        }
        logger.error("添加角色权限关系失败[{}]", permissionAndRole.toString());
        return false;
    }

    @Override
    public boolean deleteRelation(String relationId) {
        PermissionAndRole permissionAndRole = getPermissionAndRole(relationId);
        if (null != permissionAndRole) {
            long rs = redisUtils.hdel(KEY, permissionAndRole.getId());
            if (rs == 1) {
                logger.info("删除角色权限关系成功[{}]", permissionAndRole.toString());
                return true;
            }
            logger.error("删除角色权限关系失败[{}]", permissionAndRole.toString());
            return false;
        }
        logger.warn("角色权限关系已经不存在[relationId:{}]", relationId);
        return true;
    }

    @Override
    public boolean updateRelation(String relationId, String roleId) {
        PermissionAndRole permissionAndRole = getPermissionAndRole(relationId);
        if (null != permissionAndRole) {
            boolean result = redisUtils.hset(KEY, permissionAndRole.getId(),
                    permissionAndRole);
            if (result) {
                logger.info("修改角色权限关系成功[{}]", permissionAndRole.toString());
                return true;
            }
            logger.error("修改角色权限关系失败[{}]", permissionAndRole.toString());
        }
        logger.warn("角色权限关系已经不存在[relationId:{}]", relationId);
        return false;
    }

    @Override
    public List<PermissionAndRole> getRelations(String roleId) {
        return new ArrayList<>(0);
    }

    @Override
    public List<PermissionAndRole> getRelations() {
        List<PermissionAndRole> permissionAndRoles = new ArrayList<>();
        Map permissionAndRoleMap = redisUtils.hgetall(KEY);
        permissionAndRoleMap.forEach((key, value) -> {
            if (null != value) {
                PermissionAndRole permissionAndRole = (PermissionAndRole) value;
                permissionAndRoles.add(permissionAndRole);
            }
        });
        return permissionAndRoles;
    }

    private PermissionAndRole getPermissionAndRole(String id) {
        return redisUtils.hget(KEY, id, PermissionAndRole.class);
    }
}
