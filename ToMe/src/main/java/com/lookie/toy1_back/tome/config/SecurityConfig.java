package com.lookie.toy1_back.tome.config;

import com.lookie.toy1_back.tome.handler.MyLoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and();
        //로그인
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/login")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()//csrf 토큰 검사를 비활성화 하는 로직
                //form 을 통한 login 을 활성화하고
                //사용자가 login 이 필요한 페이지에 접근 했을 때, 사용자에게 보여줄 페이지를 띄워줄 URL 을 지정
                .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/doLogin")
        .usernameParameter("username")
        .passwordParameter("password")
        .successHandler(new MyLoginSuccessHandler())
        .and()
        .logout()
        .logoutUrl("/doLogout")
        .logoutSuccessUrl("/login");
        http.sessionManagement()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false)
                .expiredUrl("/duplicated-login")
                .sessionRegistry(sessionRegistry());

    }
    @Bean
    public SessionRegistry sessionRegistry(){
        return new SessionRegistryImpl();
    }
}