/*
package com.example.PicMouse.Oauth;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;

import static java.util.stream.Stream.builder;


@Service
public class JwtService {

    public String creatJwt(int userNum) {
        Date now = new Date();
        reutrn Jwts.builder()
                .setHeaderParam("type", "jwt")
                .claim("userNum", userNum)
                .setIssuedAd(now)
                .setExpiration(new Date(System.currentTimeMillis()+1*(1000*60*60*24*365))) //발급날짜 계산
                .signWith(SignatureAlgorithm.HS256, Secret.JWT_SECRET_KEY) //signature 부분
                .compact();
    }

    public String getJwt(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("X-ACCESS-TOKEN");
    }

    public int getUserNum() throws Exception {
        //1. JWT 추출
        String accessToken = getJwt();
        if(accessToken == null || accessToken.length() == 0){
            throw new Exception();
        }

        // 2. JWT parsing
        Jws<Claims> claims;
        try{
            claims = Jwts.parser()
                    .setSigningKey(Secret.JWT_SECRET_KEY)
                    .parseClaimsJws(accessToken);
        } catch (Exception ignored) {
            throw new BaseException(INVALID_JWT);
        }

        // 3. userNum 추출
        return claims.getBody().get("userNum",Integer.class);
    }
}
*/
