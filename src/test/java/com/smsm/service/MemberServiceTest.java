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


    public Member createMember()    {
        MemberDto memberDto = new MemberDto();
        memberDto.setName("홍길동");
        memberDto.setEmail("hong@gmail.com");
        memberDto.setAddress("서울시 마포구 합정동");
        memberDto.setPassword1("1234");
        memberDto.setPassword2("1234");
        return Member.createMember(memberDto, passwordEncoder);
    }


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


    @Test
    @DisplayName("중복 회원 가입 테스트")
    public void saveDuplicateMemberTest() {
        Member member1 = createMember();
        Member member2 = createMember();
        memberService.saveMember(member1);

        Throwable e = assertThrows(IllegalStateException.class, () -> {
                memberService.saveMember(member2);});

        assertEquals("이미 가입된 회원입니다.", e.getMessage());

    }
}
