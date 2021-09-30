package com.action.demoaction.service;

import com.action.demoaction.comm.httpres.XukeBody;

import java.util.List;

public interface StudyService {


    /**
     * 登录
     * @param username
     * @param password
     */
    void login(String username,String password) throws Exception;

    /**
     * 获取所有学科
     * @return
     */
    List<XukeBody> getXukes(String userName) ;

    /**
     * 根据学科获取该学科下所有的课程Id
     * @param CourseName
     * @param CourseNo
     * @return
     */
    List<String> getCourseIds(String CourseName,String CourseNo,String userName);

    /**
     * 学习
     * @param ids
     */
    void studyAll(String ids);




}
