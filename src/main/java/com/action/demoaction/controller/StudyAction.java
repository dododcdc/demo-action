package com.action.demoaction.controller;


import com.action.demoaction.comm.CommResult;
import com.action.demoaction.comm.StudyStatus;
import com.action.demoaction.comm.User;
import com.action.demoaction.comm.httpres.CourseBody;
import com.action.demoaction.comm.httpres.XukeBody;
import com.action.demoaction.comm.AppConstent;
import com.action.demoaction.service.StudyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping
public class StudyAction {

    @Autowired
    StudyService studyService;

    /**
     * 开始学习
     *
     * @param user
     * @return
     */
    @PostMapping("/study")
    public CommResult study(@RequestBody User user) {
        log.info("study接口被访问");

        // 先判断 AppConstent.STATUS 的size  不能超过一条 ,其次  状态只能是已经结束 , 这样才能启动任务
        if (AppConstent.STATUS.size() > 1) {
            return CommResult.fail("异常：发生了难以置信的情况");
        } else if (AppConstent.STATUS.size() == 1) {
            Set<String> strings = AppConstent.STATUS.keySet();
            StudyStatus s = null;
            for (String string : strings) {
                s = AppConstent.STATUS.get(string);
                break;
            }
            // 等于1有两个情况 ，一个是 已经完成，一个是还没完成
            if (s != StudyStatus.STARTED) {  // 没完成
                return CommResult.fail("当前有人提交了任务正在运行，请稍后再试");
            } else { // 已经完成
                AppConstent.STATUS.clear();
            }
        }

        try {
            String userName = user.getUserName();
            String password = user.getPassword();
            log.info(userName);
            log.info(password);

            boolean isLogin = studyService.login(userName, password);

            if (!isLogin) return CommResult.fail("用户名或密码错误");
            // 更新学习状态
            AppConstent.STATUS.put(userName, StudyStatus.STARTING);
            List<XukeBody> xukes = studyService.getXukes(userName);
            // 所有课程id
            ArrayList<CourseBody> ids = new ArrayList<>();

            for (XukeBody xuke : xukes) {
                //某个学科下的所有课程
                List<CourseBody> courseIds = studyService.getCourseIds(xuke.getCourseName(), xuke.getCourseNo(), userName);
                ids.addAll(courseIds);
            }


            // 开始学习所有课程,异步执行，学习结束后会更新学习状态
            studyService.studyAll(ids, userName);

            log.info("提交完毕");
        } catch (Exception e) {
            e.printStackTrace();
            return CommResult.fail("服务器异常");
        }

        return CommResult.success("自动上课任务已经提交,请耐心等待,该程序不返回执行状态,稍后自己去西电学习平台查看是否已经成功");
    }


    @PostMapping("/studyJob")
    public CommResult studyJob(@RequestBody User user) {
        try {
            String userName = user.getUserName();
            String password = user.getPassword();
            log.info(userName);
            log.info(password);
            boolean isLogin = studyService.login(userName, password);
            if (!isLogin) return CommResult.fail("用户名或密码错误");
            studyService.saveIds(userName);
            studyService.doJob();
        } catch (Exception e) {
            e.printStackTrace();
            return CommResult.fail("服务器异常");
        }

        return CommResult.success("任务已经提交，后续会每天自动为你上30节课");

    }

    /**
     * 该接口废弃，不做状态查询
     * 查询学习状态 前端定时任务每隔一秒调用一次
     *
     * @param userName
     * @return
     */
    @GetMapping("/status")
    public CommResult status(String userName) {
        log.info("status被访问......");
        StudyStatus studyStatus = AppConstent.STATUS.getOrDefault(userName, StudyStatus.NOTSTART);

        log.info(AppConstent.STATUS.size() + "" + studyStatus);

        switch (studyStatus) {
            case STARTED:
                return CommResult.success("完成");
            case NOTSTART:
                return CommResult.success("还未开始");
            case STARTING:
                return CommResult.success("正在上课.....");
            default:
                return CommResult.fail("异常");
        }
    }
}
