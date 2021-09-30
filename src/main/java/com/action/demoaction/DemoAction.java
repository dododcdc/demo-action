package com.action.demoaction;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
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


    @ResponseBody
    @PostMapping("/test")
    public String test() {
        log.info("test接口被访问");
        return "https://goodbin.cn/videos/v4.mp4";
    }
}
