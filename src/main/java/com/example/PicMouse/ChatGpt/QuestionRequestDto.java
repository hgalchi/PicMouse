package com.example.PicMouse.ChatGpt;

import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Getter
public class QuestionRequestDto implements Serializable {
    private String question;
    private List<String> messages;


}
