package io.security.oauth2.springsecurityoauth2.model.social;

import io.security.oauth2.springsecurityoauth2.model.Attributes;
import io.security.oauth2.springsecurityoauth2.model.OAuth2ProviderUser;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class GoogleUser extends OAuth2ProviderUser {

    public GoogleUser(Attributes mainAttributes, OAuth2User oAuth2User, ClientRegistration clientRegistration) {
        super(oAuth2User, mainAttributes.getMainAttributes(), clientRegistration);
    }

    @Override
    public String getId() {
        return (String) getAttributes().get("sub");
    }

    @Override
    public String getUsername() {
        return (String) getAttributes().get("name");
    }

    @Override
    public String getPicture() {
        return null; // 구글은 해당 기능 없음
    }
}
