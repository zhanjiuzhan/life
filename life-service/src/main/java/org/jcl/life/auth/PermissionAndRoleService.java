package org.jcl.life.auth;

import java.util.List;

/**
 * @author chenglei
 */
public interface PermissionAndRoleService {

    /**
     * 添加一对role和permission之间的关系
     *
     * @param permissionAndRole
     * @return
     */
    boolean addRelation(PermissionAndRole permissionAndRole);

    /**
     * 删除一对关系的信息
     *
     * @param relationId
     * @return
     */
    boolean deleteRelation(String relationId);

    /**
     * 修改角色的权限信息
     *
     * @param relationId
     * @param roleId
     * @return
     */
    boolean updateRelation(String relationId, String roleId);

    /**
     * 根据角色信息查询所拥有的权限
     *
     * @param roleId
     * @return
     */
    List<PermissionAndRole> getRelations(String roleId);

    /**
     * 查询所拥有的权限
     *
     * @return
     */
    List<PermissionAndRole> getRelations();

    /**
     * 根据permissionId查询所有的对应关系
     * @param permissionId
     * @return
     */
    List<PermissionAndRole> getRelationsByPermId(String permissionId);
}
