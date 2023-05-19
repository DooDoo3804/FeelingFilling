package com.a702.feelingfilling.global.config;

import com.a702.feelingfilling.global.jwt.JwtFilter;
import com.a702.feelingfilling.global.jwt.JwtTokenService;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import springfox.documentation.swagger.web.SecurityConfiguration;

public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private JwtTokenService jwtTokenService;

    public JwtSecurityConfig(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    // Before 필터 등록
    @Override
    public void configure(HttpSecurity http) {
        http.antMatcher("/auth/**").addFilterBefore(
                new JwtFilter(jwtTokenService),
                UsernamePasswordAuthenticationFilter.class
        );
    }

}
