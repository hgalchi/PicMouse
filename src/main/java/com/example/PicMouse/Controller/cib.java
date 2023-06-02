package com.example.PicMouse.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequiredArgsConstructor
public class cib {

    @RequestMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public String index() {

        return "login_success";
    }
}
