package com.example.PicMouse.Oauth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

//액세스 토큰을 보내 받아올 구글에 등록된 사용자 정보
@AllArgsConstructor
@Getter
@Setter
public class GoogleUser {
   // public String id;
    public String email;
    public String name;
    public String picture;
}
