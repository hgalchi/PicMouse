package com.example.PicMouse.Controller;

import com.example.PicMouse.Enum.Constant;
import com.example.PicMouse.Oauth.GoogleOAuth;
import com.example.PicMouse.Oauth.OAuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final OAuthService oAuthService;
    private final GoogleOAuth googleOAuth;


    //@NoAuth
    @GetMapping("/auth/{socialLoginType}")
    public void SocialLoginTypeRedirect(@PathVariable(name = "socialLoginType") String SocialLoginPath) throws IOException {

        System.out.println("SocialLoginPath=" + SocialLoginPath);
        Constant.SocialLoginType socialLoginType = Constant.SocialLoginType.valueOf(SocialLoginPath.toUpperCase());
        oAuthService.request(socialLoginType);
    }

  /*  @ResponseBody
    @GetMapping(value = "/auth/{socailLoginType}/callback")
    public BaseResponse<GetSocailOAuthRes>callback(
            @PathVariable (name="socailLoginType")String SocialLoginPath,
            @RequestParam(name="code")String code) {
        System.out.println("소셜로그인 api서버로부터 받은 code: " + code);
        Constant.SocialLoginType socialLoginType1 = Constant.SocialLoginType.valueOf(SocialLoginPath.toUpperCase());
        return new BaseResponse<>(getSocialOAuthRes);
    }*/
}
