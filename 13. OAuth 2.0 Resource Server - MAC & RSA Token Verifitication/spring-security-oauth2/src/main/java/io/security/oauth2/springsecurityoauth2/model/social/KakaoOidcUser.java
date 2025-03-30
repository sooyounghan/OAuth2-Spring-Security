package io.security.oauth2.springsecurityoauth2.model.social;

import io.security.oauth2.springsecurityoauth2.model.Attributes;
import io.security.oauth2.springsecurityoauth2.model.OAuth2ProviderUser;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

/*
    Kakao는 OIDC, OAuth2.0 방식에서 scope가 서로 다르며, user-name-attribute도 상이
    따라서, OAuth 2.0 방식은 KakaoUser / OIDC 방식은 KakaoOidcUser 클래스 사용
 */
public class KakaoOidcUser extends OAuth2ProviderUser {

    public KakaoOidcUser(Attributes attributes, OAuth2User oAuth2User, ClientRegistration clientRegistration) {
        // Kakao OIDC의 경우 바로 1단계 SCOPE로 접근 가능(main) : getMainAttributes())
        super(oAuth2User, attributes.getMainAttributes(), clientRegistration);
    }

    @Override
    public String getId() {
        return (String) getAttributes().get("id");
    }

    @Override
    public String getUsername() {
        return (String) getAttributes().get("nickname");
    }

    @Override
    public String getPicture() {
        return (String) getAttributes().get("profile_image_url");
    }
}
