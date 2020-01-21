package org.jcl.life.auth.impl;

import org.jcl.life.auth.RoleAndUser;
import org.jcl.life.auth.RoleAndUserDao;
import org.jcl.life.auth.RoleAndUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenglei
 */
@Service
public class RoleAndUserServiceImpl implements RoleAndUserService {

    @Autowired
    private RoleAndUserDao roleAndUserDaoRedisImpl;

    @Override
    public boolean addRelation(RoleAndUser roleAndUser) {
        return roleAndUserDaoRedisImpl.addRelation(roleAndUser);
    }

    @Override
    public boolean deleteRelation(String relationId) {
        return roleAndUserDaoRedisImpl.deleteRelation(relationId);
    }

    @Override
    public boolean updateRelation(String relationId, String userId) {
        return roleAndUserDaoRedisImpl.updateRelation(relationId, userId);
    }

    @Override
    public List<RoleAndUser> getRelations() {
        return roleAndUserDaoRedisImpl.getRelations();
    }

    @Override
    public List<RoleAndUser> getRelations(String userId) {
        List<RoleAndUser> tmp = new ArrayList<>();
        List<RoleAndUser> roleAndUsers = getRelations();
        for (RoleAndUser roleAndUser : roleAndUsers) {
            if (roleAndUser.getUserId().equals(userId)) {
                tmp.add(roleAndUser);
            }
        }
        return tmp;
    }
}
