package com.action.demoaction;


import com.action.demoaction.comm.CommResult;
import com.action.demoaction.comm.httpres.Xuke;
import com.action.demoaction.comm.httpres.XukeBody;
import com.action.demoaction.service.StudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping
public class StudyAction {

    @Autowired
    StudyService studyService;


    @PostMapping("/study")
    public CommResult study(String username ,String pwd)  {
        try {
            studyService.login(username,pwd);
            List<XukeBody> xukes = studyService.getXukes();
            System.out.println("gg");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return CommResult.success();
    }
}
