package com.lookie.toy1_back.tome.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // csrf 비활성화
                .authorizeRequests()
                .antMatchers("/signup/user", "/signup/admin", "/login").permitAll() // 검증 없이 접근 허용
                .anyRequest().authenticated()
        .and()
        .logout()
        .logoutUrl("/logout")//logout을 호출하는 것 만으로 스프링 시큐리티가 로그아웃을 처리해줌
        .permitAll();//로그아웃처리
    }
}