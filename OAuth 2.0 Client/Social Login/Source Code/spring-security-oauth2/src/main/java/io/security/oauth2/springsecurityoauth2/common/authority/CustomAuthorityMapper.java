package io.security.oauth2.springsecurityoauth2.common.authority;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;

import java.util.Collection;
import java.util.HashSet;

public class CustomAuthorityMapper implements GrantedAuthoritiesMapper {
    // 구글의 경우 http://..... 에서 오므로 이를 Split 작업 필요

    private String prefix = "ROLE_"; // 기본 PREFIX

    @Override
    public Collection<? extends GrantedAuthority> mapAuthorities(Collection<? extends GrantedAuthority> authorities) {

        HashSet<GrantedAuthority> mapped = new HashSet<>(authorities.size());

        for (GrantedAuthority authority : authorities) {
            mapped.add(mapAuthority(authority.getAuthority()));
        }

        return mapped;
    }

    private GrantedAuthority mapAuthority(String name) { // 구글 예) http://google.com/.../dd.email...
        if(name.lastIndexOf(".") > 0) { // 맨 마지막에 . 이 있다면,
            int index = name.lastIndexOf(".");

            name = "SCOPE_" + name.substring(index + 1); // 마지막 . 이후의 값 추출후, SCOPE_ 이름을 붙임
        }

        if(prefix.length() > 0 && !name.startsWith(prefix)) { // prefix가 있고, prefix로 시작하는 문자가 없으면,
            name = prefix + name; // prefix를 붙여 사용
        }

        return new SimpleGrantedAuthority(name); // 이를 권한 부여
    }
}
