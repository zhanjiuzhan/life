package org.jcl.life.auth.redis;

import org.jcl.life.MyUtils;
import org.jcl.life.auth.Permission;
import org.jcl.life.auth.PermissionDao;
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
public class PermissionDaoReidsImpl implements PermissionDao {

    private Logger logger = LoggerFactory.getLogger(PermissionDaoReidsImpl.class);

    private final String KEY = "permission_hash_tab";

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public boolean addPermission(Permission permission) {
        permission.setId(MyUtils.get32UUID());
        boolean rs = redisUtils.hset(KEY, permission.getId(), permission);
        if (rs) {
            logger.info("添加权限信息成功。{}", permission.toString());
            return true;
        }
        logger.error("添加权限信息失败。{}", permission.toString());
        return false;
    }

    @Override
    public boolean updatePermission(Permission permission) {
        Permission permissionTmp = getPermission(permission.getId());
        if (null != permissionTmp) {
            boolean rs = redisUtils.hset(KEY, permission.getId(), permission);
            if (rs) {
                logger.info("修改权限信息成功。{}", permission.toString());
                return true;
            }
            logger.info("修改权限信息失败。{}", permissionTmp.toString());
            return false;
        }
        logger.warn("权限信息已经删除。{}", permission.toString());
        return false;
    }

    @Override
    public boolean deletePermission(String permissionId) {
        Permission permissionTmp = getPermission(permissionId);
        if (null != permissionTmp) {
            long rs = redisUtils.hdel(KEY, permissionTmp.getId());
            if (rs == 1) {
                logger.info("权限信息删除成功。permissionId: {}", permissionId);
                return true;
            }
            logger.error("权限信息删除失败。permissionId: {}", permissionId);
            return false;
        }
        logger.error("权限信息已经删除。permissionId: {}", permissionId);
        return true;
    }

    @Override
    public Permission getPermission(String permissionId) {
        return redisUtils.hget(KEY, permissionId, Permission.class);
    }

    @Override
    public List<Permission> getPermissions() {
        List<Permission> permissions = new ArrayList<>();
        Map permissionMap = redisUtils.hgetall(KEY);
        permissionMap.forEach((key, value) -> {
            if (null != value) {
                Permission user = (Permission) value;
                permissions.add(user);
            }
        });
        return permissions;
    }
}
