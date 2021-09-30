package com.action.demoaction.comm.httpres;

public class CourseBody {


    private String id;
    private String courseName;
    private String coursePointNo;


    public CourseBody() {

    }

    public CourseBody(String id, String courseName, String coursePointNo) {
        this.id = id;
        this.courseName = courseName;
        this.coursePointNo = coursePointNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCoursePointNo() {
        return coursePointNo;
    }

    public void setCoursePointNo(String coursePointNo) {
        this.coursePointNo = coursePointNo;
    }
}
