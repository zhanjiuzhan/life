package org.jcl.life.auth;

import java.util.List;

/**
 * @author chenglei
 */
public interface UserService {
    /**
     * 添加一个用户账号信息
     * @param user
     * @return
     */
    boolean addUser(User user);

    /**
     * 删除一个用户账号信息
     * @param userId
     * @return
     */
    boolean delUser(String userId);

    /**
     * 取得用户信息
     * @param userId
     * @return
     */
    User getUser(String userId);

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    boolean updateUser(User user);

    /**
     * 取得所有的用户信息
     * @return
     */
    List<User> getUsers();

    /**
     * 根据用户名获取用户信息
     * @param userName
     * @return
     */
    User getUserByName(String userName);
}
