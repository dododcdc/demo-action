package com.action.demoaction.service;

import com.action.demoaction.comm.httpres.ResLogin;
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

import java.util.*;

@Service
public class StudyServiceImpl implements StudyService {




    @Autowired
    RestTemplate restTemplate;


    private final String urlLogin = "http://www.xdwy.com.cn/learning/loginController.do?checkuser";

    private final String urlXuke = "http://www.xdwy.com.cn/learning/yTeachOutlineController.do?datagridStuStudy&sysType=1&field=id,createName,createBy,createDate,updateName,updateBy,updateDate,useTime,courseNo,courseName,courseForm,examForm,mainTeacherName,assistTeacherName,answerTeacherName,";

    private final String urlCourse = "http://www.xdwy.com.cn/learning/yCourseKnowledgeController.do";

    private HTTP http = HTTP.builder().build();

    private Map<String,String> cookies = new HashMap<>();

    @Override
    public void login(String username, String password) throws Exception {

        // 如果已经有cookie直接返回
        if (this.cookies.containsKey(username)) return;

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
        // todo 将cookie 保存起来（用map，key存userNmame，下次来访问直接查询，有就直接拿cookie不走登录了），给其他访问带上使用
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
        ResponseEntity<String> response = restTemplate.postForEntity(this.urlXuke, request, String.class);
        Xuke xuke = restTemplate.postForObject(this.urlXuke, request, Xuke.class);
        System.out.println(response.getBody());

        return xuke.getRows();
    }

    @Override
    public List<String> getCourseIds(String CourseName, String CourseNo,String userName) {
        return null;
    }

    @Override
    public void studyAll(String ids) {


    }


}
