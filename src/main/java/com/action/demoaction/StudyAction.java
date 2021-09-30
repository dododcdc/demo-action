package com.action.demoaction;


import com.action.demoaction.comm.CommResult;
import com.action.demoaction.comm.User;
import com.action.demoaction.comm.httpres.CourseBody;
import com.action.demoaction.comm.httpres.Xuke;
import com.action.demoaction.comm.httpres.XukeBody;
import com.action.demoaction.service.StudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping
public class StudyAction {

    @Autowired
    StudyService studyService;


    @PostMapping("/study")
    public CommResult study(@RequestBody User user)  {
        try {
            String username = user.getUserName();
            String password = user.getPassword();
            studyService.login(username,password);
            List<XukeBody> xukes = studyService.getXukes(username);
            // 所有课程id
            ArrayList<CourseBody> ids = new ArrayList<>();

            for (XukeBody xuke : xukes) {
                //某个学科下的所有课程
                List<CourseBody> courseIds = studyService.getCourseIds(xuke.getCourseName(), xuke.getCourseNo(), username);
                ids.addAll(courseIds);
                if (true) break;
            }

            // 开始学习所有课程
            studyService.studyAll(ids,username);

            System.out.println("gg");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return CommResult.success();
    }
}
