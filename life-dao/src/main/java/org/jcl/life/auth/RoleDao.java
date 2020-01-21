package org.jcl.life.auth;

import java.util.List;

/**
 * @author chenglei
 */
public interface RoleDao {

    /**
     * 添加角色信息
     *
     * @param role
     * @return
     */
    boolean addRole(Role role);

    /**
     * 取得对应的角色信息
     *
     * @param roleId
     * @return
     */
    Role getRole(String roleId);

    /**
     * 取得所有的角色信息
     *
     * @return
     */
    List<Role> getRoles();

    /**
     * 删除一个角色
     *
     * @param roleId
     * @return
     */
    boolean deleteRole(String roleId);

    /**
     * 修改角色信息
     *
     * @param role
     * @return
     */
    boolean updateRole(Role role);
}
