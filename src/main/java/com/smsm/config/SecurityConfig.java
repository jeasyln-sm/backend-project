package com.smsm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.formLogin(form -> form
                .loginPage("/member/login")
                .failureUrl("/member/login/error")
                .defaultSuccessUrl("/")
                .usernameParameter("email")
                .passwordParameter("password")
                .permitAll());

        // OAuth 2.0 로그인 방식 설정
        http.oauth2Login(auth -> auth
                .loginPage("/member/login")
                .defaultSuccessUrl("/")
                .failureUrl("/member/login/error")
                .permitAll());
        http.logout(auth -> auth
                .logoutUrl("/member/logout"));
//        http.csrf(auth -> auth.disable());

        http.authorizeHttpRequests(authz -> authz
                // 인가 동작 순서 : 위에서 부터 아래로 순서대로 ! 따라서 순서 유의 (anyRequest 특히)
                .requestMatchers("/css/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")  // 접근 제어
                .requestMatchers("/", "/member/**").permitAll()
                .anyRequest().authenticated());

        http.logout(logout -> logout
                .logoutSuccessUrl("/"));

        http.exceptionHandling(exception -> exception
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint()));


        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
