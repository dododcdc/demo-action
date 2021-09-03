package com.action.demoaction.advice;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("book")
public class BookAction {

    @GetMapping("get")
    public List<Integer> get(){
        List<Integer> integers = Arrays.asList(1, 2, 3);
        return integers;
    }
}
