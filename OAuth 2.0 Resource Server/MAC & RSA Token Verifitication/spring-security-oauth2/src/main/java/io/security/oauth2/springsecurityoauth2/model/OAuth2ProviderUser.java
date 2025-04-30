package io.security.oauth2.springsecurityoauth2.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public abstract class OAuth2ProviderUser implements ProviderUser {

    private OAuth2User oAuth2User;
    private ClientRegistration clientRegistration;
    private Map<String, Object> attributes;
    private boolean isCertificated;

    public OAuth2ProviderUser(OAuth2User oAuth2User, Map<String, Object> attributes, ClientRegistration clientRegistration) {
        this.oAuth2User = oAuth2User;
        this.attributes = attributes;
        this.clientRegistration = clientRegistration;
    }

    @Override
    public String getPassword() {
        return UUID.randomUUID().toString();
    }

    @Override
    public String getEmail() {
        return (String) getAttributes().get("email");
    }

    @Override
    public String getProvider() {
        return clientRegistration.getRegistrationId();
    }

    @Override
    public List<? extends GrantedAuthority> getAuthorities() {
        // List -> Collection Stream
        return oAuth2User.getAuthorities().stream().map(authority -> new SimpleGrantedAuthority(authority.getAuthority())).collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    // Certificate 추가
    public boolean isCertificated() {
        return isCertificated;
    }

    public void isCertificated(boolean isCertificated) {
        this.isCertificated = isCertificated;
    }
}
