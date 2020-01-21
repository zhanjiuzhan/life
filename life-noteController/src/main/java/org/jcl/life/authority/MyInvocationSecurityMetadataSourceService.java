package org.jcl.life.authority;

import org.jcl.life.auth.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author Administrator
 */
@Component
public class MyInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private PermissionAndRoleService permissionAndRoleService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RoleService roleService;

    /**
     * 每一个资源所需要的角色 Collection<ConfigAttribute>决策器会用到
     */
    private static HashMap<String, Collection<ConfigAttribute>> map =null;

    /**
     * 返回请求的资源需要的角色
     * @param o
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        if (null == map) {
            loadResourceDefine();
        }
        //object 中包含用户请求的request 信息
        HttpServletRequest request = ((FilterInvocation) o).getHttpRequest();
        for (Iterator<String> it = map.keySet().iterator(); it.hasNext();) {
            String url = it.next();
            if (new AntPathRequestMatcher( url ).matches( request )) {
                return map.get(url);
            }
        }

        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

    /**
     * 初始化 所有资源 对应的角色
     */
    private void loadResourceDefine() {
        map = new HashMap<>();
        //权限资源 和 角色对应的表  也就是 角色权限 中间表
        List<Permission> permissions = permissionService.getPermissions();

        //某个资源 可以被哪些角色访问
        for (Permission permission : permissions) {
            List<PermissionAndRole> permissionAndRoles =
                    permissionAndRoleService.getRelationsByPermId(permission.getId());
            String url = permission.getUrl();

            List<ConfigAttribute> list =  new ArrayList<>();
            for (PermissionAndRole permissionAndRole : permissionAndRoles) {
                Role role = roleService.getRole(permissionAndRole.getRoleId());
                if (null != role) {
                    list.add(new SecurityConfig(role.getName()));
                }
            }
            map.put(url, list);
        }
    }
}
