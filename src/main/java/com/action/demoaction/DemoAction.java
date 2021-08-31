package com.action.demoaction;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoAction {

    @GetMapping("/getUrls")
    public String getUrls() {
        return "https://goodbin.cn/videos/v4.mp4";
    }
}
