package org.jcl.life.auth;

import java.io.Serializable;

public class Permission implements Serializable {
    private static final long serialVersionUID = 4866182708284101440L;
    private String id;

    // 权限的名称
    private String name;

    // 权限的描述
    private String description;

    // 授权链接
    private String url;

    // 父节点id
    private int pid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", pid=" + pid +
                '}';
    }
}
