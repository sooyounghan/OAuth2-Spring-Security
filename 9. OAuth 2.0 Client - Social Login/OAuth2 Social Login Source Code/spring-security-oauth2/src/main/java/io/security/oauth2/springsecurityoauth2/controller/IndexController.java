package io.security.oauth2.springsecurityoauth2.controller;

import io.security.oauth2.springsecurityoauth2.common.util.OAuth2Utils;
import io.security.oauth2.springsecurityoauth2.model.PrincipalUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model model, @AuthenticationPrincipal PrincipalUser principalUser) {
        String view = "index";

        if(principalUser != null) {

            String username = principalUser.providerUser().getUsername();

            model.addAttribute("user", username);
            model.addAttribute("provider", principalUser.providerUser().getProvider());

            if(!principalUser.providerUser().isCertificated()) view = "selfcert";
        }

        return view;
    }
}
