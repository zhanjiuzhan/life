package org.jcl.life.user.impl;

import org.jcl.life.user.User;
import org.jcl.life.user.UserDao;
import org.jcl.life.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Reference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

	@Resource
	private UserDao userDaoRedisImpl;

	@Override
	public User getUser() {
		return userDaoRedisImpl.getUser();
	}
}
