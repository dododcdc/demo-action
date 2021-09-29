package com.action.demoaction.service;

import com.action.demoaction.comm.httpres.ResLogin;
import com.action.demoaction.comm.httpres.Xuke;
import com.action.demoaction.comm.httpres.XukeBody;
import com.ejlchina.okhttps.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class StudyServiceImpl implements StudyService {




    @Autowired
    RestTemplate restTemplate;


    private final String urlLogin = "http://www.xdwy.com.cn/learning/loginController.do?checkuser";

    private final String urlXuke = "http://www.xdwy.com.cn/learning/yTeachOutlineController.do?datagridStuStudy&sysType=1&field=id,createName,createBy,createDate,updateName,updateBy,updateDate,useTime,courseNo,courseName,courseForm,examForm,mainTeacherName,assistTeacherName,answerTeacherName,";

    private final String urlCourse = "http://www.xdwy.com.cn/learning/yCourseKnowledgeController.do";

    private HTTP http = HTTP.builder().build();

    @Override
    public void login(String username, String password) throws Exception {

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("userName",username);
        map.add("password",password);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

//        ResLogin resLogin = restTemplate.postForObject(this.urlLogin, request,ResLogin.class);
        ResponseEntity<String> exchange = restTemplate.exchange(this.urlLogin, HttpMethod.POST, request, String.class);

        HttpHeaders headers1 = exchange.getHeaders();
        // todo 将cookie 保存起来（用map，key存userNmame，下次来访问直接查询，有就直接拿cookie不走登录了），给其他访问带上使用
        String cookie = headers1.get("Set-Cookie").get(0);
        String body = exchange.getBody();
//        if (!resLogin.isSuccess()) {
//            throw new Exception(resLogin.getMsg());
//        }
        System.out.println("over");

    }

    @Override
    public List<XukeBody> getXukes() {

//        ResponseEntity<Xuke.XukeBody[]> res = restTemplate.getForEntity(this.urlXuke, Xuke.XukeBody[].class);
//        Xuke response = restTemplate.getForObject(
//                this.urlXuke,
//                Xuke.class);
//        List<XukeBody> rows = response.getRows();

//        Map<String,Object> res = restTemplate.getForObject(this.urlXuke,Map.class);

        ResponseEntity<String> exchange = restTemplate.exchange(this.urlXuke, HttpMethod.GET, new RequestEntity<>(null, null), String.class);

        return null;
    }

    @Override
    public List<String> getCourseIds(String CourseName, String CourseNo) {
        return null;
    }

    @Override
    public void studyAll(String ids) {


    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
