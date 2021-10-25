package com.action.demoaction.service;

import com.action.demoaction.comm.httpres.CourseBody;
import com.action.demoaction.comm.httpres.XukeBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface StudyService {


    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    boolean login(String username, String password) throws Exception;

    /**
     * 获取所有学科
     * @return
     */
    List<XukeBody> getXukes(String userName) throws Exception;

    /**
     *
     * @param courseName
     * @param courseNo
     * @param userName

     * @return
     * @throws Exception
     */
    List<CourseBody> getCourseIds(String courseName, String courseNo, String userName) throws Exception;


    /**
     * 学习 一次学完
     * @param ids
     */
    void studyAll(ArrayList<CourseBody> ids, String userName)  throws Exception;

    void saveIds(String userName) throws Exception;

    /**
     * 学习 分批次学完
     */
    void doJob();



}
