package com.action.demoaction;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoAction {

    @GetMapping("/action")
    public String doAction() {
        return "success";
    }
}
