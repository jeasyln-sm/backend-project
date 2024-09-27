package com.smsm.config;

import com.smsm.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class CustomSecurityUserDetails implements UserDetails {

    private final Member member;
    public CustomSecurityUserDetails(Member member) {
        this.member = member;
    }

    // 현재 member의 role을 반환 (ex. "ROLE_ADMIN" / "ROLE_USER" 등)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
            	// 앞에 "ROLE_" 접두사 필수 !
                return "ROLE_" + member.getRole().name();
            }
        });

        return collection;
    }

    // member의 비밀번호 반환
    @Override
    public String getPassword() {
        return member.getPassword();
    }

    // member의 username 반환
    @Override
    public String getUsername() {
        return member.getEmail();   // 이메일로 로그인하기 때문에 email을 반환
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
