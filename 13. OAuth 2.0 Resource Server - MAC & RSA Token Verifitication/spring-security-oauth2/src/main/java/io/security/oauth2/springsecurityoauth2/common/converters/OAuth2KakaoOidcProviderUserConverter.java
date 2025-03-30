package io.security.oauth2.springsecurityoauth2.common.converters;

import io.security.oauth2.springsecurityoauth2.common.enums.OAuth2Config;
import io.security.oauth2.springsecurityoauth2.common.util.OAuth2Utils;
import io.security.oauth2.springsecurityoauth2.model.ProviderUser;
import io.security.oauth2.springsecurityoauth2.model.social.KakaoOidcUser;
import io.security.oauth2.springsecurityoauth2.model.social.KakaoUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

/*
    Kakao OIDC 방식 Converter
 */
public class OAuth2KakaoOidcProviderUserConverter implements ProviderUserConverter<ProviderUserRequest, ProviderUser> {
    @Override
    public ProviderUser converter(ProviderUserRequest providerUserRequest) {
        if(!providerUserRequest.clientRegistration().getRegistrationId().equals(OAuth2Config.SocialType.KAKAO.getSocialName())) {
            return null;
        }

        // Kakao OIDC 방식 확인
        if(!(providerUserRequest.oAuth2User() instanceof OidcUser)) {
            return null;
        }
        return new KakaoOidcUser(OAuth2Utils.getMainAttributes(providerUserRequest.oAuth2User()), providerUserRequest.oAuth2User(), providerUserRequest.clientRegistration());
    }
}
