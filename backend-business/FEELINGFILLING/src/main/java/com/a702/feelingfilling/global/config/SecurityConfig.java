/*
 * Spring Security 사용을 위한 Config 파일
 * */

package com.a702.feelingfilling.global.config;

import com.a702.feelingfilling.domain.user.model.repository.UserRepository;
import com.a702.feelingfilling.global.jwt.JwtFilter;
import com.a702.feelingfilling.global.jwt.JwtTokenService;
import com.a702.feelingfilling.global.oauth.OAuth2Service;
import com.a702.feelingfilling.global.oauth.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity //Spring Security 필터가 Spring 필터체인에 등록
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
// 특정 주소 접근시 권한 및 인증을 위한 어노테이션(secured, pre~~) 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private JwtTokenService jwtTokenService;
    private UserRepository userRepository;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/loginForm", "/token", "/swagger-ui/index.html", "/swagger-ui.html",
                        "/v3/api-docs", "/configuration/ui",
                        "/swagger-resources", "/configuration/security",
                        "/webjars/**", "/swagger/**", "/swagger-resources/**", "/swagger-ui/*");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); //rest api 서버는 csrf disable가능
        http.httpBasic().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);//토큰 기반 인증을 위해 세션 생성x

        http.authorizeRequests()
                .antMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**").permitAll()
                .antMatchers("/auth/**").authenticated()
                .antMatchers("/api/**").permitAll()
                .anyRequest().authenticated(); //나머지 요청에 대해서는 전부 사용자 인증

        // jwt 토큰 필터 추가
//        http.apply(new JwtFilter(jwtTokenService, userRepository),
//                UsernamePasswordAuthenticationFilter.class);

        http.apply(new JwtSecurityConfig(jwtTokenService));
        // cors
        http.cors().configurationSource(corsConfigurationSource());
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 내 서버가 응답할 때, json을 자바스크립트에서 처리할 수 있게 할지를 설정하는 것
        config.addExposedHeader("accessToken"); // 노출할 헤더 설정
        config.addExposedHeader("refreshToken"); // 노출할 헤더 설정
        config.addAllowedOriginPattern("*"); // 모든 ip의 응답을 허용
        config.addAllowedHeader("*"); // 모든 header의 응답을 허용
        config.addAllowedMethod("*"); // 모든 post, put 등의 메서드에 응답을 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);    // 모든 경로에 Cors설정
        return source;
    }


}


