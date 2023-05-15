package com.example.PicMouse.Oauth;

import com.example.PicMouse.Enum.Constant;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
@RequiredArgsConstructor
public class OAuthService {

    private final GoogleOAuth googleOAuth;
    private final HttpServletResponse res;

    public void request(Constant.SocialLoginType socialLoginType) throws IOException {
        String redirectURL;
        switch (socialLoginType) {
            case GOOGLE:{
                redirectURL = googleOAuth.getOauthRedirectURL();
            }break;
            default:{
                throw new IllegalArgumentException("알수 없는 소셜 로그인 형식입니다. ");
            }
        }
        res.sendRedirect(redirectURL);
    }

    public GetSocialOAuthRes oAuthRes(Constant.SocialLoginType socialLoginType, String code) throws Exception {
        switch (socialLoginType) {
            case GOOGLE :{
                //구글로 일회성 코드를 보내 액세스 토큰이 담긴 응답객체를 받아옴
                ResponseEntity<String> accessTokenResponse= googleOAuth.requestAccessToken(code);
                //응답 객체가 JSON형식으로 되어 있으므로, 이를 deserialization해서 자바 객체에 담을 것이다.
                GoogleOAuthToken oAuthToken=googleOAuth.getAccessToken(accessTokenResponse);

                //액세스 토큰을 다시 구글로 보내 구글에 저장된 사용자 정보가 담긴 응답 객체를 받아온다.
                ResponseEntity<String> userInfoResponse=googleOAuth.requestUserInfo(oAuthToken);
                //다시 JSON 형식의 응답 객체를 자바 객체로 역직렬화한다.
                GoogleUser googleUser= googleOAuth.getUserInfo(userInfoResponse);
                System.out.println(googleUser.getId());
            }
            default:{
                throw new IllegalArgumentException("oAuthRes ");
            }

        }
    }
    public GoogleUser oAuthLogin(Constant.SocialLoginType socialLoginType,String code) throws Exception {
        GoogleUser googleUser;
        switch (socialLoginType) {
            case GOOGLE: {
                //구글로 일회성 코드를 보내 액세스 토큰이 담긴 응ㅇ답객체를 받아옴
                ResponseEntity<String> accessTokenResponse = googleOAuth.requestAccessToken(code);
                //응답 객체가 Json형식으로 되어 있으므로, 이를 자바 객체에 담는다.
                GoogleOAuthToken oAuthToken = googleOAuth.getAccessToken(accessTokenResponse);
                System.out.println("발급받은 토큰" + oAuthToken.getAccess_token());

                //액세스 토큰을 다시 구글로 보내 구글에 저장된 사용자 정보가 담긴 응답객체를 받아온다.
                ResponseEntity<String> userInfoResponse = googleOAuth.requestUserInfo(oAuthToken);
                //다시 Json형식의 응답객체를 자바 객체로 역직렬화한다.
                googleUser = googleOAuth.getUserInfo(userInfoResponse);

                String user_id = googleUser.getEmail();
                System.out.println(user_id);

                // 현재 서버에 user가 존재하지 않는다라고 가정하고 jwtToken을 발급산다.
               /* String jwtToken=jwtService.createJwt(user_nu);
                GetSocialOAuthRes getSocialOAuthRes=new GetSocialOAuthRes((jwtToken,user_num,oAuthToken.getAcc))*/

                break;
            }
            default: {
                throw new IllegalArgumentException("oAuthLogin ");
            }
        }
        return googleUser;
    }

}
