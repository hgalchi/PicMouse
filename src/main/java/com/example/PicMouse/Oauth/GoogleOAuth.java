package com.example.PicMouse.Oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GoogleOAuth implements SocialOauth{

    //구글로그인url과 clientid,redirecturl를 조합해서 url을 넘겨줄것
    @Value("${google.client-id}")
    private String googleClientId;

    @Value("${google.client-secret}")
    private String googleClientSecret;

    @Value("${google.url}")
    private String googleLoginUrl;

    @Value("${google.callback-url}")
    private String googleCallbackUrl;

    @Value("${google.scope}")
    private String googleScope;

    private final ObjectMapper objectMapper;
    @Override
    public String getOauthRedirectURL() {

        Map<String, Object> params = new HashMap<>();
        params.put("scope", googleScope);
        params.put("response_type", "code");
        params.put("client_id", googleClientId);
        params.put("redirect_uri", googleCallbackUrl);

        //redirect url을 생성하는 로직을 구성
        String parameterString = params.entrySet().stream()
                .map(x -> x.getKey() + "=" + x.getValue())
                .collect(Collectors.joining("&"));
        String redirectURL=googleLoginUrl+"?"+parameterString;
        System.out.println("redirectUrl="+redirectURL);
        return redirectURL;
    }
}
