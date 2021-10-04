package com.action.demoaction;


import com.action.demoaction.comm.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping("/xd")
    public String xd() {
        return "study";
    }

    @GetMapping("/xdp")
    public String xdp() {
        return "studyphone";
    }




    @ResponseBody
    @PostMapping("/test")
    public String test(@RequestBody User user) {
        log.info(user.toString());
        log.info("test接口被访问");
        return "https://goodbin.cn/videos/v4.mp4";
    }
}
