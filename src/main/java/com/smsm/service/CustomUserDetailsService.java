package com.smsm.service;

import com.smsm.common.Role;
import com.smsm.entity.Member;
import com.smsm.oauth.CustomOauth2UserDetails;
import com.smsm.oauth.GoogleUserDetails;
import com.smsm.oauth.OAuth2UserInfo;
import com.smsm.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("getAttributes : {}" + oAuth2User.getAttributes());

        String provider = userRequest.getClientRegistration().getRegistrationId();

        OAuth2UserInfo oAuth2UserInfo = null;

        // 뒤에 진행할 다른 소셜 서비스 로그인을 위해 구분 => 구글
        if (provider.equals("google")) {
            log.info("구글 로그인");
            oAuth2UserInfo = new GoogleUserDetails(oAuth2User.getAttributes());

        }

        String providerId = oAuth2UserInfo.getProviderId();
        String email = oAuth2UserInfo.getEmail();
        String loginId = provider + "_" + providerId;
        String name = oAuth2UserInfo.getName();

        Optional<Member> findMemberOptional = memberRepository.findByEmail(email);
        Member member;

        if (findMemberOptional.isEmpty()) {  // 회원이 없으면 새로 생성
            member = Member.builder()
                    .email(email)  // 이메일로 설정
                    .name(name)    // 이름 설정
                    .provider(provider)  // 제공자 설정 (예: google)
                    .providerId(providerId)  // Google 고유 ID 설정
                    .role(Role.USER)  // 기본 역할 설정
                    .build();
            memberRepository.save(member);  // 새로운 회원 저장
        } else {
            member = findMemberOptional.get();  // 이미 존재하는 회원
        }

        return new CustomOauth2UserDetails(member, oAuth2User.getAttributes());
    }
}
