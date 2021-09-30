package com.action.demoaction;


import com.action.demoaction.comm.CommResult;
import com.action.demoaction.comm.User;
import com.action.demoaction.comm.httpres.CourseBody;
import com.action.demoaction.comm.httpres.Xuke;
import com.action.demoaction.comm.httpres.XukeBody;
import com.action.demoaction.service.StudyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping
public class StudyAction {

    @Autowired
    StudyService studyService;


    @GetMapping("/study")
    public CommResult study(String userName,String password)  {
        log.info("study接口被访问" );
        log.info(userName);
        log.info(password);
        try {
//            String username = user.getUserName();
//            String password = user.getPassword();
            boolean isLogin = studyService.login(userName, password);
            if(!isLogin) return CommResult.fail("用户名或密码错误");
            List<XukeBody> xukes = studyService.getXukes(userName);
            // 所有课程id
            ArrayList<CourseBody> ids = new ArrayList<>();

            for (XukeBody xuke : xukes) {
                //某个学科下的所有课程
                List<CourseBody> courseIds = studyService.getCourseIds(xuke.getCourseName(), xuke.getCourseNo(), userName);
                ids.addAll(courseIds);
                break;
            }

            // 开始学习所有课程
//            studyService.studyAll(ids,username);
            System.out.println("gg");
        } catch (Exception e) {
            e.printStackTrace();
            return CommResult.fail("服务器异常");
        }
        return CommResult.success();
    }
}
