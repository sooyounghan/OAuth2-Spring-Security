package io.security.oauth2.springsecurityoauth2.config;

import io.security.oauth2.springsecurityoauth2.service.CustomOAuth2UserService;
import io.security.oauth2.springsecurityoauth2.service.CustomOidcUserService;
import io.security.oauth2.springsecurityoauth2.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@EnableWebSecurity
public class OAuth2ClientConfig {
    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    private CustomOidcUserService customOidcUserService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/static/js/**", "/static/images/**", "/static/css/**", "/static/scss/**");
    }

    @Bean
    SecurityFilterChain oauth2ClientSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(authRequest -> authRequest
                .antMatchers("/api/user").access("hasAnyRole('SCOPE_profile', 'SCOPE_email')") // api 자원에 접근 가능 (SCOPE_ 권한)
                .antMatchers("/api/oidc").access("hasAnyRole('SCOPE_openid')") // OIDC의 경우에는 openid 포함
                .antMatchers("/").permitAll()
                .anyRequest().authenticated());

        // 폼 인증을 위한 로그인
        http.formLogin().loginPage("/login").loginProcessingUrl("/loginProc").defaultSuccessUrl("/").permitAll();

        http.oauth2Login(oauth2 -> oauth2.userInfoEndpoint(userInfoEndpointConfig // UserInfo EndPoint 설정
                -> userInfoEndpointConfig.userService(customOAuth2UserService) // customOAuth2UserService
                .oidcUserService(customOidcUserService))); // customOidcUserService

        http.userDetailsService(customUserDetailsService); // Form

        // 인증 실패 시 로그인 페이지로 이동
        http.exceptionHandling().authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"));

        http.logout().logoutSuccessUrl("/");

        return http.build();
    }
}
