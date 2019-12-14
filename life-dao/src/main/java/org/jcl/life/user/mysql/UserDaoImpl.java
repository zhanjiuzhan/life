package org.jcl.life.user.mysql;

import org.jcl.life.user.User;
import org.jcl.life.user.UserDao;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {
	@Override
	public User getUser() {
		User user = new User();
		user.setUserName("wo");
		return user;
	}
}
