/*
package com.example.PicMouse.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
//@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
        return httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .cors().and()
                .authorizeRequests()
                .requestMatchers("/private/**").authenicated()
                //private 로 시작하는 페이지 로그인 필수
                .requestMachers("/admin/**").access("hasRole('ROLE_ADMIN')")
                //admin으로 시작하는 uri는 관계자 계정만 접근 가능
                .anyRequest().perminAll()
                //나머지 uri는 모두 접근 가능
                .and().oauth2Login()
                .loginPage("/loginForm")
                .defaultSuccessUrl("/")
                //oath구글 로그인이 성공하면 이동할 url
                .userInfoEndpoint()
                //로그인 완료 후 회원 정보 받기
                .userSerivce(oAuth2MemberServi).and().and().build()
    }
}
*/
