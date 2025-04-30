package io.security.oauth2.springsecurityoauth2.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class Attributes {

    private Map<String, Object> mainAttributes; // 1차원적으로 가져올 수 있는 속성
    private Map<String, Object> subAttributes; // 한 단계 까지 접근 속성
    private Map<String, Object> otherAttributes; // 한 단계 이상 접근 속성
}
