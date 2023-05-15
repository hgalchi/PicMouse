package com.example.PicMouse.Oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GoogleOAuth implements SocialOauth{
    //구글과 통신하기 위한 메소드

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
//리디렉션 변후 값 state권장됨 추가 예정
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


    //accessToken 발급에서 문제가 생김.. 왜지
    public ResponseEntity<String> requestAccessToken(String code) {

        String googleToken = "https://oauth2.googleapis.com/token";
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> params = new HashMap<>();

        params.put("code", code);
        params.put("client_id", googleClientId);
        params.put("client_secret", googleClientSecret);
        params.put("redirect_uri", googleCallbackUrl);
        params.put("grant_type", "authorization_code");




        ResponseEntity<String> responseEntity = restTemplate.postForEntity(googleToken,
                params, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity;
        }

        return null;
    }

    //jsonString을  역직렬화해 객체에 담는다.
    public GoogleOAuthToken getAccessToken(ResponseEntity<String> response) throws JsonProcessingException {
        System.out.println("response.getBody:" + response.getBody());
        GoogleOAuthToken googleOAuthToken = objectMapper.readValue(response.getBody(), GoogleOAuthToken.class);
        return googleOAuthToken;
    }

    /*
   다시 구글로 액세스 토큰을 보내 구글 사용자 정보를 받아온다.
   */
    public ResponseEntity<String> requestUserInfo(GoogleOAuthToken oAuthToken) {

        String googleUserInfo = "https://www.googleapis.com/oauth2/v1/userinfo";
        RestTemplate restTemplate = new RestTemplate();


        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer" + oAuthToken.getAccess_token());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity(headers);
        ResponseEntity<String> response = restTemplate.exchange(googleUserInfo, HttpMethod.GET,
                request, String.class);
        System.out.println("response.getBody()=" + response.getBody());
        return response;
    }

    public GoogleUser getUserInfo(ResponseEntity<String> userInfoRes) throws JsonProcessingException {
        GoogleUser googleUser = objectMapper.readValue(userInfoRes.getBody(), GoogleUser.class);
        return googleUser;
    }
}
