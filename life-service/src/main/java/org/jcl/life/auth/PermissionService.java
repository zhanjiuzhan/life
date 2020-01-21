package org.jcl.life.auth;

import java.util.List;

/**
 * @author chenglei
 */
public interface PermissionService {

    /**
     * 添加一个权限信息
     *
     * @param permission
     * @return
     */
    boolean addPermission(Permission permission);

    /**
     * 修改权限信息
     *
     * @param permission
     * @return
     */
    boolean updatePermission(Permission permission);

    /**
     * 删除权限
     *
     * @param permissionId
     * @return
     */
    boolean deletePermission(String permissionId);

    /**
     * 取得权限信息
     *
     * @param permissionId
     * @return
     */
    Permission getPermission(String permissionId);

    /**
     * 取得所有的权限信息
     *
     * @return
     */
    List<Permission> getPermissions();

    /**
     * 根据用户id取得用户的权限
     * @param userName
     * @return
     */
    List<Permission> getPermissions(String userName);
}
