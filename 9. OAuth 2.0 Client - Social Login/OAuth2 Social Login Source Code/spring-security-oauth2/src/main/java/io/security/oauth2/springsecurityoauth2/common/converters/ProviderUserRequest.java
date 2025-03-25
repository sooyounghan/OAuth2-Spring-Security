package io.security.oauth2.springsecurityoauth2.common.converters;

import io.security.oauth2.springsecurityoauth2.model.users.User;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

// recode : 클래스가 Lombok 사용하는 것처럼 Getter 기능 추가, equals, hashcode, toString 자동 생성 및 불변 객체
// 필드에 대해 생성자 생성
public record ProviderUserRequest(ClientRegistration clientRegistration, OAuth2User oAuth2User, User user) {

    // 3개의 속성을 받음 (clientRegistration, oAuth2User, Form인 증에서 사용하는 User)

    public ProviderUserRequest(ClientRegistration clientRegistration, OAuth2User oAuth2User) {
        this(clientRegistration, oAuth2User, null); // 2개 속성을 받는 생성자
    }

    public ProviderUserRequest(User user) {
        this(null, null, user); // Form 인증을 받을 때 사용할 생성자
    }
}
