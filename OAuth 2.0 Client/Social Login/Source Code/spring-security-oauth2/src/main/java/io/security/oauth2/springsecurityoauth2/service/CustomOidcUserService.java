package io.security.oauth2.springsecurityoauth2.service;

import io.security.oauth2.springsecurityoauth2.common.converters.ProviderUserRequest;
import io.security.oauth2.springsecurityoauth2.model.PrincipalUser;
import io.security.oauth2.springsecurityoauth2.model.ProviderUser;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
public class CustomOidcUserService extends AbstractOAuth2UserService implements OAuth2UserService<OidcUserRequest, OidcUser> {
    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        // Kakao Oidc의 경우 sub가 user-name-attribute [OAuth2.0은 id] 이므로 이를 변경
        // ClientRegistration 설정을 모두 가져와 user-name-attribute를 변경해서 Build
        ClientRegistration clientRegistration = ClientRegistration.withClientRegistration(userRequest.getClientRegistration()).userNameAttributeName("sub").build();

        // Oidc 유저 재정의 (user-name-attribute 변경)
        OidcUserRequest oidcUserRequest = new OidcUserRequest(clientRegistration, userRequest.getAccessToken(), userRequest.getIdToken(), userRequest.getAdditionalParameters());

        OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService = new OidcUserService();
        OidcUser oidcUser = oidcUserService.loadUser(oidcUserRequest); // 인증 과정

        ProviderUserRequest providerUserRequest = new ProviderUserRequest(clientRegistration, oidcUser);
        ProviderUser providerUser = providerUser(providerUserRequest);

        // 회원 가입
        super.register(providerUser, userRequest);

        return new PrincipalUser(providerUser);
    }
}
