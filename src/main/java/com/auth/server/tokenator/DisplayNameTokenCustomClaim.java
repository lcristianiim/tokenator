package com.auth.server.tokenator;

import com.auth.server.tokenator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;


import java.util.HashMap;
import java.util.Map;

public class DisplayNameTokenCustomClaim implements TokenEnhancer {
    @Autowired
    UserRepository repository;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
            Map<String, Object> additionalInfo = new HashMap<>();
            additionalInfo.put(
                    "displayName", repository.findByEmail(oAuth2Authentication.getName()).getUsername());
            ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(
                    additionalInfo);
            return oAuth2AccessToken;
    }
}
