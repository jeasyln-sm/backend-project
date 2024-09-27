package com.smsm.entity;

import com.smsm.common.Role;
import com.smsm.dto.MemberDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String address;             // 우편 번호

    private String streetaddress;		// 지번 주소

    private String detailaddress;		// 상세 주소

    @Enumerated(EnumType.STRING)
    private Role role;

    // provider : google이 들어감
    private String provider;

    // providerId : 구굴 로그인 한 유저의 고유 ID가 들어감
    private String providerId;


    // 회원등록
    public static Member createMember(MemberDto memberDto, PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setName(memberDto.getName());
        member.setEmail(memberDto.getEmail());
        member.setAddress(memberDto.getZipcode());
        member.setDetailaddress(memberDto.getDetailadr());
        member.setStreetaddress(memberDto.getStreetadr());
        String password = passwordEncoder.encode(memberDto.getPassword1());
        member.setPassword(password);
        member.setRole(Role.USER);
        return member;
    }

}
