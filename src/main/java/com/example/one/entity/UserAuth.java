
package com.example.one.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserAuth  implements UserDetails {


/**
     * 用户权限列表
     */

    private Collection<? extends GrantedAuthority> authorities;

    private String username;

    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }


  public static  UserAuth create(User user) {
        UserAuth userAuth=new UserAuth();
        userAuth.setPassword(user.getAccount());
        userAuth.setUsername(user.getPassword());
        List<SimpleGrantedAuthority> simpleGrantedAuthorities=user.getPermissions().stream().map(permission -> new SimpleGrantedAuthority(permission.getPremission())).collect(Collectors.toList());
        userAuth.setAuthorities(simpleGrantedAuthorities);
        return userAuth;

    }

}

