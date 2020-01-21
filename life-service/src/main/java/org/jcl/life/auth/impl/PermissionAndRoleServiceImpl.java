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
public class PermissionAndRoleServiceImpl implements PermissionAndRoleService {

    @Autowired
    private PermissionAndRoleDao permissionAndRoleDaoRedisImpl;


    @Override
    public boolean addRelation(PermissionAndRole permissionAndRole) {
        return permissionAndRoleDaoRedisImpl.addRelation(permissionAndRole);
    }

    @Override
    public boolean deleteRelation(String relationId) {
        return permissionAndRoleDaoRedisImpl.deleteRelation(relationId);
    }

    @Override
    public boolean updateRelation(String relationId, String roleId) {
        return permissionAndRoleDaoRedisImpl.updateRelation(relationId, roleId);
    }

    @Override
    public List<PermissionAndRole> getRelations(String roleId) {
        List<PermissionAndRole> tmp = new ArrayList<>();
        List<PermissionAndRole> permissionAndRoles = getRelations();
        for (PermissionAndRole permissionAndRole : permissionAndRoles) {
            if (permissionAndRole.getRoleId().equals(roleId)) {
                tmp.add(permissionAndRole);
            }
        }
        return tmp;
    }

    @Override
    public List<PermissionAndRole> getRelations() {
        return permissionAndRoleDaoRedisImpl.getRelations();
    }

    @Override
    public List<PermissionAndRole> getRelationsByPermId(String permissionId) {
        List<PermissionAndRole> tmp = new ArrayList<>();
        List<PermissionAndRole> permissionAndRoles = getRelations();
        for (PermissionAndRole permissionAndRole : permissionAndRoles) {
            if (permissionAndRole.getPermissionId().equals(permissionId)) {
                tmp.add(permissionAndRole);
            }
        }
        return tmp;
    }
}
