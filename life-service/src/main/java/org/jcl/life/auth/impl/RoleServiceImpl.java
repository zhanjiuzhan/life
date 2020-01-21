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
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDaoRedisImpl;

    @Autowired
    private RoleAndUserService roleAndUserService;

    @Override
    public boolean addRole(Role role) {
        return roleDaoRedisImpl.addRole(role);
    }

    @Override
    public Role getRole(String roleId) {
        return roleDaoRedisImpl.getRole(roleId);
    }

    @Override
    public List<Role> getRoles() {
        return roleDaoRedisImpl.getRoles();
    }

    @Override
    public boolean deleteRole(String roleId) {
        return roleDaoRedisImpl.deleteRole(roleId);
    }

    @Override
    public boolean updateRole(Role role) {
        return roleDaoRedisImpl.updateRole(role);
    }

    @Override
    public List<Role> getRoles(String userId) {
        List<Role> roles = new ArrayList<>();
        List<RoleAndUser> roleAndUsers =
                roleAndUserService.getRelations(userId);
        for (RoleAndUser roleAndUser : roleAndUsers) {
            roles.add(getRole(roleAndUser.getRoleId()));
        }
        return roles;
    }
}
