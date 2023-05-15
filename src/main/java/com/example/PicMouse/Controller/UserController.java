package com.example.PicMouse.Controller;

import com.example.PicMouse.Enum.Constant;
import com.example.PicMouse.Oauth.GetSocialOAuthRes;
import com.example.PicMouse.Oauth.GoogleOAuth;
import com.example.PicMouse.Oauth.GoogleUser;
import com.example.PicMouse.Oauth.OAuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final OAuthService oAuthService;
    private final GoogleOAuth googleOAuth;


    //사용자가 저
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
            @RequestParam(name="code")String code) throws Exception {
        System.out.println("소셜로그인 api서버로부터 받은 code: " + code);
        Constant.SocialLoginType socialLoginType1 = Constant.SocialLoginType.valueOf(SocialLoginPath.toUpperCase());
        GetSocialOAuthRes getSocialOAuthRes=oAuthService.oAuthRes(socialLoginType1,code);
        return new BaseResponse<>(getSocialOAuthRes);
    }*/

    //호출되지 않음 ctr+b로 심볼 사용확인

    /*
    * */
    @ResponseBody
    @GetMapping(value = "/app/accounts/auth/{socailLoginType}/callback")
    public void callback(
            @PathVariable (name="socailLoginType")String SocialLoginPath,
            @RequestParam(name="code")String code) throws Exception {
        System.out.println("소셜로그인 api서버로부터 받은 code: " + code);
        Constant.SocialLoginType socialLoginType1 = Constant.SocialLoginType.valueOf(SocialLoginPath.toUpperCase());
        GoogleUser getSocialOAuthRes=oAuthService.oAuthLogin(socialLoginType1,code);
        System.out.println(getSocialOAuthRes);
    }

   /* @ResponseBody
    @PostMapping("/auth")
    public BaseResponse<PostAuthRes> accountAuth(@RequestBody PostAuthReq postAuthReq) throws BaseException {
        //입력값에 대한 validation
        if(postAuthReq.getUser_id()==null){
            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
        }
        if(!isRegexEmail(postAuthReq.getUser_id())){
            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
        }
        int checkAccountStatus=accountProvider.checkAccountStatus(postAuthReq.getUser_id());
        if(checkAccountStatus==user_deactivated){
            //비교시에 상수처리
            return new BaseResponse<>(ACCOUNT_DEACTIVATED);
        }
        try{
            PostAuthRes postAuthRes=accountProvider.accountAuth(postAuthReq);
            return new BaseResponse<>(postAuthRes);

        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }*/
}
