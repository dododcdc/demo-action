package com.action.demoaction.service;

import com.action.demoaction.comm.StudyStatus;
import com.action.demoaction.comm.httpres.Course;
import com.action.demoaction.comm.httpres.CourseBody;
import com.action.demoaction.comm.httpres.Xuke;
import com.action.demoaction.comm.httpres.XukeBody;
import com.action.demoaction.config.AppConstent;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class StudyServiceImpl implements StudyService {

    @Autowired
    RestTemplate restTemplate;


    private final String urlLogin = "http://www.xdwy.com.cn/learning/loginController.do?checkuser";

    private final String urlXuke = "http://www.xdwy.com.cn/learning/yTeachOutlineController.do?datagridStuStudy&sysType=1&field=id,createName,createBy,createDate,updateName,updateBy,updateDate,useTime,courseNo,courseName,courseForm,examForm,mainTeacherName,assistTeacherName,answerTeacherName,";

    private final String urlCourse = "http://www.xdwy.com.cn/learning/yCourseKnowledgeController.do";

    private final String urlWatch = "http://www.xdwy.com.cn/learning/yCourseKnowledgeController.do?yCourseKnowledgeStudy&id=";


    private Map<String, String> cookies = new HashMap<>();

    private final ConcurrentHashMap<String,ArrayBlockingQueue<CourseBody>> map = new ConcurrentHashMap<>();

    @Override
    public boolean login(String username, String password) throws Exception {

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("userName", username);
        map.add("password", password);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

//        ResLogin resLogin = restTemplate.postForObject(this.urlLogin, request,ResLogin.class);
        ResponseEntity<String> exchange = restTemplate.exchange(this.urlLogin, HttpMethod.POST, request, String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(exchange.getBody());
        JsonNode success = jsonNode.path("success");
        boolean isLogin = success.asBoolean();
        if (!isLogin) return isLogin;

        // 登录成功将cookie保存起来
        HttpHeaders headers1 = exchange.getHeaders();
        String cookie = headers1.get("Set-Cookie").get(0);
        this.cookies.put(username, cookie);
        log.info("登录成功 " + username + "&" + password);
        return true;

    }

    @Override
    public List<XukeBody> getXukes(String userName) throws Exception {

        String cookie = this.cookies.get(userName);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.COOKIE, cookie);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(null, headers);
        Xuke xuke = restTemplate.postForObject(this.urlXuke, request, Xuke.class);
        return xuke.getRows();
    }

    @Override
    public List<CourseBody> getCourseIds(String courseName, String courseNo, String userName) throws Exception {
        String cookie = this.cookies.get(userName);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.COOKIE, cookie);
        String tmp = "?datagrid&courseName=" + courseName + "&sysType=1&courseNo=" + courseNo
                + "&field=id,courseName,coursePointNo,";
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(null, headers);
        Course course = restTemplate.postForObject(this.urlCourse + tmp, request, Course.class);
        List<CourseBody> rows = course.getRows();
        return rows;
    }

    @Override
    public void saveIds(String userName) throws Exception{
        List<XukeBody> xukes = getXukes(userName);
        String cookie = this.cookies.get(userName);
        ArrayBlockingQueue ids = new ArrayBlockingQueue<CourseBody>(500);
        for (XukeBody xuke : xukes) {

            List<CourseBody> courseIds = getCourseIds(xuke.getCourseName(), xuke.getCourseNo(), userName);

            ids.addAll(courseIds);

        }
        this.map.put(userName+"|"+cookie,ids);

    }


    @Async
    @Override
    public void studyAll(ArrayList<CourseBody> ids, String userName) throws Exception {
        String cookie = this.cookies.get(userName);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.COOKIE, cookie);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(null, headers);
        int num = 0;
        for (CourseBody courseBody : ids) {
            log.info("学号: " + userName + "总共有" + ids.size() + " 个课程");
            log.info( ++num + courseBody.getCourseName() + courseBody.getCoursePointNo() + "开始");
            String url = this.urlWatch + courseBody.getId();
            ResponseEntity<String> ent = restTemplate.postForEntity(url, request, String.class);
            log.info(num + courseBody.getCourseName()+courseBody.getCoursePointNo() + "链接--" + url + "\n" + ent);
            log.info(num + courseBody.getCourseName() + courseBody.getCoursePointNo() + "结束");
            Thread.sleep(500);
        }

        // 将学习状态更新
        AppConstent.STATUS.put(userName, StudyStatus.STARTED);

        log.info("所有课程学习完毕");

    }

    @Override
    public Map getMap()  {
        return this.map;
    }

    // 给定时任务调用

    @Async
    @Scheduled(cron = "0 0 9 * * ?")
//    @Scheduled(fixedDelay = 5000)
    @Override
    public void doJob() {

        //所有的用户
        ConcurrentHashMap.KeySetView<String, ArrayBlockingQueue<CourseBody>> strings = this.map.keySet();
        if (strings.size() < 1) {
            log.info("当前没有任务");
            return;
        }

        for (String user : strings) {
            ArrayBlockingQueue<CourseBody> courseBodies = this.map.get(user);
            // 如果这个用户没有，直接跳到下一个用户
            if (courseBodies==null || courseBodies.size()==0) continue;
            int num = 0 ;

            String[] split = user.split("\\|");
            String cookie = split[1];
            String userName = split[0];
            // 每次调度 ， 给每个用户上30节课
            while (num < 30) {
                CourseBody courseBody = courseBodies.poll();
                if (courseBody == null) break;
                String id = courseBody.getId();
                String url = this.urlWatch + id;

                HttpHeaders headers = new HttpHeaders();
                headers.set(HttpHeaders.COOKIE, cookie);
                HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(null, headers);
                ResponseEntity<String> ent = restTemplate.postForEntity(url, request, String.class);
                log.info("\n"+ ++num + "链接->>"+url+ "\n返回结果\n"+ent);
                log.info( userName + "->" + courseBody.getCourseName() + courseBody.getCoursePointNo() + "完毕");
            }

            Integer res = AppConstent.COURSED.getOrDefault(userName, 0);
            AppConstent.COURSED.put(userName,res + num);
            log.info(userName + "还有" + courseBodies.size() +"节课未上");
        }
    }
}
