package com.action.demoaction;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DemoAction {

    @ResponseBody
    @GetMapping("/urls")
    public String urls() {
        return "https://goodbin.cn/videos/v4.mp4";
    }

    @GetMapping("/home")
    public String home() {
        return "index";
    }
}
