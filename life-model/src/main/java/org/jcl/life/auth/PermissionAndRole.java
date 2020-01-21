package org.jcl.life.auth;

import java.io.Serializable;

/**
 * @author Administrator
 */
public class PermissionAndRole implements Serializable {
    private static final long serialVersionUID = 408198233716573097L;
    private String id;
    private String roleId;
    private String permissionId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    @Override
    public String toString() {
        return "PermissionAndRole{" +
                "id=" + id +
                ", roleId=" + roleId +
                ", permissionId=" + permissionId +
                '}';
    }
}
