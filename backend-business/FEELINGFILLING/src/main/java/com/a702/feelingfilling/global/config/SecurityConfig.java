package com.a702.feelingfilling.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Override
  public void configure(WebSecurity web) throws Exception {
    // resources 모든 접근을 허용하는 설정을 해버리면
    // HttpSecurity 설정한 ADIM권한을 가진 사용자만 resources 접근가능한 설정을 무시해버린다.
    web.ignoring()
        .antMatchers("**","/api/spot/token", "/swagger-ui/index.html", "/swagger-ui.html", "/",
            "/loginForm", "/token/refresh", "/v3/api-docs", "/configuration/ui",
            "/swagger-resources", "/configuration/security",
            "/webjars/**", "/swagger/**", "/api/postcard/detail");
  }
}
