package org.jcl.life.auth;

import java.util.List;

/**
 * @author chenglei
 */
public interface RoleAndUserService {

    /**
     * 添加一对user和role之间的关系
     *
     * @param roleAndUser
     * @return
     */
    boolean addRelation(RoleAndUser roleAndUser);

    /**
     * 删除一对关系的信息
     *
     * @param relationId
     * @return
     */
    boolean deleteRelation(String relationId);

    /**
     * 修改用户的角色信息
     *
     * @param relationId
     * @param userId
     * @return
     */
    boolean updateRelation(String relationId, String userId);

    /**
     * 根据用户信息查询所拥有的角色
     *
     * @param userId
     * @return
     */
    List<RoleAndUser> getRelations(String userId);


    /**
     * 根据用户信息查询所拥有的角色
     *
     * @return
     */
    List<RoleAndUser> getRelations();
}
