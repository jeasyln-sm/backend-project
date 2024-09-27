package com.smsm.oauth;

import com.smsm.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class CustomOauth2UserDetails implements UserDetails, OAuth2User {

    private final Member member;
    private Map<String, Object> attributes;

    public CustomOauth2UserDetails(Member member, Map<String, Object> attributes) {
        this.member = member;
        this.attributes = attributes;
    }

    // OAuth2User의 메서드
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;  // OAuth2 provider에서 제공하는 사용자 속성
    }

    @Override
    public String getName() {
        return member.getProviderId();
    }
    // UserDetails의 메서드
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(() -> "ROLE_" + member.getRole().name());
    }

    @Override
    public String getPassword() {
        return member.getPassword();  // 비밀번호 반환
    }

    @Override
    public String getUsername() {
        return member.getEmail();  // 이메일로 로그인하기 때문에 이메일 반환
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // 계정 만료 여부
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // 계정 잠금 여부
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // 자격 증명 만료 여부
    }

    @Override
    public boolean isEnabled() {
        return true;  // 계정 활성화 여부
    }
}
