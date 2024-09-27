package com.smsm.service;

import com.smsm.dto.MemberDto;
import com.smsm.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;


//    public Member createMember()    {
//        MemberDto memberDto = new MemberDto();
//        memberDto.setName("홍길동");
//        memberDto.setEmail("hong@gmail.com");
//        memberDto.setZipcode("12345");
//        memberDto.setPassword1("1234");
//        memberDto.setPassword2("1234");
//        return Member.createMember(memberDto, passwordEncoder);
//    }


//    @Test
//    @DisplayName("회원가입 테스트")
//    public void saveMemberTest() {
//        Member member = createMember();
//        Member savedMember = memberService.saveMember(member);
//
//        assertEquals(member.getEmail(), savedMember.getEmail());
//        assertEquals(member.getName(), savedMember.getName());
//        assertEquals(member.getRole(), savedMember.getRole());
//        assertEquals(member.getAddress(), savedMember.getAddress());
//        assertEquals(member.getPassword(), savedMember.getPassword());
//    }
}
