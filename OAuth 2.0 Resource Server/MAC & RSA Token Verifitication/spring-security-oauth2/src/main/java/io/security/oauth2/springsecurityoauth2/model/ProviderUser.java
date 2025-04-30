package io.security.oauth2.springsecurityoauth2.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.List;
import java.util.Map;

public interface ProviderUser {

    public String getId();

    public String getUsername();

    public String getPassword();

    public String getEmail();

    public String getPicture(); // 프로필 사진 불러오기

    public String getProvider();

    public List<? extends GrantedAuthority> getAuthorities();

    public Map<String, Object> getAttributes();

    public OAuth2User getOAuth2User(); // OAuth2User 가져오기

    // Certificate 추가
    public boolean isCertificated(); // 조건에 따라 본인 인증 시작 / 완료
    public void isCertificated(boolean isCertificated); // 조건에 따라 본인 인증 시작 / 완료
}
