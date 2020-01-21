package org.jcl.life.auth.impl;

import org.jcl.life.auth.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenglei
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionDao permissionDaoRedisImpl;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionAndRoleService permissionAndRoleService;

    @Override
    public boolean addPermission(Permission permission) {
        return permissionDaoRedisImpl.addPermission(permission);
    }

    @Override
    public boolean updatePermission(Permission permission) {
        return permissionDaoRedisImpl.updatePermission(permission);
    }

    @Override
    public boolean deletePermission(String permissionId) {
        return permissionDaoRedisImpl.deletePermission(permissionId);
    }

    @Override
    public Permission getPermission(String permissionId) {
        return permissionDaoRedisImpl.getPermission(permissionId);
    }

    @Override
    public List<Permission> getPermissions() {
        return permissionDaoRedisImpl.getPermissions();
    }

    @Override
    public List<Permission> getPermissions(String userName) {
        List<Permission> tmp = new ArrayList<>();
        User user = userService.getUserByName(userName);
        List<Role> roles = roleService.getRoles(user.getId());
        for (Role role : roles) {
            for (PermissionAndRole permissionAndRole :
                    permissionAndRoleService.getRelations(role.getId())) {
                tmp.add(getPermission(permissionAndRole.getPermissionId()));
            }
        }
        return tmp;
    }
}
