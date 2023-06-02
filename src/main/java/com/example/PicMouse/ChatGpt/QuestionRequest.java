package com.example.PicMouse.ChatGpt;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class QuestionRequest {

    private List<Map<String, String>> messages=new ArrayList<>();
    private String word;

    public QuestionRequest(String word) {
        this.word = word;
        Map<String, String> map = new HashMap<>();
        map.put("role", "user");
        map.put("content", "post단어가 들어간 어린이들이 이해할 수 있는 영어1문장과 그걸 한글로 번역한 문장도 같이 출력해줘. 그리고 post단어를 한글로 번역해서줘.");
        messages.add(map);
        Map<String, String> map1 = new HashMap<>();
        map1.put("role", "assistant");
        map1.put("content", "영어: Cats are cute and cuddly pets that love to play with strings and balls of yarn.\\n한글: 고양이는 귀엽고 포근한 애완동물이며, 실과 얼룩말뭉치 등으로 놀기를 좋아합니다.\\n번역:고양이.");
        messages.add(map1);
        Map<String, String> map2 = new HashMap<>();
        map2.put("role", "user");
        map2.put("content", word + "단어가 들어간 어린이들이 이해할 수 있는 영어1문장과 그걸 한글로 번역한 문장도 같이 출력해줘. 그리고 " + word + "단어도 같이 번역해서 줘. 출력의 형태는 이전 결과물과 동일하게 줘");
        messages.add(map2);

    }





}
