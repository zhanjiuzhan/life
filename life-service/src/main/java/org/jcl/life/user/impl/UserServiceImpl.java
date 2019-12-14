package org.jcl.life.user.impl;

import org.jcl.life.user.User;
import org.jcl.life.user.UserDao;
import org.jcl.life.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDaoImpl;

	@Override
	public User getUser() {
		return userDaoImpl.getUser();
	}
}
