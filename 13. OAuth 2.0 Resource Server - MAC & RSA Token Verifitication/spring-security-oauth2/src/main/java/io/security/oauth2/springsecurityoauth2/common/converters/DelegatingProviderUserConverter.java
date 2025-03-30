package io.security.oauth2.springsecurityoauth2.common.converters;

import io.security.oauth2.springsecurityoauth2.model.ProviderUser;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Component
public class DelegatingProviderUserConverter implements ProviderUserConverter<ProviderUserRequest, ProviderUser> {

    // Converter를 모아놓는 List
    private List<ProviderUserConverter<ProviderUserRequest, ProviderUser>> converters;

    // 생성자로, 해당 Provider를 저장할 List 생성
    public DelegatingProviderUserConverter() {
        List<ProviderUserConverter<ProviderUserRequest, ProviderUser>> providerUserConverters =
                Arrays.asList(new UserDetailsProviderUserConverter(), new OAuth2GoogleProviderUserConverter(), new OAuth2NaverProviderUserConverter(), new OAuth2KakaoProviderUserConverter(), new OAuth2KakaoOidcProviderUserConverter());

        this.converters = Collections.unmodifiableList(new LinkedList<>(providerUserConverters));
    }

    @Override
    public ProviderUser converter(ProviderUserRequest providerUserRequest) {
        // ProviderUserRequst는 Null이면 안 됨
        Assert.notNull(providerUserRequest, "providerUserRequest must not be null");

        // ProviderConverter를 하나씩 확인해서
        for (ProviderUserConverter<ProviderUserRequest, ProviderUser> converter : converters) {
            ProviderUser providerUser = converter.converter(providerUserRequest);// 위임해서 확인

            if(providerUser != null) {
                return providerUser;
            }
        }

        return null;
    }
}