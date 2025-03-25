package io.security.oauth2.springsecurityoauth2.service;

import io.security.oauth2.springsecurityoauth2.common.converters.ProviderUserConverter;
import io.security.oauth2.springsecurityoauth2.common.converters.ProviderUserRequest;
import io.security.oauth2.springsecurityoauth2.model.*;
import io.security.oauth2.springsecurityoauth2.model.users.User;
import io.security.oauth2.springsecurityoauth2.repository.UserRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.stereotype.Service;

@Service
@Getter
public abstract class AbstractOAuth2UserService {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public UserService userService;

    @Autowired
    private ProviderUserConverter<ProviderUserRequest, ProviderUser> providerUserConverter;

    // Refactoring
    public ProviderUser providerUser(ProviderUserRequest providerUserRequest) {
        return providerUserConverter.converter(providerUserRequest);
    }

    public void register(ProviderUser providerUser, OAuth2UserRequest userRequest) {
        User user = userRepository.findByUsername(providerUser.getUsername());

        if(user == null) {
            String registrationId = userRequest.getClientRegistration().getRegistrationId();

            userService.register(registrationId, providerUser);
        } else {
            System.out.println("user = " + user);
        }
    }
}
