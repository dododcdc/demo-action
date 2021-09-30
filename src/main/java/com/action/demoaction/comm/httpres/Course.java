package com.action.demoaction.comm.httpres;

import java.util.ArrayList;
import java.util.List;

public class Course {

    private String total;

    private List<CourseBody> rows = new ArrayList<>();

    public Course() {

    }

    public Course(String total, List<CourseBody> rows) {
        this.total = total;
        this.rows = rows;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<CourseBody> getRows() {
        return rows;
    }

    public void setRows(List<CourseBody> rows) {
        this.rows = rows;
    }
}
