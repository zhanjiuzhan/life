package org.jcl.life.authority;

import org.jcl.life.auth.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenglei
 */
@Service
public class AuthUserServiceImpl implements UserDetailsService {

    private final Logger logger =
            LoggerFactory.getLogger(AuthUserServiceImpl.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        logger.info("取得userName[{}]", userName);
        User user = userService.getUserByName(userName);
        if (null != user) {
            List<Role> roles = roleService.getRoles(user.getId());
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

            for (Role role : roles) {
                if (role != null && role.getName() != null) {
                    GrantedAuthority grantedAuthority =
                            new SimpleGrantedAuthority(role.getName());

                    // 此处将用户对应的角色信息添加到 GrantedAuthority
                    // 对象中，在后面进行全权限验证时会使用GrantedAuthority 对象。
                    grantedAuthorities.add(grantedAuthority);
                }
            }
            UserDetailImpl userDetails = new UserDetailImpl();
            userDetails.setPassword(user.getPassword())
                    .setGrantedAuthoritys(grantedAuthorities)
                    .setUserName(user.getUserName());
            return userDetails;
        }
        throw new UsernameNotFoundException("用户不存在");
    }
}
