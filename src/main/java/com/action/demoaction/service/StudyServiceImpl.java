package com.action.demoaction.service;

import com.action.demoaction.comm.httpres.Course;
import com.action.demoaction.comm.httpres.CourseBody;
import com.action.demoaction.comm.httpres.Xuke;
import com.action.demoaction.comm.httpres.XukeBody;
import com.ejlchina.okhttps.HTTP;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class StudyServiceImpl implements StudyService {




    @Autowired
    RestTemplate restTemplate;


    private final String urlLogin = "http://www.xdwy.com.cn/learning/loginController.do?checkuser";

    private final String urlXuke = "http://www.xdwy.com.cn/learning/yTeachOutlineController.do?datagridStuStudy&sysType=1&field=id,createName,createBy,createDate,updateName,updateBy,updateDate,useTime,courseNo,courseName,courseForm,examForm,mainTeacherName,assistTeacherName,answerTeacherName,";

    private final String urlCourse = "http://www.xdwy.com.cn/learning/yCourseKnowledgeController.do";

    private final String urlWatch = "http://www.xdwy.com.cn/learning/yCourseKnowledgeController.do?yCourseKnowledgeStudy&id=";

    private HTTP http = HTTP.builder().build();

    private Map<String,String> cookies = new HashMap<>();

    @Override
    public void login(String username, String password) throws Exception {

        // 如果已经有cookie直接返回
//        if (this.cookies.containsKey(username)) return;

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("userName",username);
        map.add("password",password);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

//        ResLogin resLogin = restTemplate.postForObject(this.urlLogin, request,ResLogin.class);
        ResponseEntity<String> exchange = restTemplate.exchange(this.urlLogin, HttpMethod.POST, request, String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(exchange.getBody());
        JsonNode success = jsonNode.path("success");
        boolean b = success.asBoolean();

        HttpHeaders headers1 = exchange.getHeaders();
        String cookie = headers1.get("Set-Cookie").get(0);
        this.cookies.put(username,cookie);
        String body = exchange.getBody();
//        if (!resLogin.isSuccess()) {
//            throw new Exception(resLogin.getMsg());
//        }
        System.out.println("over");

    }

    @Override
    public List<XukeBody> getXukes(String userName) {

        String cookie = this.cookies.get(userName);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.COOKIE,cookie);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(null, headers);
//        ResponseEntity<String> response = restTemplate.postForEntity(this.urlXuke, request, String.class);
        Xuke xuke = restTemplate.postForObject(this.urlXuke, request, Xuke.class);
        return xuke.getRows();
    }

    @Override
    public List<CourseBody> getCourseIds(String courseName, String courseNo, String userName) {
        String cookie = this.cookies.get(userName);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.COOKIE,cookie);
        String tmp = "?datagrid&courseName=" + courseName + "&sysType=1&courseNo=" + courseNo
                + "&field=id,courseName,coursePointNo,";
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(null, headers);
        Course course = restTemplate.postForObject(this.urlCourse+tmp, request, Course.class);

        return course.getRows();

    }

    @Override
    public void studyAll(ArrayList<CourseBody> ids,String userName) {

        String cookie = this.cookies.get(userName);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.COOKIE,cookie);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(null, headers);

        for (CourseBody courseBody : ids) {
            String url = this.urlWatch + courseBody.getId();
            ResponseEntity<String> ent = restTemplate.postForEntity(url, request, String.class);
            System.out.println("链接--"+url+"\n" + ent);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


        System.out.println("over");

    }


}
