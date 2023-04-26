package com.example.PicMouse.Oauth;

import com.example.PicMouse.Enum.Constant;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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


}
