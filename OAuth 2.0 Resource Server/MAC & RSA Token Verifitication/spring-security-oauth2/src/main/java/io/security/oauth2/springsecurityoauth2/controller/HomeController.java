package io.security.oauth2.springsecurityoauth2.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    // 인증 완료 상태 (SCOPE_profie, SCOPE_email 권한 필요) [OIDC 지원이 되지 않은 경우]
    @GetMapping("/api/user")
    public Authentication user(Authentication authentication, @AuthenticationPrincipal OAuth2User oAuth2User) {

        System.out.println("authentication = " + authentication + "oAuth2User = " + oAuth2User);

        return authentication;
    }

    // 인증 완료 상태 (OIDC 이므로 SCOPE_openid 필요) [구글의 경우 OIDC 지원하므로 이 권한을 가능하도록 하기 위함]
    @GetMapping("/api/oidc")
    public Authentication oidc(Authentication authentication, @AuthenticationPrincipal OidcUser oidcUser) {

        System.out.println("authentication = " + authentication + "oidcUser = " + oidcUser);

        return authentication;
    }
}
