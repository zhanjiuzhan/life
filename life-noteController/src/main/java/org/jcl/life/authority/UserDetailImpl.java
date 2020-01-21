package org.jcl.life.authority;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


/**
 * @author chenglei
 */
public class UserDetailImpl implements UserDetails {

    private static final long serialVersionUID = 7448215284148355835L;

    private final Logger logger = LoggerFactory.getLogger(UserDetailImpl.class);

    private String userName;
    private String password;

    private List<GrantedAuthority> grantedAuthoritys;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthoritys;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    /**
     * 当前账号是否已经过期
     *
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    /**
     * 当前账号是否被锁
     *
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    /**
     * 当前账号证书（密码）是否过期
     *
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    /**
     * 当前账号是否被禁用
     *
     * @return
     */
    @Override
    public boolean isEnabled() {
        return false;
    }


    public UserDetailImpl setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public UserDetailImpl setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserDetailImpl setGrantedAuthoritys(List<GrantedAuthority> grantedAuthoritys) {
        this.grantedAuthoritys = grantedAuthoritys;
        return this;
    }
}
