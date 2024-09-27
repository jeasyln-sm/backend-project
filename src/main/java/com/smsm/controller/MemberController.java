package com.smsm.controller;

import com.smsm.dto.MemberDto;
import com.smsm.entity.Member;
import com.smsm.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/member")
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;


    // 회원 등록 -> form으로 이동
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("memberDto", new MemberDto());

        return "member/registerForm";
    }

    // 회원 등록
    @PostMapping("/register")
    public String register(@Valid  MemberDto memberDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "member/registerForm";
        }

        if (!memberDto.getPassword1().equals(memberDto.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다.");
            return "member/registerForm";
        }

        try {
            // 회원 생성 로직
            Member member = Member.createMember(memberDto, passwordEncoder);
            memberService.saveMember(member);
        } catch(DataIntegrityViolationException e){
            // 중복된 사용자 처리
            bindingResult.reject("registerFailed", "이미 등록된 사용자입니다.");
            return "member/registerForm";
        } catch (IllegalStateException e) {
            // 일반적인 예외 처리
            model.addAttribute("errorMessage", e.getMessage());
            return "member/registerForm";
        }

        return "redirect:/";
    }


    // 회원 로그인
    @GetMapping("/login")
    public String loginMember() {
        return "member/loginForm";
    }

    @GetMapping("/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요.");
        return "member/loginForm";
    }


    // 회원 로그아웃
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/";
    }
}
