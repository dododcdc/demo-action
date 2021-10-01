package com.action.demoaction.service;

import com.action.demoaction.comm.httpres.CourseBody;
import com.action.demoaction.comm.httpres.XukeBody;

import java.util.ArrayList;
import java.util.List;

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
     * 根据学科获取该学科下所有的课程Id
     * @param courseName
     * @param courseNo
     * @return
     */
    List<CourseBody> getCourseIds(String courseName, String courseNo, String userName) throws Exception;

    /**
     * 学习
     * @param ids
     */
    void studyAll(ArrayList<CourseBody> ids, String userName)  throws Exception;

}
