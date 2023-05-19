package cn.knightzz.community.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author 王天赐
 * @title: User
 * @projectName technical-tutorials
 * @description:
 * @website <a href="https://knightzz.cn/">https://knightzz.cn/</a>
 * @github <a href="https://github.com/knightzz1998">https://github.com/knightzz1998</a>
 * @create: 2023-05-19 11:43
 */
@Getter
@Setter
public class User implements UserDetails {

    private int id;
    private String username;
    private String password;
    private String salt;
    private String email;
    /**
     * 账号类型 : 1 ==> ADMIN
     */
    private int type;
    private int status;
    private String activationCode;
    private String headerUrl;
    private Date createTime;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                switch (type) {
                    case 1:
                        return "ADMIN";
                    default:
                        return "USER";
                }
            }
        });
        return authorities;
    }

    /**
     * true: 账号未过期.
     *
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * true: 账号未锁定.
     *
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * true: 凭证未过期.
     *
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * true: 账号可用.
     *
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
