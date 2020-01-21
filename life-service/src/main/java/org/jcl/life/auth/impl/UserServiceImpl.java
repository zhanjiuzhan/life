package org.jcl.life.auth.impl;

import org.jcl.life.auth.User;
import org.jcl.life.auth.UserDao;
import org.jcl.life.auth.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chenglei
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDaoRedisImpl;

    @Override
    public boolean addUser(User user) {
        return userDaoRedisImpl.addUser(user);
    }

    @Override
    public boolean delUser(String userId) {
        return userDaoRedisImpl.delUser(userId);
    }

    @Override
    public User getUser(String userId) {
        return userDaoRedisImpl.getUser(userId);
    }

    @Override
    public boolean updateUser(User user) {
        return userDaoRedisImpl.updateUser(user);
    }

    @Override
    public List<User> getUsers() {
        return userDaoRedisImpl.getUsers();
    }

    @Override
    public User getUserByName(String userName) {
        List<User> users = getUsers();
        for(User user : users) {
            if (user.getUserName().equals(userName)) {
                return user;
            }
        }
        return null;
    }
}
